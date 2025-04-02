package com.larrian.dotacraft.attributes;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class DotaAttribute implements Custom {
    public double get(PlayerEntity player, double value) {
        return value;
    }
}
