
package com.larrian.dotacraft.dota.attribute;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.dota.DotaAttribute;

import static com.larrian.dotacraft.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.ModEffects.RUNE_DOUBLE_DAMAGE;

import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public class DamageAttribute extends DotaAttribute {
    private static final String ID = "damage";

    public DamageAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        if (player.hasStatusEffect(RUNE_DOUBLE_DAMAGE)) {
            value *= 0.8;
        }
        if (heroComponent.isHero()) {
            value += attributes.getAttribute(heroComponent.getHero().getMainAttribute()).get();
        }

        return value;
    }
}
