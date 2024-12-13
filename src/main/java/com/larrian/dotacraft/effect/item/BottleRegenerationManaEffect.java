package com.larrian.dotacraft.effect.item;


import com.larrian.dotacraft.Custom;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;
import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_MANA;

public class BottleRegenerationManaEffect extends StatusEffect implements Custom {
    private static final String ID = "bottle_regeneration_mana";
    private static final double amplifier = ((double) 60 / 54) + ERROR;

    public BottleRegenerationManaEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
        addAttributeModifier(REGENERATION_MANA, "D63A1B7F-04D2-45D3-9353-E75DB3DB35D4", amplifier, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public String getId() {
        return ID;
    }
}
