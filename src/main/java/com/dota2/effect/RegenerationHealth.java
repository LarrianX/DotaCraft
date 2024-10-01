package com.dota2.effect;

import com.dota2.component.EffectComponent;
import com.dota2.component.HeroComponent.ValuesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static com.dota2.component.ModComponents.EFFECT_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;


public class RegenerationHealth extends CustomEffect {
    private static final String ID = "regeneration_health";

    protected RegenerationHealth() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
            EffectComponent effectComponent = player.getComponent(EFFECT_COMPONENT);
            double toAdd = this.getAmplifier(effectComponent.getAmplifiers(), amplifier);
            valuesComponent.addHealth(toAdd);
//            valuesComponent.sync();
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}