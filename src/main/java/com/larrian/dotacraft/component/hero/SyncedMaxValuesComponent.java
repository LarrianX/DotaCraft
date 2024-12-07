package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.larrian.dotacraft.init.ModComponents.MAX_VALUES_COMPONENT;

public class SyncedMaxValuesComponent implements MaxValuesComponent, AutoSyncedComponent {
    public static final double MIN = 1;
    public static final double MAX = 30000;
    private final PlayerEntity provider;
    private double maxMana;
    private double maxHealth;
    private NbtCompound cache;

    public SyncedMaxValuesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtCompound();
    }

    @Override
    public void sync() {
        NbtCompound data = new NbtCompound();
        writeToNbt(data);
        if (!data.equals(this.cache)) {
            provider.syncComponent(MAX_VALUES_COMPONENT);
        }
        this.cache = data;
    }

    @Override
    public double getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMaxMana(double maxMana) {
        this.maxMana = Math.max(Math.min(maxMana, MAX), MIN);
    }

    @Override
    public double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = Math.max(Math.min(maxHealth, MAX), MIN);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.maxMana = Math.max(Math.min(tag.getDouble("maxMana"), MAX), MIN);
        this.maxHealth = Math.max(Math.min(tag.getDouble("maxHealth"), MAX), MIN);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("maxMana", this.maxMana);
        tag.putDouble("maxHealth", this.maxHealth);
    }
}
