package com.dota2.effect.rune;

import com.dota2.effect.CustomEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RuneDoubleDamageEffect extends CustomEffect {
    private static final String ID = "rune_double_damage";
    private static final boolean persistent = false;

    public RuneDoubleDamageEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751, persistent);
        addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "2225271A-2443-4B0E-B6C2-65FAE0911EED", 1, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public String getId() {
        return ID;
    }
}
