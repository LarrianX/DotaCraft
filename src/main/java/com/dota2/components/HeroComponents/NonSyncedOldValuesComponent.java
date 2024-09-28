package com.dota2.components.HeroComponents;

import com.dota2.components.ModComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class NonSyncedOldValuesComponent implements OldValuesComponent {
    private final PlayerEntity provider;
    private int oldHealth;
    private int oldMaxHealth;
    private NbtCompound cache;

    public NonSyncedOldValuesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtCompound();
    }

    private void sync() {
        NbtCompound data = new NbtCompound();
        writeToNbt(data);
        if (!data.equals(this.cache)) {
            ModComponents.OLD_VALUES_COMPONENT.sync(this.provider);
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
