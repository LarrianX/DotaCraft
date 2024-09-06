package com.dota2.components;

import net.minecraft.nbt.NbtCompound;

public class PlayerNonSyncedComponent implements EffectAttributes {
    private int tickMana;
    private int tickHealth;

    @Override
    public int getTickMana() {
        return tickMana;
    }

    @Override
    public void setTickMana(int tickMana) {
        this.tickMana = tickMana;
    }

    @Override
    public int getTickHealth() {
        return tickHealth;
    }

    @Override
    public void setTickHealth(int tickHealth) {
        this.tickHealth = tickHealth;
    }

    @Override
    public void serverTick() {
        if (this.tickMana > 0)
            this.tickMana--;
        else if (this.tickMana < 0)
            this.tickMana = 0;

        if (this.tickHealth > 0)
            this.tickHealth--;
        else if (this.tickHealth < 0)
            this.tickHealth = 0;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.tickHealth = tag.getInt("tickHealth");
        this.tickMana = tag.getInt("tickMana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("tickHealth", this.tickHealth);
        tag.putInt("tickMana", this.tickMana);
    }
}
