package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.DotaEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RuneInvisibilityEffect extends DotaEffect {
    private static final String ID = "rune_invisibility";

    public RuneInvisibilityEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751, ID);
    }
}