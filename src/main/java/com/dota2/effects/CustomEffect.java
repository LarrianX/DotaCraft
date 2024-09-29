package com.dota2.effects;

import com.dota2.components.EffectComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

import static com.dota2.components.ModComponents.EFFECT_COMPONENT;

public abstract class CustomEffect extends StatusEffect {
    protected CustomEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public abstract String getId();

    protected double getAmplifier(Map<String, Double> amplifiers, int amplifier) {
        return amplifiers.getOrDefault(getId(), (double) amplifier + 1);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            EffectComponent effectComponent = player.getComponent(EFFECT_COMPONENT);
            effectComponent.getAmplifiers().remove(getId());
        }

        super.onRemoved(entity, attributes, amplifier);
    }
}
