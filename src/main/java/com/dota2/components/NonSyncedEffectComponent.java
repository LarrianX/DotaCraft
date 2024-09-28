package com.dota2.components;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class NonSyncedEffectComponent implements EffectComponent {
    private final Map<String, Double> amplifiers;

    public NonSyncedEffectComponent(PlayerEntity provider) {
        this.amplifiers = new HashMap<String, Double>();
    }

    @Override
    public Map<String, Double> getAmplifiers() {
        return this.amplifiers;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        for (String name : tag.getKeys()) {
            amplifiers.put(name, tag.getDouble(name));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        for (String name : this.amplifiers.keySet()) {
            tag.putDouble(name, this.amplifiers.get(name));
        }
    }
}
