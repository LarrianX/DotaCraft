package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.attributes.DotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
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
    public void addModifiers(EnumMap<DotaAttributeType, DotaAttribute> attributes) {
        attributes.get(DotaAttributeType.DAMAGE).addModifier(getId(), DAMAGE);
        attributes.get(DotaAttributeType.REGENERATION_HEALTH).addModifier(getId(), REGENERATION_HEALTH);
        attributes.get(DotaAttributeType.REGENERATION_MANA).addModifier(getId(), REGENERATION_MANA);
    }
}
