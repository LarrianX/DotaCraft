package com.larrian.dotacraft.effect;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.*;

public abstract class RegenerationMana extends StatusEffect implements Custom {
    private final double amplifier;

    public RegenerationMana(StatusEffectCategory category, int color, double amplifier) {
        super(category, color);
        this.amplifier = amplifier;
    }

    public double getAmplifier() {
        return amplifier;
    }
}
