package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.IDotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class ScepterItem extends DotaItem implements Custom {
    private static final String ID = "scepter";

    private static final double ALL_ATTRIBUTES = 10;
    private static final double MAX_HEALTH = 175;
    private static final double MAX_MANA = 175;

    public ScepterItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributes, IDotaAttribute> attributes, int slot, int count) {
        attributes.get(DotaAttributes.STRENGTH).addModifier(String.valueOf(slot), ALL_ATTRIBUTES);
        attributes.get(DotaAttributes.AGILITY).addModifier(String.valueOf(slot), ALL_ATTRIBUTES);
        attributes.get(DotaAttributes.INTELLIGENCE).addModifier(String.valueOf(slot), ALL_ATTRIBUTES);
        attributes.get(DotaAttributes.MAX_HEALTH).addModifier(String.valueOf(slot), MAX_HEALTH);
        attributes.get(DotaAttributes.MAX_MANA).addModifier(String.valueOf(slot), MAX_MANA);
    }

    @Override
    public String getId() {
        return ID;
    }
}