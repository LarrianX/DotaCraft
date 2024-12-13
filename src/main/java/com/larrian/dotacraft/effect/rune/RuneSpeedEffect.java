package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RuneSpeedEffect extends StatusEffect implements Custom {
    private static final String ID = "rune_speed";

    public RuneSpeedEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "DDFA971E-9C93-451E-B015-2CCB3A3A4252", 0.09F, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public String getId() {
        return ID;
    }
}
