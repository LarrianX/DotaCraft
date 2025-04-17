package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationHealthEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;

public class TangoRegenerationHealthEffect extends RegenerationHealthEffect {
    private static final String ID = "tango_regeneration_health";
    private static final double amplifier = 7 + ERROR;

    public TangoRegenerationHealthEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, ID, amplifier);
    }
}
