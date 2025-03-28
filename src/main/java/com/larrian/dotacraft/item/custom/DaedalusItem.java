package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class DaedalusItem extends DotaItem implements Custom {
    private static final String ID = "daedalus";

    private static final double DAMAGE = 88;

    public DaedalusItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot, int count) {
        attributes.get(DotaAttributeType.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
    }

    @Override
    public String getId() {
        return ID;
    }
}
