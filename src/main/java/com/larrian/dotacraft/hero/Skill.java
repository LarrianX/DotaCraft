package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.component.custom.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public abstract class Skill {
    abstract public void use(PlayerEntity source, SkillInstance skillInstance);

    abstract public int[] getCooldowns();

    abstract public double getMana(int level);

    public int getMaxLevel() {
        return getCooldowns().length;
    }

    public enum Type {
        FIRST,
        SECOND,
        THIRD,
        ULT
    }
}
