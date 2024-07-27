package com.dota2.item;

import net.minecraft.item.Item;

public class CustomItemWrapper<T extends Item & CustomItem> {
    protected final T item;

    public CustomItemWrapper(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}
