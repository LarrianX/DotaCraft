package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class ScepterItem extends DotaItem {
    private static final String ID = "scepter";
    private static final double ALLS = 10;
    private static final double MAX_HEALTH = 175;
    private static final double MAX_MANA = 175;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.STRENGTH, ALLS,
            ModAttributes.AGILITY, ALLS,
            ModAttributes.INTELLIGENCE, ALLS,
            ModAttributes.MAX_HEALTH, MAX_HEALTH,
            ModAttributes.MAX_MANA, MAX_MANA
    );

    public ScepterItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }
}