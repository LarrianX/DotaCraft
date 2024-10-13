package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class RecDaedalus extends Item implements CustomItem {
    private static final String ID = "rec_daedalus";

    public RecDaedalus() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }
}
