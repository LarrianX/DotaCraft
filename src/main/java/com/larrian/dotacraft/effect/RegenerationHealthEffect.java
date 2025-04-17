package com.larrian.dotacraft.effect;

import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class RegenerationHealthEffect extends DotaEffect {
    private final double amplifier;

    public RegenerationHealthEffect(StatusEffectCategory category, int color, String id, double amplifier) {
        super(category, color, id);
        this.amplifier = amplifier;
    }

    public double getAmplifier() {
        return amplifier;
    }
}
