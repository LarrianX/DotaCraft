package com.dota2.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerSyncedComponent implements HeroAttributes, AutoSyncedComponent {
    private final PlayerEntity provider;  // or World, or whatever you are attaching to
    private boolean hero;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;

    public PlayerSyncedComponent(PlayerEntity provider) {
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
    public boolean isNotFullHealth() {
        return (this.health != this.maxHealth);
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
    public boolean isNotFullMana() {
        return (this.mana != this.maxMana);
    }

    @Override
    public void serverTick() {
        if (this.maxHealth < 0)
            this.maxHealth = 0;
        else if (this.maxHealth > 30000)
            this.maxHealth = 30000;
        maxHealth = 30000;
        if (this.health < 0)
            this.health = 0;

        if (this.maxMana < 0)
            this.maxMana = 0;
        else if (this.maxMana > 30000)
            this.maxMana = 30000;
        maxMana = 30000;
        if (this.mana < 0)
            this.mana = 0;
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
