package com.dota2.effects;

import com.dota2.components.EffectComponent;
import com.dota2.components.HeroComponents.ValuesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

import static com.dota2.components.ModComponents.EFFECT_COMPONENT;
import static com.dota2.components.ModComponents.VALUES_COMPONENT;


public class RegenerationMana extends StatusEffect implements CustomEffect {
    private static final String ID = "regeneration_mana";

    protected RegenerationMana() {
        // category: StatusEffectCategory - describes if the effect is helpful (BENEFICIAL), harmful (HARMFUL) or useless (NEUTRAL)
        // color: int - Color is the color assigned to the effect (in RGB)
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    // Called every tick to check if the effect can be applied or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    private double getAmplifier(Map<String, Double> amplifiers, int amplifier) {
        return amplifiers.getOrDefault(ID, (double) amplifier + 1);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
            EffectComponent effectComponent = player.getComponent(EFFECT_COMPONENT);
            valuesComponent.addMana(this.getAmplifier(effectComponent.getAmplifiers(), amplifier));
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            EffectComponent effectComponent = player.getComponent(EFFECT_COMPONENT);
            effectComponent.getAmplifiers().remove(ID);
        }

        super.onRemoved(entity, attributes, amplifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}