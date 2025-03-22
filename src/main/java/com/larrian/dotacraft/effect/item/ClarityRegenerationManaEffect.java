package com.larrian.dotacraft.effect.item;


import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.RegenerationMana;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;

public class ClarityRegenerationManaEffect extends RegenerationMana implements Custom {
    private static final String ID = "clarity_regeneration_mana";
    private static final double amplifier = ((double) 150 / 500) + ERROR;

    public ClarityRegenerationManaEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, amplifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}
