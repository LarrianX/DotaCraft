package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.DotaEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RuneDoubleDamageEffect extends DotaEffect {
    private static final String ID = "rune_double_damage";

    public RuneDoubleDamageEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751, ID);
    }
}
