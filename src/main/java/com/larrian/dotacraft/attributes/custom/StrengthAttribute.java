
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.SyncedAttributesComponent;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class StrengthAttribute extends DotaAttribute {
    private static final String ID = "strength";

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);

        double levelBonus = component.isHero() ? component.getHero().getStrengthLevelBonus() : 0;
        return value + (component.getLevel() - 1) * levelBonus;
    }

    @Override
    public String getId() {
        return ID;
    }
}

