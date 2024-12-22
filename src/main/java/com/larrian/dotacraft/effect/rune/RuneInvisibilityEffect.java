package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RuneInvisibilityEffect extends StatusEffect implements Custom {
    private static final String ID = "rune_invisibility";

    public RuneInvisibilityEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
    }

    @Override
    public String getId() {
        return ID;
    }
}