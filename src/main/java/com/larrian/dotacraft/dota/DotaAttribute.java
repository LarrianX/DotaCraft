package com.larrian.dotacraft.dota;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.player.PlayerEntity;

public abstract class DotaAttribute implements Custom {
    private final String id;

    protected DotaAttribute(String id) {
        this.id = id;
    }

    public double get(PlayerEntity player, double value) {
        return value;
    }

    @Override
    public String getCustomId() {
        return id;
    }
}
