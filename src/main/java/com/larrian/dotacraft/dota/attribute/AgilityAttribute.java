
package com.larrian.dotacraft.dota.attribute;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.ModComponents.HERO_COMPONENT;

public class AgilityAttribute extends DotaAttribute {
    private static final String ID = "agility";

    public AgilityAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);

        double levelBonus = component.isHero() ? component.getHero().getAgilityLevelBonus() : 0;
        return value + (component.getLevel() - 1) * levelBonus;
    }
}
