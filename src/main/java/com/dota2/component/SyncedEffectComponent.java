package com.dota2.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class SyncedEffectComponent implements EffectComponent, AutoSyncedComponent {
    private final Map<String, Double> amplifiers;
    private final PlayerEntity provider;

    public SyncedEffectComponent(PlayerEntity provider) {
        this.provider = provider;
        this.amplifiers = new HashMap<String, Double>();
    }

    public void sync() {
        ModComponents.EFFECT_COMPONENT.sync(this.provider);
    }

    @Override
    public Map<String, Double> getAmplifiers() {
        return this.amplifiers;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        amplifiers.clear();
        for (String name : tag.getKeys()) {
            amplifiers.put(name, tag.getDouble(name));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        for (Map.Entry<String, Double> entry : amplifiers.entrySet()) {
            tag.putDouble(entry.getKey(), entry.getValue());
        }
    }
}
