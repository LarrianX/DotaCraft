package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.component.custom.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public abstract class Skill {
    abstract public void use(PlayerEntity source, HeroComponent component);

    abstract public int getCooldown(int level);

    abstract public double getMana(int level);

    public enum Type {
        FIRST,
        SECOND,
        THIRD,
        ULT
    }
}
