package com.larrian.dotacraft.client.predicate;

import com.larrian.dotacraft.ModItems;
import com.larrian.dotacraft.client.Predicate;
import com.larrian.dotacraft.item.BottleItem;
import com.larrian.dotacraft.dota.DotaRune;
import com.larrian.dotacraft.dota.Runes;
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
