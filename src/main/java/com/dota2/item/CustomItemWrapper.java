package com.dota2.item;

import net.minecraft.item.Item;

public record CustomItemWrapper<T extends Item & CustomItem>(T item) {
}
