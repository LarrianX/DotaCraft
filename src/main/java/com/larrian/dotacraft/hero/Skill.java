package com.larrian.dotacraft.hero;

import net.minecraft.entity.player.PlayerEntity;

public abstract class Skill {
    private final Type type;
    private final double mana;

    protected Skill(Type type, double mana) {
        this.type = type;
        this.mana = mana;
    }

    abstract public boolean use(PlayerEntity source);

    abstract public int getCooldown(int level);

    public Type getType() {
        return type;
    }

    public double getMana() {
        return mana;
    }

    public enum Type {
        FIRST,
        SECOND,
        THIRD,
        SUPER
    }
}
