package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_HEALTH;
import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_MANA;
import static com.larrian.dotacraft.init.ModAttributes.MAX_HEALTH;
import static com.larrian.dotacraft.init.ModAttributes.MAX_MANA;
import static com.larrian.dotacraft.init.ModComponents.VALUES_COMPONENT;

public class SyncedValuesComponent implements ValuesComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private final NbtCompound cache;
    private double mana;
    private double health;

    public SyncedValuesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtCompound();
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

    private void increaseValues() {
        addMana(provider.getAttributeBaseValue(REGENERATION_MANA));
        addHealth(provider.getAttributeBaseValue(REGENERATION_HEALTH));
    }

    @Override
    public void serverTick() {
        increaseValues();
    }

    @Override
    public void clientTick() {
        increaseValues();
    }

    @Override
    public boolean isNotFullMana() {
        return Math.round(this.mana) == provider.getAttributeBaseValue(MAX_MANA);
    }

    @Override
    public void addMana(double mana) {
        this.mana = Math.max(Math.min(this.mana + mana, provider.getAttributeBaseValue(MAX_MANA)), 0);
    }

    @Override
    public double getMana() {
        return this.mana;
    }

    @Override
    public void setMana(double mana) {
        this.mana = Math.max(Math.min(mana, provider.getAttributeBaseValue(MAX_MANA)), 0);
    }

    @Override
    public boolean isNotFullHealth() {
        return Math.round(this.health) == provider.getAttributeBaseValue(MAX_HEALTH);
    }

    @Override
    public void addHealth(double health) {
        this.health = Math.max(Math.min(this.health + health, provider.getAttributeBaseValue(MAX_HEALTH)), 0);
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.max(Math.min(health, provider.getAttributeBaseValue(MAX_HEALTH)), 0);
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