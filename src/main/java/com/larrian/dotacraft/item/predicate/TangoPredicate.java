package com.larrian.dotacraft.item.predicate;

import com.larrian.dotacraft.init.ModItems;
import com.larrian.dotacraft.item.Predicate;
import com.larrian.dotacraft.item.custom.TangoItem;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class TangoPredicate implements Predicate {
    private static final String ID = TangoItem.FULLNESS_KEY;
    private static final Item ITEM = ModItems.TANGO;
    private static final ClampedModelPredicateProvider PROVIDER = (stack, world, entity, seed) -> {
        float value = (float) TangoItem.getFullness(stack) / TangoItem.MAX_FULLNESS;
        // Делим fullness на макс. fullness и получаем float
        return Math.round(value * 100.0f) / 100.0f; // Округляем до двух чисел после запятой
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
