
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.init.ModHeroes;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.init.ModHeroes.PUDGE;

public class IntelligenceAttribute extends DotaAttribute {
    private static final String ID = "intelligence";

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        double levelBonus = component.isHero() ? component.getHero().getIntelligenceLevelBonus() : 0;
        double bonus = component.isHero() && component.getHero().equals(PUDGE) &&
                attributes.getLevel() >= 17 ? 2 : 0;

        return value + ((attributes.getLevel() - 1) * levelBonus) + bonus;
    }

    @Override
    public String getId() {
        return ID;
    }
}
