package com.dota2.component.hero;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class NonSyncedOldValuesComponent implements OldValuesComponent {
    private int oldHealth;
    private int oldMaxHealth;

    public NonSyncedOldValuesComponent(PlayerEntity provider) {
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
