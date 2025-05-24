package com.larrian.dotacraft.item.custom;


import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.attribute.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class PhaseBootsItem extends DotaItem {
    private static final String ID = "phase_boots";

    private static final double SPEED = 50;
    // TODO: melee and range +damage(+18 melee, +12 range)
    private static final double ARMOR = 4;

    public PhaseBootsItem() {
        super(new FabricItemSettings().maxCount(64), ID);
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.MOVEMENT_SPEED).addModifier(String.valueOf(slot), SPEED * count);
        attributes.get(ModAttributes.ARMOR).addModifier(String.valueOf(slot), ARMOR * count);
    }
}