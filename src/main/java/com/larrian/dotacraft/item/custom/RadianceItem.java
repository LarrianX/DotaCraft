package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class RadianceItem extends DotaItem implements Custom {
    private static final String ID = "radiance";

    private static final int DAMAGE = 55;
    // TODO: +20% dodge

    public RadianceItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot) {
        attributes.get(DotaAttributeType.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
    }
}

