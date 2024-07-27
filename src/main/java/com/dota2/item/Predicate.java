package com.dota2.item;


import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.util.Identifier;

public class Predicate {
    private final Identifier ID;
    private final ClampedModelPredicateProvider PROVIDER;

    Predicate(String id, ClampedModelPredicateProvider provider) {
        ID = new Identifier(id);
        PROVIDER = provider;
    }

    public Identifier getId() {
        return ID;
    }

    public ClampedModelPredicateProvider getProvider() {
        return PROVIDER;
    }
}
