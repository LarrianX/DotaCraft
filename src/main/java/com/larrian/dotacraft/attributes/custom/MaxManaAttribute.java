
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public class MaxManaAttribute extends DotaAttribute {

    public MaxManaAttribute(PlayerEntity provider, AttributesComponent attributes) {
        super(provider, attributes);
    }

    @Override
    public double get() {
        return Math.max(0, super.get() + Math.floor(attributes.getAttribute(DotaAttributes.INTELLIGENCE).get()) * 12);
    }
}
