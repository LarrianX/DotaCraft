package com.larrian.dotacraft.component;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.event.AutoCraftPacket;
import com.larrian.dotacraft.event.ServerEvents;
import com.larrian.dotacraft.event.SkillPacket;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.hero.Skill;
import com.larrian.dotacraft.init.ModAttributes;
import com.larrian.dotacraft.init.ModRegistries;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class SyncedHeroComponent implements HeroComponent, AutoSyncedComponent {
    private static final int LEVEL_LIMIT = 30;

    private final PlayerEntity provider;
    private DotaHero hero;
    private double health;
    private double mana;
    private int level;
    private EnumMap<Skill.Type, Integer> skillCooldowns;
    private final Set<Integer> clientBlockedSlots;

    private NbtList cache;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
        this.skillCooldowns = new EnumMap<>(Skill.Type.class);
        for (var type : Skill.Type.values()) {
            skillCooldowns.put(type, 0);
        }
        this.clientBlockedSlots = new HashSet<>();
        this.cache = new NbtList();
    }

    private AttributesComponent getAttributesComponent() {
        return this.provider.getComponent(ATTRIBUTES_COMPONENT);
    }

    @Override
    public void sync() {
        provider.syncComponent(HERO_COMPONENT);
    }

    private void tick() {
        AttributesComponent attributes = getAttributesComponent();
        addHealth(attributes.getAttribute(ModAttributes.REGENERATION_HEALTH).get() / 20);
        addMana(attributes.getAttribute(ModAttributes.REGENERATION_MANA).get() / 20);

        skillCooldowns.replaceAll((k, v) -> Math.max(0, v - 1));
    }

    @Override
    public void serverTick() {
        tick();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void clientTick() {
        tick();
        if (isHero() && DotaCraft.AUTO_CRAFT) {
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

    @Override
    public DotaHero getHero() {
        return hero;
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
        return getHealth() == getAttributesComponent().getAttribute(ModAttributes.MAX_HEALTH).get();
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.max(0, Math.min(health, getAttributesComponent().getAttribute(ModAttributes.MAX_HEALTH).get()));
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
        return getMana() == getAttributesComponent().getAttribute(ModAttributes.MAX_MANA).get();
    }

    @Override
    public void setMana(double mana) {
        this.mana = Math.max(0, Math.min(mana, getAttributesComponent().getAttribute(ModAttributes.MAX_MANA).get()));
    }

    @Override
    public void addMana(double amount) {
        setMana(this.mana + amount);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(level, LEVEL_LIMIT));
    }

    @Override
    public void addLevel(int add) {
        setLevel(getLevel() + add);
    }

    @Override
    public EnumMap<Skill.Type, Integer> getSkillCooldowns() {
        return skillCooldowns;
    }

    @Override
    public void setBlock(int slot, boolean blocked) {
        if (blocked) {
            clientBlockedSlots.add(slot);
        } else {
            clientBlockedSlots.remove(slot);
        }
    }

    @Override
    public boolean isBlocked(int slot) {
        return clientBlockedSlots.contains(slot);
    }

    @Override
    public Set<Integer> getBlocked() {
        return clientBlockedSlots;
    }

    @Override
    public void useSkill(Skill.Type type) {
        if (isHero()) {
            Skill skill = hero.getSkill(type);
            if (skillCooldowns.get(type) == 0 && getMana() >= skill.getMana()) {
                skillCooldowns.put(type, skill.getCooldown(getLevel()));
                addMana(-skill.getMana());
                if (provider.getWorld().isClient) {
                    ClientPlayNetworking.send(ServerEvents.SKILL_PACKET,
                            new SkillPacket(type).toPacketByteBuf());
                } else {
                    skill.use(provider);
                }
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        String heroNbt = tag.getString("hero");
        this.hero = heroNbt.isEmpty() ? null : ModRegistries.HEROES.get(new Identifier(DotaCraft.MOD_ID, heroNbt));
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

    @Override
    public void writeToNbt(NbtCompound tag) {
        String heroNbt = isHero() ? hero.getId() : "";
        tag.putString("hero", heroNbt);
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
