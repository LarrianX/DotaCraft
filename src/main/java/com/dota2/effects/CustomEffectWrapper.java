package com.dota2.effects;

import net.minecraft.entity.effect.StatusEffect;

public class CustomEffectWrapper<T extends StatusEffect & CustomEffect> {
    protected final T effect;

    public CustomEffectWrapper(T effect) {
        this.effect = effect;
    }

    public T getEffect() {
        return effect;
    }
}
