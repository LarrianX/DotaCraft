
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class MaxHealthAttribute extends DotaAttribute {
    private static final String ID = "max_health";

    @Override
    public double get(PlayerEntity player, double value) {
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        return value + attributes.getAttribute(ModAttributes.STRENGTH).get() * 22;
    }

    @Override
    public String getId() {
        return ID;
    }
}
