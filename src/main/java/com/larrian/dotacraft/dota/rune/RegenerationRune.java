package com.larrian.dotacraft.dota.rune;

import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.effect.DotaEffect;
import com.larrian.dotacraft.dota.DotaRune;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.ModEffects.RUNE_REGENERATION;

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
