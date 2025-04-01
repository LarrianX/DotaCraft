package com.larrian.dotacraft.rune;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public abstract class DotaRune implements Custom {
    private final String id;

    protected abstract int getDuration();
    protected abstract StatusEffect getEffect();

    protected DotaRune(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void use(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(getEffect(), getDuration(), 0, false, true),null);
    }
}
