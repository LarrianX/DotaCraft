
package com.larrian.dotacraft.dota.attribute;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.dota.ModHeroes.PUDGE;

public class IntelligenceAttribute extends DotaAttribute {
    private static final String ID = "intelligence";

    public IntelligenceAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);

        double levelBonus = component.isHero() ? component.getHero().getIntelligenceLevelBonus() : 0;
        double bonus = component.isHero() && component.getHero().equals(PUDGE) &&
                component.getLevel() >= 17 ? 2 : 0;

        return value + ((component.getLevel() - 1) * levelBonus) + bonus;
    }
}
