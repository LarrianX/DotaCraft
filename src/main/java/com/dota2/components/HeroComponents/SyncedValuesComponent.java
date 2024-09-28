package com.dota2.components.HeroComponents;

import com.dota2.components.ModComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.dota2.components.ModComponents.MAX_VALUES_COMPONENT;

public class SyncedValuesComponent implements ValuesComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private double mana;
    private double health;
    private NbtCompound cache;

    public SyncedValuesComponent(PlayerEntity provider) {
        this.provider = provider;
        this.cache = new NbtCompound();
    }

    private MaxValuesComponent getMaxValuesComponent() {
        return this.provider.getComponent(MAX_VALUES_COMPONENT);
    }

    private void sync() {
        NbtCompound data = new NbtCompound();
        writeToNbt(data);
        if (!data.equals(this.cache)) {
            ModComponents.VALUES_COMPONENT.sync(this.provider);
        }
        this.cache = data;
    }

    @Override
    public boolean isNotFullMana() {
        return Math.round(this.mana) == (getMaxValuesComponent().getMaxMana());
    }

    @Override
    public void addMana(double mana) {
        this.mana = Math.max(Math.min(this.mana + mana, getMaxValuesComponent().getMaxMana()), 0);
        sync();
    }

    @Override
    public double getMana() {
        return this.mana;
    }

    @Override
    public void setMana(double mana) {
        this.mana = Math.max(Math.min(mana, getMaxValuesComponent().getMaxMana()), 0);
        sync();
    }

    @Override
    public boolean isNotFullHealth() {
        return Math.round(this.health) == (getMaxValuesComponent().getMaxHealth());
    }

    @Override
    public void addHealth(double health) {
        this.health = Math.max(Math.min(this.health + health, getMaxValuesComponent().getMaxHealth()), 0);
        sync();
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.max(Math.min(health, getMaxValuesComponent().getMaxHealth()), 0);
        sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
//        this.mana =   Math.max(Math.min(tag.getDouble("mana"), getMaxValuesComponent().getMaxMana()), 0);
//        this.health = Math.max(Math.min(tag.getDouble("health"), getMaxValuesComponent().getMaxHealth()), 0);
        this.mana = tag.getDouble("mana");
        this.health = tag.getDouble("health");
        System.out.println("Reading NBT: Mana = " + this.mana + ", Health = " + this.health);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("mana", this.mana);
        tag.putDouble("health", this.health);
    }
}
