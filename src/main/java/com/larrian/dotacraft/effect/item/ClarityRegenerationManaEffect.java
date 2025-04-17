package com.larrian.dotacraft.effect.item;


import com.larrian.dotacraft.effect.RegenerationManaEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;

public class ClarityRegenerationManaEffect extends RegenerationManaEffect {
    private static final String ID = "clarity_regeneration_mana";
    private static final double amplifier = ((double) 150 / 25) + ERROR;

    public ClarityRegenerationManaEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, ID, amplifier);
    }
}
