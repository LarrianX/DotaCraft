package com.larrian.dotacraft.component.hero;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.MANA_COMPONENT;

public class SyncedManaComponent implements ManaComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private double mana;

    public SyncedManaComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    @Override
    public void sync() {
        provider.syncComponent(MANA_COMPONENT);
    }

    @Override
    public void tick() {
        addMana(provider.getAttributeValue(REGENERATION_MANA));
    }

    @Override
    public boolean isNotFullMana() {
        return this.mana == provider.getAttributeValue(MAX_MANA);
    }

    @Override
    public void addMana(double mana) {
        setMana(getMana() + mana);
    }

    @Override
    public double getMana() {
        return this.mana;
    }

    @Override
    public void setMana(double mana) {
        this.mana = Math.max(Math.min(mana, provider.getAttributeValue(MAX_MANA)), 0);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.mana = tag.getDouble("mana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("mana", this.mana);
    }
}