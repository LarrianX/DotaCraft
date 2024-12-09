package com.larrian.dotacraft.item.predicate;

import com.larrian.dotacraft.init.ModItems;
import com.larrian.dotacraft.item.Predicate;
import com.larrian.dotacraft.item.custom.BottleItem;
import com.larrian.dotacraft.rune.Rune;
import com.larrian.dotacraft.rune.Runes;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class BottlePredicate implements Predicate {
    private static final String ID = "state";
    private static final Item ITEM = ModItems.BOTTLE;
    private static final ClampedModelPredicateProvider PROVIDER = (stack, world, entity, seed) -> {
        Rune rune = BottleItem.getRune(stack);
        if (rune != null) {
            return Runes.valueOf(rune.getId()).getState();
        } else {
            return (float) BottleItem.getFullness(stack) / 10;
        }
    };

    @Override
    public Item getItem() {
        return ITEM;
    }

    @Override
    public Identifier getId() {
        return new Identifier(ID);
    }

    @Override
    public ClampedModelPredicateProvider getProvider() {
        return PROVIDER;
    }
}
