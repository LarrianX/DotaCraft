package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.IDotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class CrystalysItem extends DotaItem implements Custom {
    private static final String ID = "crystalys";

    private static final int DAMAGE = 32;

    public CrystalysItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributes, IDotaAttribute> attributes, int slot, int count) {
        attributes.get(DotaAttributes.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
    }

    @Override
    public String getId() {
        return ID;
    }
}