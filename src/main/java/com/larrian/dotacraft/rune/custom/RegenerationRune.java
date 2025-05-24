package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.effect.DotaEffect;
import com.larrian.dotacraft.rune.DotaRune;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.effect.ModEffects.RUNE_REGENERATION;

public class RegenerationRune extends DotaRune {
    private static final DotaEffect EFFECT = RUNE_REGENERATION;
    private static final int DURATION = 600;

    public RegenerationRune(String id) {
        super(id, EFFECT, DURATION);
    }

    @Override
    public void use(PlayerEntity player) {
        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        if (!(heroComponent.isFullMana() && heroComponent.isFullHealth())) {
            super.use(player);
        }
    }
}
