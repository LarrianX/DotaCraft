package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.component.attributes.IDotaAttribute;
import com.larrian.dotacraft.item.DotaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.EnumMap;

public class BattleFuryItem extends DotaItem implements Custom {
    private static final String ID = "battle_fury";

    private static final double DAMAGE = 50;
    private static final double REGENERATION_HEALTH = 7.5;
    private static final double REGENERATION_MANA = 2.75;

    public BattleFuryItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void addModifiers(EnumMap<DotaAttributeType, IDotaAttribute> attributes, int slot) {
        attributes.get(DotaAttributeType.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
        attributes.get(DotaAttributeType.REGENERATION_HEALTH).addModifier(String.valueOf(slot), REGENERATION_HEALTH);
        attributes.get(DotaAttributeType.REGENERATION_MANA).addModifier(String.valueOf(slot), REGENERATION_MANA);
    }
}
