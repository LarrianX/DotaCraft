package com.larrian.dotacraft.effect;

import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class RegenerationManaEffect extends DotaEffect {
    private final double amplifier;

    public RegenerationManaEffect(StatusEffectCategory category, int color, String id, double amplifier) {
        super(category, color, id);
        this.amplifier = amplifier;
    }

    public double getAmplifier() {
        return amplifier;
    }
}
