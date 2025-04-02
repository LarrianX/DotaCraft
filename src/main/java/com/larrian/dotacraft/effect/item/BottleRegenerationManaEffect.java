package com.larrian.dotacraft.effect.item;


import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.RegenerationMana;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;

public class BottleRegenerationManaEffect extends RegenerationMana implements Custom {
    private static final String ID = "bottle_regeneration_mana";
    private static final double amplifier = ((double) 60 / 2.7) + ERROR;

    public BottleRegenerationManaEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, amplifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}
