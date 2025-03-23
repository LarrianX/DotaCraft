package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class BootsOfSpeedItem extends DotaItem implements Custom {
    private static final String ID = "boots_of_speed";

    private static final double SPEED = 45;

    public BootsOfSpeedItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot) {
        attributes.get(DotaAttributeType.MOVEMENT_SPEED).addModifier(String.valueOf(slot), SPEED);
    }

    @Override
    public String getId() {
        return ID;
    }
}
