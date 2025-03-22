package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class DemonEdgeItem extends DotaItem implements Custom {
    private static final String ID = "demon_edge";

    private static final double DAMAGE = 25;
    private static final double ATTACK_SPEED = 35;

    public DemonEdgeItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes) {
        attributes.get(DotaAttributeType.DAMAGE).addModifier(getId(), DAMAGE);
        attributes.get(DotaAttributeType.ATTACK_SPEED).addModifier(getId(), ATTACK_SPEED);
    }

    @Override
    public String getId() {
        return ID;
    }
}

