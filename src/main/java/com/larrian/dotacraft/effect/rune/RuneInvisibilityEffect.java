package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class RuneInvisibilityEffect extends StatusEffect implements Custom {
    private static final String ID = "rune_invisibility";

    public RuneInvisibilityEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 200, amplifier, false, true));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public String getId() {
        return ID;
    }
}