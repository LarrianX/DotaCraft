package com.larrian.dotacraft.item;


import com.larrian.dotacraft.ModAttributes;
import com.larrian.dotacraft.DotaItem;
import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.dota.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class DesolatorItem extends DotaItem {
    private static final String ID = "desolator";

    private static final double DAMAGE = 25;
    private static final double ATTACK_SPEED = 35;

    public DesolatorItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
        attributes.get(ModAttributes.ATTACK_SPEED).addModifier(String.valueOf(slot), ATTACK_SPEED);
    }
}

