package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class RadianceItem extends DotaItem {
    private static final String ID = "radiance";
    private static final int DAMAGE = 55;
    // TODO: +20% dodge
    private static final Map<DotaAttribute, Double> MODIFIERS = Map.of(
            ModAttributes.DAMAGE, (double) DAMAGE
    );

    public RadianceItem() {
        super(new FabricItemSettings().maxCount(1), MODIFIERS, ID);
    }
}