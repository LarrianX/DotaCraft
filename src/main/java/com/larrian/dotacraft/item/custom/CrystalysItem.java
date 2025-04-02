package com.larrian.dotacraft.item.custom;


import com.larrian.dotacraft.init.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class CrystalysItem extends DotaItem {
    private static final String ID = "crystalys";

    private static final int DAMAGE = 32;

    public CrystalysItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
    }

    @Override
    public String getId() {
        return ID;
    }
}