package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.component.custom.AttributesComponent;
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

public abstract class DotaHero implements Custom {
    private static final int LEVEL_LIMIT = 30;

    private final DotaHeroType<?> type;
    private final PlayerEntity provider;

    private double health;
    private double mana;
    private int level;
    private final EnumMap<Skill.Type, Integer> skillCooldowns;
    @Environment(EnvType.CLIENT)
    private Set<Integer> clientBlockedSlots;

    // cached
    private final AttributesComponent attributes;
    private NbtList cache;

    // static values
    protected DotaHero(DotaHeroType<?> type, PlayerEntity provider) {
        this.type = type;
        this.provider = provider;
        this.attributes = provider.getComponent(ATTRIBUTES_COMPONENT);
        this.skillCooldowns = new EnumMap<>(Skill.Type.class);
        if (provider.getWorld().isClient) {
            this.clientBlockedSlots = new HashSet<>();
        }
        this.cache = new NbtList();
        for (var skillType : Skill.Type.values()) {
            skillCooldowns.put(skillType, 0);
        }
    }

    private void tick() {
        addHealth(attributes.getAttribute(ModAttributes.REGENERATION_HEALTH).get() / 20);
        addMana(attributes.getAttribute(ModAttributes.REGENERATION_MANA).get() / 20);

        skillCooldowns.replaceAll((k, v) -> Math.max(0, v - 1));
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

    public DotaHeroType<?> getType() {
        return type;
    }

    public double getHealth() {
        return health;
    }

    public boolean isFullHealth() {
        return getHealth() == attributes.getAttribute(ModAttributes.MAX_HEALTH).get();
    }

    public void setHealth(double health) {
        this.health = Math.max(0, Math.min(health, attributes.getAttribute(ModAttributes.MAX_HEALTH).get()));
    }

    public void addHealth(double amount) {
        setHealth(this.health + amount);
    }

    public double getMana() {
        return mana;
    }

    public boolean isFullMana() {
        return getMana() == attributes.getAttribute(ModAttributes.MAX_MANA).get();
    }

    public void setMana(double mana) {
        this.mana = Math.max(0, Math.min(mana, attributes.getAttribute(ModAttributes.MAX_MANA).get()));
    }

    public void addMana(double amount) {
        setMana(this.mana + amount);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(level, LEVEL_LIMIT));
    }

    public void addLevel(int add) {
        setLevel(getLevel() + add);
    }

    public EnumMap<Skill.Type, Integer> getSkillCooldowns() {
        return skillCooldowns;
    }

    public void useSkill(Skill.Type skillType) {
        Skill skill = type.getSkill(skillType);
        if ((skillCooldowns.get(skillType) == 0 && getMana() >= skill.getMana(getLevel())) ||
                provider.isCreative()) {
            if (!provider.isCreative()) {
                skillCooldowns.put(skillType, skill.getCooldown(getLevel()));
                addMana(-skill.getMana(getLevel()));
            }
            if (provider.getWorld().isClient) {
                ClientPlayNetworking.send(ServerEvents.SKILL_PACKET,
                        new SkillPacket(skillType).toPacketByteBuf());
                provider.sendMessage(Text.literal("Used " + skillType.name().toLowerCase() + " skill"));
            } else {
                skill.use(provider);
            }
        }
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

        if (tag.contains("cooldowns", NbtElement.COMPOUND_TYPE)) {
            NbtCompound cooldownsTag = tag.getCompound("cooldowns");
            for (var type : Skill.Type.values()) {
                if (cooldownsTag.contains(type.name(), NbtElement.INT_TYPE)) {
                    skillCooldowns.put(type, cooldownsTag.getInt(type.name()));
                }
            }
        }
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("health", health);
        tag.putDouble("mana", mana);
        tag.putInt("level", this.level);

        NbtCompound cooldownsTag = new NbtCompound();
        for (var entry : skillCooldowns.entrySet()) {
            cooldownsTag.putInt(entry.getKey().name(), entry.getValue());
        }
        tag.put("cooldowns", cooldownsTag);
    }
}
