package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class PhaseBootsItem extends DotaItem {
    private static final String ID = "phase_boots";
    private static final double SPEED = 50;
    private static final double ARMOR = 4;
    // TODO: melee and range +damage(+18 melee, +12 range)
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.MOVEMENT_SPEED, SPEED,
            ModAttributes.ARMOR, ARMOR
    );

    public PhaseBootsItem() {
        super(new FabricItemSettings().maxCount(64), MODIFIERS, ID);
    }
}