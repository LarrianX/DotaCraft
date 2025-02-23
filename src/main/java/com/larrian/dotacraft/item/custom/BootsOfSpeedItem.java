package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class BootsOfSpeedItem extends Item implements Custom {
    private static final String ID = "boots_of_speed";

    // TODO: сделать +45 к скорости передвижения

    public BootsOfSpeedItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }
}
