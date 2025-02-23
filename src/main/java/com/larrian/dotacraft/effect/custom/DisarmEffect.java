package com.larrian.dotacraft.effect.custom;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class DisarmEffect extends StatusEffect implements Custom {
    private static final String ID = "disarm";

    public DisarmEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    public String getId() {
        return ID;
    }
}
