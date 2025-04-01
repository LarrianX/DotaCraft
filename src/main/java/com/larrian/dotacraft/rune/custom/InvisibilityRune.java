package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.rune.DotaRune;
import net.minecraft.entity.effect.StatusEffect;

import static com.larrian.dotacraft.init.ModEffects.RUNE_INVISIBILITY_EFFECT;

public class InvisibilityRune extends DotaRune {
    private static final String ID = "invisibility";
    private static final int DURATION = 900;
    private static final StatusEffect EFFECT = RUNE_INVISIBILITY_EFFECT;

    public InvisibilityRune(String id) {
        super(id);
    }

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
