
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.RUNE_DOUBLE_DAMAGE_EFFECT;

import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public class DamageAttribute extends DotaAttribute {
    private static final String ID = "damage";

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        if (player.hasStatusEffect(RUNE_DOUBLE_DAMAGE_EFFECT)) {
            value *= 0.8;
        }
        if (heroComponent.isHero()) {
            value += attributes.getAttribute(heroComponent.getHero().getMainAttribute()).get();
        }

        return value;
    }

    @Override
    public String getId() {
        return ID;
    }
}
