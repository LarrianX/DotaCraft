
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class MaxManaAttribute extends DotaAttribute {
    private static final String ID = "max_mana";

    @Override
    public double get(PlayerEntity player, double value) {
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        return value + Math.floor(attributes.getAttribute(ModAttributes.INTELLIGENCE).get()) * 12;
    }

    @Override
    public String getId() {
        return ID;
    }
}
