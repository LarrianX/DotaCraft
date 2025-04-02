package com.larrian.dotacraft.item.custom;


import com.larrian.dotacraft.init.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class BootsOfSpeedItem extends DotaItem {
    private static final String ID = "boots_of_speed";

    private static final double SPEED = 45;

    public BootsOfSpeedItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.MOVEMENT_SPEED).addModifier(String.valueOf(slot), SPEED * count);
    }

    @Override
    public String getId() {
        return ID;
    }
}
