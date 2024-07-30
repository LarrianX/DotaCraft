package com.dota2.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import static net.minecraft.entity.effect.StatusEffects.SATURATION;


public class Saturation extends StatusEffect implements CustomEffect {
    private static final String ID = "saturation";
    private static final int PER_TICK = 10;
    private static int TICK_COUNTER = 0;

    protected Saturation() {
        // category: StatusEffectCategory - describes if the effect is helpful (BENEFICIAL), harmful (HARMFUL) or useless (NEUTRAL)
        // color: int - Color is the color assigned to the effect (in RGB)
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    // Called every tick to check if the effect can be applied or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // Called when the effect is applied.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            int per_tick = PER_TICK * (amplifier + 1);
            if (TICK_COUNTER == 0) {
                TICK_COUNTER = per_tick;
                entity.setStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 1), null);
            } else {
                TICK_COUNTER--;
            }
//            ((PlayerEntity) entity).addExperience(1 << amplifier); // Higher amplifier gives you experience faster
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}