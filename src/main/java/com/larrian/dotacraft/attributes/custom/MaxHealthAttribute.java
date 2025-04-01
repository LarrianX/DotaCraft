
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.player.PlayerEntity;

public class MaxHealthAttribute extends DotaAttribute {

    public MaxHealthAttribute(PlayerEntity provider, AttributesComponent attributes) {
        super(provider, attributes);
    }

    @Override
    public double get() {
        return super.get() + Math.max(0, attributes.getAttribute(DotaAttributes.STRENGTH).get() * 22);
    }
}
