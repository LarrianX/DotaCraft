package com.dota2.item;


import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public interface Predicate {
    public Item getItem();
    public Identifier getId();
    public ClampedModelPredicateProvider getProvider();
}
