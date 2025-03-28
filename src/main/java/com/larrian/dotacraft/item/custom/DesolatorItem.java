package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class DesolatorItem extends DotaItem implements Custom {
    private static final String ID = "desolator";

    private static final double DAMAGE = 25;
    private static final double ATTACK_SPEED = 35;

    public DesolatorItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot, int count) {
        attributes.get(DotaAttributeType.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
        attributes.get(DotaAttributeType.ATTACK_SPEED).addModifier(String.valueOf(slot), ATTACK_SPEED);
    }

    @Override
    public String getId() {
        return ID;
    }
}

