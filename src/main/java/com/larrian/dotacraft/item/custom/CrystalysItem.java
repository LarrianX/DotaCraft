package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class CrystalysItem extends DotaItem {
    private static final String ID = "crystalys";
    private static final int DAMAGE = 32;
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.DAMAGE, (double) DAMAGE
    );

    public CrystalysItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }
}