package com.larrian.dotacraft.component.attributes;

import net.minecraft.entity.player.PlayerEntity;
import java.util.HashMap;
import java.util.Map;

public class DotaAttribute {
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

    public void set(double value) {
        this.baseValue = value;
    }

    public double getBase() {
        return baseValue;
    }

    public void add(double value) {
        set(this.baseValue + value);
    }

    public double get() {
        double modifiedValue = baseValue + modifiers.values().stream().mapToDouble(Double::doubleValue).sum();
        double correlationValue = type.getCorrelationValue(provider, attributes);
        return modifiedValue + correlationValue;
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
