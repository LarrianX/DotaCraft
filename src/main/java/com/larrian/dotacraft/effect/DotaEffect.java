package com.larrian.dotacraft.effect;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class DotaEffect extends StatusEffect implements Custom {
    private final String id;

    protected DotaEffect(StatusEffectCategory category, int color, String id) {
        super(category, color);
        this.id = id;
    }

    @Override
    public String getCustomId() {
        return id;
    }
}
