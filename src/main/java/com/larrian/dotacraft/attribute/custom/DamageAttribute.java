
package com.larrian.dotacraft.attribute.custom;

import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.attribute.DotaAttribute;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.effect.ModEffects.RUNE_DOUBLE_DAMAGE;

import com.larrian.dotacraft.component.custom.HeroComponent;
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
            DotaAttribute attribute = heroComponent.getHeroType().getMainAttribute().getAssociatedAttribute();
            if (attribute != null) {
                value += attributes.getAttribute(attribute).get();
            } else {
                // TODO: universal main attribute
            }
        }

        return value;
    }
}
