package com.larrian.dotacraft.attributes;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

/* Base attribute class with basic functionality. */
public class DotaAttribute implements IDotaAttribute {

    // Basic fields
    protected final PlayerEntity provider;
    protected final AttributesComponent attributes;
    protected double baseValue;
    protected final Map<String, Double> modifiers = new HashMap<>();

    public DotaAttribute(PlayerEntity provider, AttributesComponent attributes) {
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

    // Base get() method simply returns the base value plus modifiers.
    @Override
    public double get() {
        double modifiedValue = baseValue + modifiers.values().stream().mapToDouble(Double::doubleValue).sum();
        return Math.max(0, modifiedValue);
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
