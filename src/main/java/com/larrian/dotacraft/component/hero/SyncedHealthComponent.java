package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.HEALTH_COMPONENT;

public class SyncedHealthComponent implements HealthComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private double health;

    public SyncedHealthComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    @Override
    public void sync() {
        provider.syncComponent(HEALTH_COMPONENT);
    }

    @Override
    public void tick() {
        add(provider.getAttributeValue(REGENERATION_HEALTH));
    }

    @Override
    public boolean isFull() {
        return this.health == provider.getAttributeValue(MAX_HEALTH);
    }

    @Override
    public void add(double health) {
        set(get() + health);
    }

    @Override
    public double get() {
        return this.health;
    }

    @Override
    public void set(double health) {
        this.health = Math.max(Math.min(health, provider.getAttributeValue(MAX_HEALTH)), 0);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.health = tag.getDouble("health");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("health", this.health);
    }
}