package com.dota2.component.HeroComponent;

import com.dota2.component.ModComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class SyncedMaxValuesComponent implements MaxValuesComponent, AutoSyncedComponent {
    public static final int LIMIT = 30000;
    private final PlayerEntity provider;
    private int maxMana;
    private int maxHealth;
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
            ModComponents.MAX_VALUES_COMPONENT.sync(this.provider);
        }
        this.cache = data;
    }

    @Override
    public int getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMaxMana(int maxMana) {
        this.maxMana = Math.max(Math.min(maxMana, LIMIT), 0);
    }

    @Override
    public int getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = Math.max(Math.min(maxHealth, LIMIT), 0);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.maxMana = Math.max(Math.min(tag.getInt("maxMana"), LIMIT), 0);
        this.maxHealth = Math.max(Math.min(tag.getInt("maxHealth"), LIMIT), 0);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("maxMana", this.maxMana);
        tag.putInt("maxHealth", this.maxHealth);
    }
}
