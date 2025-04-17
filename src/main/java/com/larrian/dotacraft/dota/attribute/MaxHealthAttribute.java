
package com.larrian.dotacraft.dota.attribute;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.ModComponents.ATTRIBUTES_COMPONENT;

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
