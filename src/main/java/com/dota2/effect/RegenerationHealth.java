package com.dota2.effect;

import com.dota2.component.hero.ValuesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static com.dota2.component.ModComponents.VALUES_COMPONENT;


public abstract class RegenerationHealth extends CustomEffect {
    private final String id;
    private final double amplifier;

    protected RegenerationHealth(String id, double amplifier, boolean persistent) {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, persistent);
        this.id = id;
        this.amplifier = amplifier;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
            valuesComponent.addHealth(this.amplifier);
        }

        super.applyUpdateEffect(entity, amplifier);
    }
}