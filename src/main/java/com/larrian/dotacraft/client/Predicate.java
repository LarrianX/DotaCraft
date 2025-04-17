package com.larrian.dotacraft.client;

import com.larrian.dotacraft.Custom;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;

public abstract class Predicate implements Custom {
    private final String id;
    private final Item item;
    private final ClampedModelPredicateProvider provider;

    protected Predicate(String id, Item item, ClampedModelPredicateProvider provider) {
        this.id = id;
        this.item = item;
        this.provider = provider;
    }

    @Override
    public String getCustomId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public ClampedModelPredicateProvider getProvider() {
        return provider;
    }
}
