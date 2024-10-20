package com.dota2.component.hero;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.dota2.component.ModComponents.MAX_VALUES_COMPONENT;

public class SyncedOldValuesComponent implements OldValuesComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private int oldHealth;
    private int oldMaxHealth;
    private NbtCompound cache;

    public SyncedOldValuesComponent(PlayerEntity provider) {
        this.provider = provider;
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
    public int getOldHealth() {
        return this.oldHealth;
    }

    @Override
    public void setOldHealth(int oldHealth) {
        this.oldHealth = oldHealth;
    }

    @Override
    public int getOldMaxHealth() {
        return this.oldMaxHealth;
    }

    @Override
    public void setOldMaxHealth(int oldMaxHealth) {
        this.oldMaxHealth = oldMaxHealth;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.oldMaxHealth = tag.getInt("oldHealth");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("oldHealth", oldHealth);
    }
}
