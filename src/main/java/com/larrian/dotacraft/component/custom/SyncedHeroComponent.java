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

import static com.larrian.dotacraft.DotaCraft.TICKS_PER_SECOND;
import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, AutoSyncedComponent {
    private static final int LEVEL_LIMIT = 30;

    private final PlayerEntity provider;
    private DotaHero hero;
    private double health;
    private double mana;
    private int level;
    private final EnumMap<Skill.Type, SkillInstance> skills;
    @Environment(EnvType.CLIENT)
    private Set<Integer> clientBlockedSlots;
    private NbtList cache;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtList();
        this.skills = new EnumMap<>(Skill.Type.class);
        if (provider.getWorld().isClient) {
            this.clientBlockedSlots = new HashSet<>();
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

        for (var entry : skills.entrySet()) {
            SkillInstance skillInstance = entry.getValue();
            if (skillInstance.getCooldown() > 0) {
                skillInstance.setCooldown(skillInstance.getCooldown() - 1);
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
        return isHero() ? hero.getType() : null;
    }

    @Override
    public boolean isHero() {
        return hero != null;
    }

    @Override
    public void setHero(DotaHero hero) {
        this.hero = hero;
        if (isHero()) {
            for (Skill.Type skillType : Skill.Type.values()) {
                SkillInstance skillInstance = new SkillInstance();
                skillInstance.setMaxLevel(getHero().getType().getSkill(skillType).getMaxLevel());
                skills.put(skillType, skillInstance);
            }
        }
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
    public SkillInstance getSkillInstance(Skill.Type type) {
        return skills.get(type);
    }

    @Override
    public void useSkill(Skill.Type skillType) {
        if (isHero()) {
            Skill skill = hero.getType().getSkill(skillType);
            SkillInstance skillInstance = getSkillInstance(skillType);

            if (skill != null && ((skillInstance.getCooldown() == 0 && mana >= skill.getMana(skillInstance.getLevel())) || provider.isCreative()) &&
                    (skill instanceof ToggleSkill || !skillInstance.isActive())) {
                if (!provider.isCreative()) {
                    skillInstance.setCooldown(skill.getCooldowns()[skillInstance.getLevel() - 1] * TICKS_PER_SECOND);
                    addMana(-skill.getMana(skillInstance.getLevel()));
                }
                if (skill instanceof ToggleSkill)
                    skillInstance.setActive(!skillInstance.isActive());
                else
                    skillInstance.setActive(true);

                if (provider.getWorld().isClient) {
                    ClientPlayNetworking.send(ServerEvents.SKILL_PACKET,
                            new SkillPacket(skillType).toPacketByteBuf());
                    provider.sendMessage(Text.literal("Used " + skillType.name().toLowerCase() + " skill"));
                }
                skill.use(provider, skillInstance);
            }
        }
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

                if (tag.contains("hero_data", NbtElement.COMPOUND_TYPE)) {
                    NbtCompound heroTag = tag.getCompound("heroData");
                    hero.readFromNbt(heroTag);
                }

                if (tag.contains("skills", NbtElement.COMPOUND_TYPE)) {
                    NbtCompound skillsTag = tag.getCompound("skills");
                    for (Skill.Type type : Skill.Type.values()) {
                        if (skillsTag.contains(type.name().toLowerCase(), NbtElement.COMPOUND_TYPE)) {
                            NbtCompound skillTag = skillsTag.getCompound(type.name().toLowerCase());

                            SkillInstance skillInstance = skills.get(type);
                            skillInstance.readFromNbt(skillTag);
                        }
                    }
                }
            }
        } else {
            setHero(null);
        }
        this.health = tag.getDouble("health");
        this.mana = tag.getDouble("mana");
        this.level = tag.getByte("level");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        String heroNbt = (isHero()) ? hero.getCustomId() : "";
        tag.putString("hero", heroNbt);
        tag.putDouble("health", health);
        tag.putDouble("mana", mana);
        tag.putByte("level", (byte) level);

        if (isHero()) {
            NbtCompound heroTag = new NbtCompound();
            hero.writeToNbt(heroTag);
            tag.put("hero_data", heroTag);

            NbtCompound skillsTag = new NbtCompound();
            for (var entry : skills.entrySet()) {
                Skill.Type skillType = entry.getKey();
                SkillInstance skillInstance = entry.getValue();

                NbtCompound skillTag = new NbtCompound();
                skillInstance.writeToNbt(skillTag);
                skillsTag.put(skillType.name().toLowerCase(), skillTag);
            }
            if (!skillsTag.isEmpty()) {
                tag.put("skills", skillsTag);
            }
        }
    }
}