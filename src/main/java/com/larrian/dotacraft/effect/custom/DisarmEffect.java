package com.larrian.dotacraft.effect.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.DotaEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class DisarmEffect extends DotaEffect {
    private static final String ID = "disarm";

    public DisarmEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, ID);
    }
}
