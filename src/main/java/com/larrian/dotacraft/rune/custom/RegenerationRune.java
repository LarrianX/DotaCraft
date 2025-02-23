package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.component.HealthComponent;
import com.larrian.dotacraft.component.ManaComponent;
import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.HEALTH_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.MANA_COMPONENT;

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
        ManaComponent manaComponent = player.getComponent(MANA_COMPONENT);
        HealthComponent healthComponent = player.getComponent(HEALTH_COMPONENT);
        if (!(manaComponent.isFull() && healthComponent.isFull()))
            super.use(player);
    }
}
