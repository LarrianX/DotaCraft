
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.SyncedAttributesComponent;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class AgilityAttribute extends DotaAttribute {

    public AgilityAttribute(PlayerEntity provider, AttributesComponent attributes) {
        super(provider, attributes);
    }

    @Override
    public double get() {
        HeroComponent component = provider.getComponent(HERO_COMPONENT);
        double levelBonus = component.isHero() ? component.getHero().getAgilityLevelBonus() : 0;
        return Math.max(0, super.get() + (attributes.getLevel() - 2) * levelBonus);
    }
}
