package com.dota2.component.hero;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.dota2.component.ModComponents.MAX_VALUES_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public class SyncedValuesComponent implements ValuesComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private final NbtCompound cache;
    private double mana;
    private double health;

    public SyncedValuesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtCompound();
    }

    private MaxValuesComponent getMaxValuesComponent() {
        return this.provider.getComponent(MAX_VALUES_COMPONENT);
    }

    @Override
    public void sync() {
        if (
                this.cache.isEmpty() ||
                        this.cache.getDouble("mana") != this.mana ||
                        this.cache.getDouble("health") != this.health
        ) {
            provider.syncComponent(VALUES_COMPONENT);
            writeToNbt(this.cache);
        }
    }

    @Override
    public boolean isNotFullMana() {
        return Math.round(this.mana) == (getMaxValuesComponent().getMaxMana());
    }

    @Override
    public void addMana(double mana) {
        this.mana = Math.max(Math.min(this.mana + mana, getMaxValuesComponent().getMaxMana()), 0);
    }

    @Override
    public double getMana() {
        return this.mana;
    }

    @Override
    public void setMana(double mana) {
        this.mana = Math.max(Math.min(mana, getMaxValuesComponent().getMaxMana()), 0);
    }

    @Override
    public boolean isNotFullHealth() {
        return Math.round(this.health) == (getMaxValuesComponent().getMaxHealth());
    }

    @Override
    public void addHealth(double health) {
        this.health = Math.max(Math.min(this.health + health, getMaxValuesComponent().getMaxHealth()), 0);
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.max(Math.min(health, getMaxValuesComponent().getMaxHealth()), 0);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.mana = tag.getDouble("mana");
        this.health = tag.getDouble("health");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("mana", this.mana);
        tag.putDouble("health", this.health);
    }
}
