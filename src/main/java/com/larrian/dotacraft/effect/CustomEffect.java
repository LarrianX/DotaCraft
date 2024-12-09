package com.larrian.dotacraft.effect;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class CustomEffect extends StatusEffect implements Custom {
    public static final double ERROR = 0.00000000001;
    private final boolean persistent;

    protected CustomEffect(StatusEffectCategory category, int color, boolean persistent) {
        super(category, color);
        this.persistent = persistent;
    }

    public boolean isPersistent() {
        return persistent;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
