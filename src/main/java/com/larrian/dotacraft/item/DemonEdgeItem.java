package com.larrian.dotacraft.item;



import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.dota.DotaAttributeInstance;
import com.larrian.dotacraft.dota.ModAttributes;
import com.larrian.dotacraft.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class DemonEdgeItem extends DotaItem {
    private static final String ID = "demon_edge";

    private static final double DAMAGE = 25;
    private static final double ATTACK_SPEED = 35;

    public DemonEdgeItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
        attributes.get(ModAttributes.ATTACK_SPEED).addModifier(String.valueOf(slot), ATTACK_SPEED);
    }
}

