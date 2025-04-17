package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.DotaEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class RuneRegenerationEffect extends DotaEffect {
    private static final String ID = "rune_regeneration";

    public RuneRegenerationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751, ID);
    }
}