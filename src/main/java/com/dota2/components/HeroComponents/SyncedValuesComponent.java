package com.dota2.components.HeroComponents;

import com.dota2.components.ModComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.components.ModComponents.MAX_VALUES_COMPONENT;

public class SyncedValuesComponent implements ValuesComponent, AutoSyncedComponent {
    private final PlayerEntity provider;
    private double mana;
    private double health;

    public SyncedValuesComponent(PlayerEntity provider) {
        this.provider = provider;
    }

    private MaxValuesComponent getMaxValuesComponent() {
        return this.provider.getComponent(MAX_VALUES_COMPONENT);
    }

    @Override
    public void sync() {
        ModComponents.VALUES_COMPONENT.sync(this.provider, this);
    }

    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity player) {
        buf.writeVarInt((int) this.mana);
        buf.writeVarInt((int) this.health);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        this.mana = buf.readVarInt();
        this.health = buf.readVarInt();
    }

    @Override
    public boolean isNotFullMana() {
        return Math.round(this.mana) == (getMaxValuesComponent().getMaxMana());
    }

    @Override
    public void addMana(double mana) {
        this.mana = Math.max(Math.min(this.mana + mana, getMaxValuesComponent().getMaxMana()), 0);
    }

    @Override
    public double getMana() {
        return this.mana;
    }

    @Override
    public void setMana(double mana) {
        this.mana = Math.max(Math.min(mana, getMaxValuesComponent().getMaxMana()), 0);
    }

    @Override
    public boolean isNotFullHealth() {
        return Math.round(this.health) == (getMaxValuesComponent().getMaxHealth());
    }

    @Override
    public void addHealth(double health) {
        this.health = Math.max(Math.min(this.health + health, getMaxValuesComponent().getMaxHealth()), 0);
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.max(Math.min(health, getMaxValuesComponent().getMaxHealth()), 0);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
//        this.mana =   Math.max(Math.min(tag.getDouble("mana"), getMaxValuesComponent().getMaxMana()), 0);
//        this.health = Math.max(Math.min(tag.getDouble("health"), getMaxValuesComponent().getMaxHealth()), 0);
        this.mana = tag.getInt("mana");
        this.health = tag.getInt("health");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("mana", (int) this.mana);
        tag.putInt("health", (int) this.health);
    }
}
