package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.DotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
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
    public void addModifiers(EnumMap<DotaAttributeType, DotaAttribute> attributes) {
        attributes.get(DotaAttributeType.DAMAGE).addModifier(getId(), DAMAGE);
    }

    @Override
    public String getId() {
        return ID;
    }
}