package com.larrian.dotacraft.item;

import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.attributes.IDotaAttribute;
import net.minecraft.item.Item;

import java.util.EnumMap;

public abstract class DotaItem extends Item {
    public DotaItem(Settings settings) {
        super(settings);
    }

    public void addModifiers(EnumMap<DotaAttributes, IDotaAttribute> attributes, int slot, int count) {

    }

    public void removeModifiers(EnumMap<DotaAttributes, IDotaAttribute> attributes, int slot) {
        for (IDotaAttribute attribute : attributes.values()) {
            attribute.removeModifier(String.valueOf(slot));
        }
    }
}
