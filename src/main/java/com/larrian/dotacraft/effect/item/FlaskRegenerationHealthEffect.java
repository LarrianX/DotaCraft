package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationHealthEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;

public class FlaskRegenerationHealthEffect extends RegenerationHealthEffect {
    private static final String ID = "flask_regeneration_health";
    private static final double amplifier = ((double) 390 / 13) + ERROR;

    public FlaskRegenerationHealthEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, ID, amplifier);
    }
}
