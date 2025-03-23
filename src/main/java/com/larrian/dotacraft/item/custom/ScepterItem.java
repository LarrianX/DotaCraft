package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

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
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot) {
        attributes.get(DotaAttributeType.STRENGTH).addModifier(String.valueOf(slot), ALL_ATTRIBUTES);
        attributes.get(DotaAttributeType.AGILITY).addModifier(String.valueOf(slot), ALL_ATTRIBUTES);
        attributes.get(DotaAttributeType.INTELLIGENCE).addModifier(String.valueOf(slot), ALL_ATTRIBUTES);
        attributes.get(DotaAttributeType.MAX_HEALTH).addModifier(String.valueOf(slot), MAX_HEALTH);
        attributes.get(DotaAttributeType.MAX_MANA).addModifier(String.valueOf(slot), MAX_MANA);
    }

    @Override
    public String getId() {
        return ID;
    }
}