package com.larrian.dotacraft.item;


import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public interface Predicate {
    Item getItem();

    Identifier getId();

    ClampedModelPredicateProvider getProvider();
}
