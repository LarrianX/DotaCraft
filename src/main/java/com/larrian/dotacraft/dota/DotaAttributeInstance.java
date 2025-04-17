package com.larrian.dotacraft.dota;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class DotaAttributeInstance {
    private final DotaAttribute type;
    private final PlayerEntity player;
    private double baseValue;
    private final Map<String, Double> modifiers = new HashMap<>();

    public DotaAttributeInstance(DotaAttribute type, PlayerEntity player) {
        this.type = type;
        this.player = player;
        this.baseValue = 0;
    }

    public void set(double value) {
        this.baseValue = Math.max(0, value);
    }

    public double getBase() {
        return baseValue;
    }

    public void add(double value) {
        set(this.baseValue + value);
    }

    public double get() {
        double modifiedValue = baseValue + modifiers.values().stream().mapToDouble(Double::doubleValue).sum();
        double value = this.type.get(this.player, modifiedValue);
        return Math.max(0, value);
    }

    public void addModifier(String key, double amount) {
        modifiers.put(key, amount);
    }

    public void removeModifier(String key) {
        modifiers.remove(key);
    }

    public void clearModifiers() {
        modifiers.clear();
    }
}
