package com.larrian.dotacraft.item.custom;


import com.larrian.dotacraft.init.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class ScepterItem extends DotaItem {
    private static final String ID = "scepter";

    private static final double ALLS = 10;
    private static final double MAX_HEALTH = 175;
    private static final double MAX_MANA = 175;

    public ScepterItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.STRENGTH).addModifier(String.valueOf(slot), ALLS);
        attributes.get(ModAttributes.AGILITY).addModifier(String.valueOf(slot), ALLS);
        attributes.get(ModAttributes.INTELLIGENCE).addModifier(String.valueOf(slot), ALLS);
        attributes.get(ModAttributes.MAX_HEALTH).addModifier(String.valueOf(slot), MAX_HEALTH);
        attributes.get(ModAttributes.MAX_MANA).addModifier(String.valueOf(slot), MAX_MANA);
    }

    @Override
    public String getId() {
        return ID;
    }
}