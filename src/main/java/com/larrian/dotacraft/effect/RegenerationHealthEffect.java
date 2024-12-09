package com.larrian.dotacraft.effect;

import com.larrian.dotacraft.component.hero.ValuesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.VALUES_COMPONENT;


public abstract class RegenerationHealthEffect extends CustomEffect {
    private final String id;
    private final double amplifier;

    protected RegenerationHealthEffect(String id, double amplifier, boolean persistent) {
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