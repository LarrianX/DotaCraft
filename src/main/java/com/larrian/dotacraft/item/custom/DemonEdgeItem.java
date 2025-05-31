package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class DemonEdgeItem extends DotaItem {
    private static final String ID = "demon_edge";
    private static final double DAMAGE = 25;
    private static final double ATTACK_SPEED = 35;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.DAMAGE, DAMAGE,
            ModAttributes.ATTACK_SPEED, ATTACK_SPEED
    );

    public DemonEdgeItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }
}