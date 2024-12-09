package com.larrian.dotacraft.rune;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.effect.StatusEffect;

public interface Rune extends Custom {
    int getDuration();
    StatusEffect getEffect();
}
