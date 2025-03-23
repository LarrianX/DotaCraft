package com.larrian.dotacraft.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import net.minecraft.item.Item;

import java.util.EnumMap;

public abstract class DotaItem extends Item implements Custom {
    public DotaItem(Settings settings) {
        super(settings);
    }

    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot) {

    }

    public void removeModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot) {
        for (IDotaAttribute attribute : attributes.values()) {
            attribute.removeModifier(String.valueOf(slot));
        }
    }
}
