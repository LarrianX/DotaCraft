package com.larrian.dotacraft.client.predicate.custom;

import com.larrian.dotacraft.item.ModItems;
import com.larrian.dotacraft.client.predicate.Predicate;
import com.larrian.dotacraft.item.custom.BottleItem;
import com.larrian.dotacraft.rune.DotaRune;
import com.larrian.dotacraft.rune.Runes;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;

public class BottlePredicate extends Predicate {
    private static final String ID = "state";
    private static final Item ITEM = ModItems.BOTTLE;
    private static final ClampedModelPredicateProvider PROVIDER = (stack, world, entity, seed) -> {
        DotaRune rune = BottleItem.getRune(stack);
        if (rune != null) {
            return Runes.valueOf(rune.getCustomId()).getState();
        } else {
            return (float) BottleItem.getFullness(stack) / 10;
        }
    };

    public BottlePredicate() {
        super(ID, ITEM, PROVIDER);
    }
}
