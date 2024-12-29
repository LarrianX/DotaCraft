package com.larrian.dotacraft.rune;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public abstract class Rune implements Custom {
    protected abstract int getDuration();
    protected abstract StatusEffect getEffect();

    public void use(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(getEffect(), getDuration(), 0, false, false),null);
    }
}
