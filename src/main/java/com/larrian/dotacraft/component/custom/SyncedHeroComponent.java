package com.larrian.dotacraft.component.custom;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.event.network.AutoCraftPacket;
import com.larrian.dotacraft.event.ServerEvents;
import com.larrian.dotacraft.event.network.SkillPacket;
import com.larrian.dotacraft.hero.*;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.ModRegistries;
import com.larrian.dotacraft.hero.tick.ClientTickingHero;
import com.larrian.dotacraft.hero.tick.ServerTickingHero;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, AutoSyncedComponent {
    private static final int LEVEL_LIMIT = 30;
    private static final int TICKS_PER_SECOND = 20;

    private final PlayerEntity provider;
    private DotaHero hero;
    private double health;
    private double mana;
    private int level;
    private final EnumMap<Skill.Type, Integer> skillCooldowns;
    private final EnumMap<Skill.Type, Boolean> activeSkills;
    @Environment(EnvType.CLIENT)
    private Set<Integer> clientBlockedSlots;
    private NbtList cache;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
        this.skillCooldowns = new EnumMap<>(Skill.Type.class);
        this.activeSkills = new EnumMap<>(Skill.Type.class);
        if (provider.getWorld().isClient) {
            this.clientBlockedSlots = new HashSet<>();
        }
        this.cache = new NbtList();
        for (var skillType : Skill.Type.values()) {
            skillCooldowns.put(skillType, 0);
            activeSkills.put(skillType, false);
        }
    }

    @Override
    public void sync() {
        provider.syncComponent(HERO_COMPONENT);
    }

    private void tick() {
        double healthRegen = provider.getComponent(ATTRIBUTES_COMPONENT).getAttribute(ModAttributes.REGENERATION_HEALTH).get() / TICKS_PER_SECOND;
        double manaRegen = provider.getComponent(ATTRIBUTES_COMPONENT).getAttribute(ModAttributes.REGENERATION_MANA).get() / TICKS_PER_SECOND;
        addHealth(healthRegen);
        addMana(manaRegen);

        for (var entry : skillCooldowns.entrySet()) {
            int currentCooldown = entry.getValue();
            if (currentCooldown > 0) {
                entry.setValue(currentCooldown - 1);
            }
        }
    }

    @Override
    public void serverTick() {
        tick();
        if (getHero() instanceof ServerTickingHero serverTickingHero)
            serverTickingHero.serverTick();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void clientTick() {
        tick();
        if (DotaCraft.AUTO_CRAFT) {
            NbtList current = provider.getInventory().writeNbt(new NbtList());
            if (!this.cache.equals(current)) {
                if (AutoCraftPacket.craft(provider, clientBlockedSlots)) {
                    ClientPlayNetworking.send(ServerEvents.AUTO_CRAFT_PACKET,
                            new AutoCraftPacket(clientBlockedSlots).toPacketByteBuf());
                    this.cache = current;
                }
            }
        }
        if (getHero() instanceof ClientTickingHero serverTickingHero)
            serverTickingHero.clientTick();
    }

    @Override
    public DotaHero getHero() {
        return hero;
    }

    @Override
    public DotaHeroType<?> getHeroType() {
        return hero != null ? hero.getType() : null;
    }

    @Override
    public boolean isHero() {
        return hero != null;
    }

    @Override
    public void setHero(DotaHero hero) {
        this.hero = hero;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public boolean isFullHealth() {
        return health == provider.getComponent(ATTRIBUTES_COMPONENT).getAttribute(ModAttributes.MAX_HEALTH).get();
    }

    @Override
    public void setHealth(double health) {
        double maxHealth = provider.getComponent(ATTRIBUTES_COMPONENT).getAttribute(ModAttributes.MAX_HEALTH).get();
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    @Override
    public void addHealth(double amount) {
        setHealth(this.health + amount);
    }

    @Override
    public double getMana() {
        return mana;
    }

    @Override
    public boolean isFullMana() {
        return mana == provider.getComponent(ATTRIBUTES_COMPONENT).getAttribute(ModAttributes.MAX_MANA).get();
    }

    @Override
    public void setMana(double mana) {
        double maxMana = provider.getComponent(ATTRIBUTES_COMPONENT).getAttribute(ModAttributes.MAX_MANA).get();
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    @Override
    public void addMana(double amount) {
        setMana(this.mana + amount);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(level, LEVEL_LIMIT));
    }

    @Override
    public void addLevel(int add) {
        setLevel(this.level + add);
    }

    @Override
    public EnumMap<Skill.Type, Integer> getSkillCooldowns() {
        return skillCooldowns;
    }

    @Override
    public void useSkill(Skill.Type skillType) {
        if (hero != null) {
            Skill skill = hero.getType().getSkill(skillType);
            if (skill != null && ((skillCooldowns.get(skillType) == 0 && mana >= skill.getMana(level)) || provider.isCreative()) &&
                    (skill instanceof ToggleSkill || !isSkillActive(skillType))) {
                if (!provider.isCreative()) {
                    skillCooldowns.put(skillType, skill.getCooldown(level));
                    addMana(-skill.getMana(level));
                }
                if (skill instanceof ToggleSkill)
                    this.activeSkills.put(skillType, !isSkillActive(skillType));
                else
                    this.activeSkills.put(skillType, true);

                if (provider.getWorld().isClient) {
                    ClientPlayNetworking.send(ServerEvents.SKILL_PACKET,
                            new SkillPacket(skillType).toPacketByteBuf());
                    provider.sendMessage(Text.literal("Used " + skillType.name().toLowerCase() + " skill"));
                } else {
                    skill.use(provider, this);
                }
            }
        }
    }

    @Override
    public void deactivateSkill(Skill.Type skillType) {
        activeSkills.put(skillType, false);
        sync();
    }

    @Override
    public boolean isSkillActive(Skill.Type skillType) {
        return activeSkills.getOrDefault(skillType, false);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setBlock(int slot, boolean blocked) {
        if (blocked) {
            clientBlockedSlots.add(slot);
        } else {
            clientBlockedSlots.remove(slot);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isBlocked(int slot) {
        return clientBlockedSlots.contains(slot);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Set<Integer> getBlocked() {
        return clientBlockedSlots;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        String heroNbt = tag.getString("hero");
        if (!heroNbt.isEmpty()) {
            var factory = ModRegistries.HERO_TYPES.get(new Identifier(DotaCraft.MOD_ID, heroNbt));
            if (factory != null) {
                setHero(factory.become(provider));
            }
        }
        this.health = tag.getDouble("health");
        this.mana = tag.getDouble("mana");
        this.level = tag.getInt("level");

        for (var type : Skill.Type.values()) {
            skillCooldowns.put(type, 0);
        }
        if (tag.contains("cooldowns", NbtElement.COMPOUND_TYPE)) {
            NbtCompound cooldownsTag = tag.getCompound("cooldowns");
            for (var type : Skill.Type.values()) {
                if (cooldownsTag.contains(type.name(), NbtElement.INT_TYPE)) {
                    skillCooldowns.put(type, cooldownsTag.getInt(type.name()));
                }
            }
        }

        for (var type : Skill.Type.values()) {
            activeSkills.put(type, false);
        }
        if (tag.contains("activeSkills", NbtElement.COMPOUND_TYPE)) {
            NbtCompound activeSkillsTag = tag.getCompound("activeSkills");
            for (var type : Skill.Type.values()) {
                if (activeSkillsTag.contains(type.name(), NbtElement.BYTE_TYPE)) {
                    activeSkills.put(type, activeSkillsTag.getBoolean(type.name()));
                }
            }
        }

        if (hero != null && tag.contains("heroData", NbtElement.COMPOUND_TYPE)) {
            NbtCompound heroTag = tag.getCompound("heroData");
            hero.readFromNbt(heroTag);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        String heroNbt = (hero != null) ? hero.getCustomId() : "";
        tag.putString("hero", heroNbt);
        tag.putDouble("health", health);
        tag.putDouble("mana", mana);
        tag.putInt("level", level);

        NbtCompound cooldownsTag = new NbtCompound();
        for (var entry : skillCooldowns.entrySet()) {
            if (entry.getValue() != 0) {
                cooldownsTag.putInt(entry.getKey().name(), entry.getValue());
            }
        }
        if (!cooldownsTag.isEmpty()) {
            tag.put("cooldowns", cooldownsTag);
        }

        NbtCompound activeSkillsTag = new NbtCompound();
        for (var entry : activeSkills.entrySet()) {
            if (entry.getValue()) {
                activeSkillsTag.putBoolean(entry.getKey().name(), true);
            }
        }
        if (!activeSkillsTag.isEmpty()) {
            tag.put("activeSkills", activeSkillsTag);
        }

        if (hero != null) {
            NbtCompound heroTag = new NbtCompound();
            hero.writeToNbt(heroTag);
            tag.put("heroData", heroTag);
        }
    }
}