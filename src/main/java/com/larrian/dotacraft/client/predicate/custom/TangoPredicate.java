package com.larrian.dotacraft.client.predicate.custom;

import com.larrian.dotacraft.item.ModItems;
import com.larrian.dotacraft.client.predicate.Predicate;
import com.larrian.dotacraft.item.custom.TangoItem;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;

public class TangoPredicate extends Predicate {
    private static final String ID = TangoItem.FULLNESS_KEY;
    private static final Item ITEM = ModItems.TANGO;
    private static final ClampedModelPredicateProvider PROVIDER = (stack, world, entity, seed) -> {
        float value = (float) TangoItem.getFullness(stack) / TangoItem.MAX_FULLNESS;
        // Делим fullness на макс. fullness и получаем float
        return Math.round(value * 100.0f) / 100.0f; // Округляем до двух чисел после запятой
    };

    public TangoPredicate() {
        super(ID, ITEM, PROVIDER);
    }
}
