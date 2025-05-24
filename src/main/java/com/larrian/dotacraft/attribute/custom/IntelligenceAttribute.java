
package com.larrian.dotacraft.attribute.custom;

import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.component.custom.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.hero.ModHeroes.PUDGE;

public class IntelligenceAttribute extends DotaAttribute {
    private static final String ID = "intelligence";

    public IntelligenceAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);

        double levelBonus = component.isHero() ? component.getHeroType().getIntelligenceLevelBonus() : 0;
        double bonus = component.isHero() && component.getHeroType().equals(PUDGE) &&
                component.getLevel() >= 17 ? 2 : 0;

        return value + ((component.getLevel() - 1) * levelBonus) + bonus;
    }
}
