package com.larrian.dotacraft.dota;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.DotaEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public abstract class DotaRune implements Custom {
    private final String id;
    private final DotaEffect effect;
    private final int duration;

    protected DotaRune(String id, DotaEffect effect, int duration) {
        this.id = id;
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public String getCustomId() {
        return id;
    }

    public DotaEffect getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    public void use(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(getEffect(), getDuration(), 0, false, true),null);
    }
}
