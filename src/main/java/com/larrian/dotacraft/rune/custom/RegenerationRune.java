package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.rune.DotaRune;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.RUNE_REGENERATION_EFFECT;

public class RegenerationRune extends DotaRune {
    private static final String ID = "regeneration";
    private static final int DURATION = 600;
    private static final StatusEffect EFFECT = RUNE_REGENERATION_EFFECT;

    public RegenerationRune(String id) {
        super(id);
    }

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
