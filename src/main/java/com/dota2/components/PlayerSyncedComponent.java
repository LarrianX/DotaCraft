package com.dota2.components;

import com.dota2.commands.BecomeHero;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerSyncedComponent implements HeroAttributes, AutoSyncedComponent {
    private static final int LIMIT = 30000;
    private static NbtCompound CACHE = new NbtCompound();
    private final PlayerEntity provider;  // or World, or whatever you are attaching to
    private boolean hero;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int oldHealth;
    private int oldMaxHealth;

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
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void addHealth(int health) {
        this.health += health;
        if (this.health > this.maxHealth)
            this.health = this.maxHealth;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
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
    }

    @Override
    public void addMana(int mana) {
        this.mana += mana;
        if (this.mana > this.maxMana)
            this.mana = this.maxMana;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public boolean isNotFullMana() {
        return (this.mana != this.maxMana);
    }

    @Override
    public void setOldHealth(int oldHealth) {
        this.oldHealth = oldHealth;
    }

    @Override
    public int getOldHealth() {
        return this.oldHealth;
    }

    @Override
    public void setOldMaxHealth(int oldMaxHealth) {
        this.oldMaxHealth = oldMaxHealth;
    }

    @Override
    public int getOldMaxHealth() {
        return this.oldMaxHealth;
    }

    @Override
    public void serverTick() {
        provider.setHealth(BecomeHero.HEALTH);
        provider.getHungerManager().setFoodLevel(100);

        if (this.maxHealth < 0)
            this.maxHealth = 0;
        else if (this.maxHealth > LIMIT)
            this.maxHealth = LIMIT;

        if (this.health < 0)
            this.health = 0;
        else if (this.health > this.maxHealth)
            this.health = this.maxHealth;

        if (this.maxMana < 0)
            this.maxMana = 0;
        else if (this.maxMana > LIMIT)
            this.maxMana = LIMIT;

        if (this.mana < 0)
            this.mana = 0;
        else if (this.mana > this.maxMana)
            this.mana = this.maxMana;

        NbtCompound data = new NbtCompound();
        writeToNbt(data);
        if (!data.equals(CACHE))
            sync();
        CACHE = data;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hero = tag.getBoolean("Hero");
        this.health = tag.getInt("Health");
        this.maxHealth = tag.getInt("MaxHealth");
        this.mana = tag.getInt("Mana");
        this.maxMana = tag.getInt("MaxMana");
        this.oldHealth = tag.getInt("OldHealth");
        this.oldMaxHealth = tag.getInt("OldMaxHealth");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("Hero", this.hero);
        tag.putInt("Health", this.health);
        tag.putInt("MaxHealth", this.maxHealth);
        tag.putInt("Mana", this.mana);
        tag.putInt("MaxMana", this.maxMana);
        tag.putInt("OldHealth", this.oldHealth);
        tag.putInt("OldMaxHealth", this.oldMaxHealth);
    }
}
