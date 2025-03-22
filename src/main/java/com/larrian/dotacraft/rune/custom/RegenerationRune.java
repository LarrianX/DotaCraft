package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.attributes.AttributesComponent;
import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class RegenerationRune extends Rune {
    private static final String ID = "regeneration";
    private static final int DURATION = 600;
    private static final StatusEffect EFFECT = ModEffects.RUNE_REGENERATION_EFFECT;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDuration() {
        return DURATION;
    }

    @Override
    public StatusEffect getEffect() {
        return EFFECT;
    }

    @Override
    public void use(PlayerEntity player) {
        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        if (!(heroComponent.isFullMana() && heroComponent.isFullHealth())) {
            super.use(player);
        }
    }
}
