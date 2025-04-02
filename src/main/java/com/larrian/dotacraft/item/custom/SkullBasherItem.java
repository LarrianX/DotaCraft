package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;

import com.larrian.dotacraft.init.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class SkullBasherItem extends DotaItem {
    private static final String ID = "skull_basher";

    public SkullBasherItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }

}
