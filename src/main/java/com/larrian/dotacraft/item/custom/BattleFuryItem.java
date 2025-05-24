package com.larrian.dotacraft.item.custom;


import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.item.DotaItem;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.attribute.DotaAttributeInstance;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.Map;

public class BattleFuryItem extends DotaItem {
    private static final String ID = "battle_fury";

    private static final double DAMAGE = 50;
    private static final double REGENERATION_HEALTH = 7.5;
    private static final double REGENERATION_MANA = 2.75;

    public BattleFuryItem() {
        super(new FabricItemSettings().maxCount(1), ID);
    }

    @Override
    public void addModifiers(Map<DotaAttribute, DotaAttributeInstance> attributes, int slot, int count) {
        attributes.get(ModAttributes.DAMAGE).addModifier(String.valueOf(slot), DAMAGE);
        attributes.get(ModAttributes.REGENERATION_HEALTH).addModifier(String.valueOf(slot), REGENERATION_HEALTH);
        attributes.get(ModAttributes.REGENERATION_MANA).addModifier(String.valueOf(slot), REGENERATION_MANA);
    }
}
