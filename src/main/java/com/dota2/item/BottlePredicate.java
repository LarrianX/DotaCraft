package com.dota2.item;

import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class BottlePredicate implements Predicate {
    private static final String ID = Bottle.FULLNESS_KEY;
    private static final Item ITEM = ModItems.BOTTLE;
    private static final ClampedModelPredicateProvider PROVIDER = (stack, world, entity, seed) -> {
        float value = (float) Bottle.getFullness(stack) / Bottle.MAX_FULLNESS;
        return Math.round(value * 100.0f) / 100.0f;
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
