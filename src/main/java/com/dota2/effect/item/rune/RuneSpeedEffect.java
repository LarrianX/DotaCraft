package com.dota2.effect.item.rune;

import com.dota2.effect.CustomEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class RuneSpeedEffect extends CustomEffect {
    private static final String ID = "rune_speed";
    private static final boolean persistent = false;

    public RuneSpeedEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751, persistent);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "DDFA971E-9C93-451E-B015-2CCB3A3A4252", 0.05F, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public String getId() {
        return ID;
    }
}
