package com.larrian.dotacraft.component.custom;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.event.network.AutoCraftPacket;
import com.larrian.dotacraft.event.ServerEvents;
import com.larrian.dotacraft.event.network.SkillPacket;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.hero.DotaHeroType;
import com.larrian.dotacraft.hero.Skill;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.ModRegistries;
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
    private final PlayerEntity provider;
    private DotaHero hero;

    public SyncedHeroComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    @Override
    public void sync() {
        provider.syncComponent(HERO_COMPONENT);
    }

    @Override
    public void serverTick() {
        if (isHero()) {
            getHero().serverTick();
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void clientTick() {
        if (isHero()) {
            getHero().clientTick();
        }
    }

    @Override
    public DotaHero getHero() {
        return hero;
    }

    @Override
    public DotaHeroType<?> getHeroType() {
        return getHero().getType();
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
        return getHero().getHealth();
    }

    @Override
    public boolean isFullHealth() {
        return getHero().isFullHealth();
    }

    @Override
    public void setHealth(double health) {
        getHero().setHealth(health);
    }

    @Override
    public void addHealth(double amount) {
        getHero().addHealth(amount);
    }

    @Override
    public double getMana() {
        return getHero().getMana();
    }

    @Override
    public boolean isFullMana() {
        return getHero().isFullMana();
    }

    @Override
    public void setMana(double mana) {
        getHero().setMana(mana);
    }

    @Override
    public void addMana(double amount) {
        getHero().addMana(amount);
    }

    @Override
    public int getLevel() {
        return getHero().getLevel();
    }

    @Override
    public void setLevel(int level) {
        getHero().setLevel(level);
    }

    @Override
    public void addLevel(int level) {
        getHero().addLevel(level);
    }

    @Override
    public EnumMap<Skill.Type, Integer> getSkillCooldowns() {
        return getHero().getSkillCooldowns();
    }

    @Override
    public void useSkill(Skill.Type skillType) {
        getHero().useSkill(skillType);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setBlock(int slot, boolean blocked) {
        getHero().setBlock(slot, blocked);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isBlocked(int slot) {
        return getHero().isBlocked(slot);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Set<Integer> getBlocked() {
        return getHero().getBlocked();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        String heroNbt = tag.getString("hero");
        if (!heroNbt.isEmpty()) {
            var factory = ModRegistries.HEROES.get(new Identifier(DotaCraft.MOD_ID, heroNbt));
            if (factory != null) {
                setHero(factory.become(provider));
                getHero().readFromNbt(tag);
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        String heroNbt = isHero() ? hero.getCustomId() : "";
        tag.putString("hero", heroNbt);
        if (isHero()) {
            getHero().writeToNbt(tag);
        }
    }
}