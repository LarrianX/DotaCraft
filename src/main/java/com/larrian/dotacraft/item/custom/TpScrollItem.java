package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;

import com.larrian.dotacraft.init.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class TpScrollItem extends DotaItem {
    private static final String ID = "tp_scroll";

    public TpScrollItem() {
        super(new FabricItemSettings().maxCount(64));
    }

    @Override
    public String getId() {
        return ID;
    }
}
