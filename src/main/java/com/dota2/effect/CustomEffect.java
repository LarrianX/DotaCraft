package com.dota2.effect;

import com.dota2.Custom;
import com.dota2.component.EffectComponent;
import com.dota2.component.hero.ValuesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

import static com.dota2.component.ModComponents.EFFECT_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public abstract class CustomEffect extends StatusEffect implements Custom {
    protected CustomEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

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
            effectComponent.sync();
        }

        super.onRemoved(entity, attributes, amplifier);
    }
}
