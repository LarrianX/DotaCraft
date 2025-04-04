package com.larrian.dotacraft.hero;

import net.minecraft.entity.player.PlayerEntity;

public abstract class Skill {
    abstract public void use(PlayerEntity source);

    abstract public int getCooldown(int level);

    abstract public double getMana(int level);

    public enum Type {
        FIRST,
        SECOND,
        THIRD,
        SUPER
    }
}
