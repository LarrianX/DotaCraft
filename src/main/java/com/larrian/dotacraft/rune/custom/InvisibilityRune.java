package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.effect.StatusEffect;

public class InvisibilityRune extends Rune {
    private static final String ID = "invisibility";
    private static final int DURATION = 900;
    private static final StatusEffect EFFECT = ModEffects.RUNE_INVISIBILITY_EFFECT;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDuration() {
        return DURATION;
    }

    @Override
    public StatusEffect getEffect() {
        return EFFECT;
    }
}
