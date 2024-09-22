package com.dota2.item;

public class BottlePredicate extends Bottle implements HasPredicate {
    private static final Predicate PREDICATE = new Predicate(FULLNESS_KEY, (stack, world, entity, seed) -> {
        float value = (float) Bottle.getFullness(stack) / Bottle.MAX_FULLNESS;
        return Math.round(value * 100.0f) / 100.0f;
    });

    @Override
    public Predicate getPredicate() {
        return PREDICATE;
    }
}
