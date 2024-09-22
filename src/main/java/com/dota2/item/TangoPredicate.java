package com.dota2.item;

public class TangoPredicate extends Tango implements HasPredicate {
    private static final Predicate PREDICATE = new Predicate(FULLNESS_KEY, (stack, world, entity, seed) -> {
        float value = (float) Tango.getFullness(stack) / Tango.MAX_FULLNESS;
        // Делим fullness на макс. fullness и получаем float
        return Math.round(value * 100.0f) / 100.0f; // Округляем до двух чисел после запятой
    });

    @Override
    public Predicate getPredicate() {
        return PREDICATE;
    }
}
