package com.dota2.item;

import com.dota2.rune.Rune;
import com.dota2.rune.Runes;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class BottlePredicate implements Predicate {
    private static final String ID = "state";
    private static final Item ITEM = ModItems.BOTTLE;
    private static final ClampedModelPredicateProvider PROVIDER = (stack, world, entity, seed) -> {
        Rune rune = Bottle.getRune(stack);
        if (rune != null) {
            return Runes.valueOf(rune.getId()).getState();
        } else {
            return (float) Bottle.getFullness(stack) / 10;
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
