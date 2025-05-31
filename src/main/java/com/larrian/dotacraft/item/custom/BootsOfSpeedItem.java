package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.attribute.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class BootsOfSpeedItem extends DotaItem {
    private static final String ID = "boots_of_speed";
    private static final double SPEED = 45;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.MOVEMENT_SPEED, SPEED
    );

    public BootsOfSpeedItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }
}