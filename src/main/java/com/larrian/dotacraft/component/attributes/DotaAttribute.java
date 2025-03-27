package com.larrian.dotacraft.component.attributes;

import net.minecraft.entity.player.PlayerEntity;
import java.util.HashMap;
import java.util.Map;

public class DotaAttribute implements IDotaAttribute {
    private final DotaAttributeType type;
    private final PlayerEntity provider;
    private final AttributesComponent attributes;
    private double baseValue;
    private final Map<String, Double> modifiers = new HashMap<>();

    public DotaAttribute(DotaAttributeType type, PlayerEntity provider, AttributesComponent attributes) {
        this.type = type;
        this.provider = provider;
        this.attributes = attributes;
        this.baseValue = 0;
    }

    @Override
    public void set(double value) {
        this.baseValue = Math.max(0, value);
    }

    @Override
    public double getBase() {
        return baseValue;
    }

    @Override
    public void add(double value) {
        set(this.baseValue + value);
    }

    @Override
    public double get() {
        double modifiedValue = baseValue + modifiers.values().stream().mapToDouble(Double::doubleValue).sum();
        double correlationValue = type.getCorrelationValue(provider, attributes, modifiedValue);
        return Math.max(0, correlationValue);
    }

    @Override
    public void addModifier(String key, double amount) {
        modifiers.put(key, amount);
    }

    @Override
    public void removeModifier(String key) {
        modifiers.remove(key);
    }

    @Override
    public void clearModifiers() {
        modifiers.clear();
    }
}
