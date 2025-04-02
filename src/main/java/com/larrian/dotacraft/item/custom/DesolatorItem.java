package com.larrian.dotacraft.item.custom;


import com.larrian.dotacraft.init.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.attributes.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class DesolatorItem extends DotaItem {
    private static final String ID = "desolator";

    private static final double DAMAGE = 25;
    private static final double ATTACK_SPEED = 35;

    public DesolatorItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
        attributes.get(ModAttributes.ATTACK_SPEED).addModifier(String.valueOf(slot), ATTACK_SPEED);
    }

    @Override
    public String getId() {
        return ID;
    }
}

