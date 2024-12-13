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
        addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "2225271A-2443-4B0E-B6C2-65FAE0911EED", 1, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public String getId() {
        return ID;
    }
}
