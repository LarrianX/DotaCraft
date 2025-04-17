
package com.larrian.dotacraft.dota.attribute;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.ModComponents.HERO_COMPONENT;

public class StrengthAttribute extends DotaAttribute {
    private static final String ID = "strength";

    public StrengthAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);

        double levelBonus = component.isHero() ? component.getHero().getStrengthLevelBonus() : 0;
        return value + (component.getLevel() - 1) * levelBonus;
    }
}

