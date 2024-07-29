package com.dota2.block;

import net.minecraft.block.Block;

public class CustomBlockWrapper<T extends Block & CustomBlock> {
    protected final T block;

    public CustomBlockWrapper(T block) {
        this.block = block;
    }

    public T getBlock() {
        return block;
    }
}
