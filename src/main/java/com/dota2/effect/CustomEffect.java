package com.dota2.effect;

import com.dota2.Custom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class CustomEffect extends StatusEffect implements Custom {
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

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
    }
}
