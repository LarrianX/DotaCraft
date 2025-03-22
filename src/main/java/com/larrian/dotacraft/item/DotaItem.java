package com.larrian.dotacraft.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.DotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import net.minecraft.item.Item;

import java.util.EnumMap;

public abstract class DotaItem extends Item implements Custom {
    public DotaItem(Settings settings) {
        super(settings);
    }

    public void addModifiers(EnumMap<DotaAttributeType, DotaAttribute> attributes) {

    }
}
