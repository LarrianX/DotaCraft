package com.dota2.item;

import net.minecraft.item.Item;

public class CustomPredicateItemWrapper<T extends Item & CustomItem & HasPredicate>
        extends CustomItemWrapper<T> {

    public CustomPredicateItemWrapper(T item) {
        super(item);
    }
}
