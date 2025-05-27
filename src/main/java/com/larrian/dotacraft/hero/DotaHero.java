package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.event.ServerEvents;
import com.larrian.dotacraft.event.network.AutoCraftPacket;
import com.larrian.dotacraft.event.network.SkillPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public abstract class DotaHero implements Custom {
    private static final int LEVEL_LIMIT = 30;
    private static final int TICKS_PER_SECOND = 20;

    private final DotaHeroType<?> type;
    private final PlayerEntity provider;

    private double health;
    private double mana;
    private int level;
    private final EnumMap<Skill.Type, Integer> skillCooldowns;
    private final EnumMap<Skill.Type, Boolean> activeSkills;
    @Environment(EnvType.CLIENT)
    private Set<Integer> clientBlockedSlots;

    private final AttributesComponent _attributes;
    private final HeroComponent _hero;
    private NbtList cache;

    protected DotaHero(DotaHeroType<?> type, PlayerEntity provider) {
        this.type = type;
        this.provider = provider;
        this._attributes = provider.getComponent(ATTRIBUTES_COMPONENT);
        this._hero = provider.getComponent(HERO_COMPONENT);
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

    public void sync() {
        _hero.sync();
    }

    private void tick() {
        double healthRegen = _attributes.getAttribute(ModAttributes.REGENERATION_HEALTH).get() / TICKS_PER_SECOND;
        double manaRegen = _attributes.getAttribute(ModAttributes.REGENERATION_MANA).get() / TICKS_PER_SECOND;
        addHealth(healthRegen);
        addMana(manaRegen);

        for (var entry : skillCooldowns.entrySet()) {
            int currentCooldown = entry.getValue();
            if (currentCooldown > 0) {
                entry.setValue(currentCooldown - 1);
            }
        }
    }

    public void serverTick() {
        tick();
    }

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
    }

    public String getAdditionalInfo() {
        return "";
    }

    public DotaHeroType<?> getType() {
        return type;
    }

    public double getHealth() {
        return health;
    }

    public boolean isFullHealth() {
        return health == _attributes.getAttribute(ModAttributes.MAX_HEALTH).get();
    }

    public void setHealth(double health) {
        double maxHealth = _attributes.getAttribute(ModAttributes.MAX_HEALTH).get();
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public void addHealth(double amount) {
        setHealth(this.health + amount);
    }

    public double getMana() {
        return mana;
    }

    public boolean isFullMana() {
        return mana == _attributes.getAttribute(ModAttributes.MAX_MANA).get();
    }

    public void setMana(double mana) {
        double maxMana = _attributes.getAttribute(ModAttributes.MAX_MANA).get();
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public void addMana(double amount) {
        setMana(this.mana + amount);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(level, LEVEL_LIMIT));
    }

    public void addLevel(int add) {
        setLevel(level + add);
    }

    public EnumMap<Skill.Type, Integer> getSkillCooldowns() {
        return skillCooldowns;
    }

    public void useSkill(Skill.Type skillType) {
        Skill skill = type.getSkill(skillType);
        if (skill != null &&
                ((skillCooldowns.get(skillType) == 0 && mana >= skill.getMana(level)) || provider.isCreative())) {
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

    public void deactivateSkill(Skill.Type skillType) {
        activeSkills.put(skillType, false);
        sync();
    }

    public boolean isSkillActive(Skill.Type skillType) {
        return activeSkills.getOrDefault(skillType, false);
    }

    @Environment(EnvType.CLIENT)
    public void setBlock(int slot, boolean blocked) {
        if (blocked) {
            clientBlockedSlots.add(slot);
        } else {
            clientBlockedSlots.remove(slot);
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean isBlocked(int slot) {
        return clientBlockedSlots.contains(slot);
    }

    @Environment(EnvType.CLIENT)
    public Set<Integer> getBlocked() {
        return clientBlockedSlots;
    }

    @Override
    public String getCustomId() {
        return type.getCustomId();
    }

    public void readFromNbt(NbtCompound tag) {
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
    }

    public void writeToNbt(NbtCompound tag) {
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
    }
}