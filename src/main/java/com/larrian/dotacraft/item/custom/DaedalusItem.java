package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class DaedalusItem extends DotaItem {
    private static final String ID = "daedalus";
    private static final double DAMAGE = 88;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.DAMAGE, DAMAGE
    );

    public DaedalusItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }
}