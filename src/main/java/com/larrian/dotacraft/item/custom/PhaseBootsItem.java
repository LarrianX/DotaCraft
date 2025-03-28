package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import java.util.EnumMap;

public class PhaseBootsItem extends DotaItem implements Custom {
    private static final String ID = "phase_boots";

    private static final double SPEED = 50;
    // TODO: melee and range +damage(+18 melee, +12 range)
    private static final double ARMOR = 4;

    public PhaseBootsItem() {super(new FabricItemSettings());}

    @Override
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot, int count) {
        attributes.get(DotaAttributeType.MOVEMENT_SPEED).addModifier(String.valueOf(slot), SPEED * count);
        attributes.get(DotaAttributeType.ARMOR).addModifier(String.valueOf(slot), ARMOR * count);
    }

    @Override
    public String getId() {
        return ID;
    }
}