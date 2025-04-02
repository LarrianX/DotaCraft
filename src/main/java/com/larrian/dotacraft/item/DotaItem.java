package com.larrian.dotacraft.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributeInstance;
import net.minecraft.item.Item;

import java.util.Map;

public abstract class DotaItem extends Item implements Custom {
    public DotaItem(Settings settings) {
        super(settings);
    }

    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {

    }

    public void removeModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot) {
        for (DotaAttributeInstance attribute : attributes.values()) {
            attribute.removeModifier(String.valueOf(slot));
        }
    }
}
