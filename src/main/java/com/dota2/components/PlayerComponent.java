package com.dota2.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerComponent implements HeroAttributes, AutoSyncedComponent {
    private final PlayerEntity provider;  // or World, or whatever you are attaching to
    private boolean hero;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;

    public PlayerComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    private void sync() {
        ModComponents.HERO_ATTRIBUTES.sync(this.provider);
    }

    @Override
    public boolean isHero() {
        return hero;
    }

    @Override
    public void setHero(boolean hero) {
        this.hero = hero;
        sync();
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
        sync();
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        sync();
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
        sync();
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hero = tag.getBoolean("Hero");
        this.health = tag.getInt("Health");
        this.maxHealth = tag.getInt("MaxHealth");
        this.mana = tag.getInt("Mana");
        this.maxMana = tag.getInt("MaxMana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("Hero", this.hero);
        tag.putInt("Health", this.health);
        tag.putInt("MaxHealth", this.maxHealth);
        tag.putInt("Mana", this.mana);
        tag.putInt("MaxMana", this.maxMana);
    }
}
