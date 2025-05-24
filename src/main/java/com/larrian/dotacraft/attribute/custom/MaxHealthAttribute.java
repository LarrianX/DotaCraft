
package com.larrian.dotacraft.attribute.custom;

import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.attribute.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;

public class MaxHealthAttribute extends DotaAttribute {
    private static final String ID = "max_health";

    public MaxHealthAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        return value + attributes.getAttribute(ModAttributes.STRENGTH).get() * 22;
    }
}
