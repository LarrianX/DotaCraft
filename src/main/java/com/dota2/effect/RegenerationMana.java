package com.dota2.effect;

import com.dota2.component.hero.ValuesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static com.dota2.component.ModComponents.VALUES_COMPONENT;


public abstract class RegenerationMana extends CustomEffect {
    private final String id;
    private final double amplifier;
    private final boolean persistent;

    protected RegenerationMana(String id, double amplifier, boolean persistent) {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
        this.id = id;
        this.amplifier = amplifier;
        this.persistent = persistent;
    }

    public boolean isPersistent() {
        return persistent;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
            valuesComponent.addMana(this.amplifier);
        }

        super.applyUpdateEffect(entity, amplifier);
    }
}