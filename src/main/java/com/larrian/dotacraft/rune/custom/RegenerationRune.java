package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.effect.StatusEffect;

public class RegenerationRune implements Rune {
    private static final String ID = "regeneration";
    private static final int DURATION = 600;
    private static final StatusEffect EFFECT = ModEffects.RUNE_REGENERATION_EFFECT;

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
