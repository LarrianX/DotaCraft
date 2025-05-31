package com.larrian.dotacraft.item.custom;


import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class BattleFuryItem extends DotaItem {
    private static final String ID = "battle_fury";

    private static final double DAMAGE = 50;
    private static final double REGENERATION_HEALTH = 7.5;
    private static final double REGENERATION_MANA = 2.75;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
        ModAttributes.DAMAGE, DAMAGE,
        ModAttributes.REGENERATION_HEALTH, REGENERATION_HEALTH,
        ModAttributes.REGENERATION_MANA, REGENERATION_MANA
    );

    public BattleFuryItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }
}
