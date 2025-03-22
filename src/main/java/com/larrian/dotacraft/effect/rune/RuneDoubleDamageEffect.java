package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RuneDoubleDamageEffect extends StatusEffect implements Custom {
    private static final String ID = "rune_double_damage";

    public RuneDoubleDamageEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
        // TODO: modifier damage
    }

    @Override
    public String getId() {
        return ID;
    }
}
