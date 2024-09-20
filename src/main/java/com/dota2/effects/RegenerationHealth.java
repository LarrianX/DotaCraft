package com.dota2.effects;

import com.dota2.components.EffectAttributes;
import com.dota2.components.HeroAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static com.dota2.components.ModComponents.EFFECT_ATTRIBUTES;
import static com.dota2.components.ModComponents.HERO_ATTRIBUTES;


public class RegenerationHealth extends StatusEffect implements CustomEffect {
    private static final String ID = "regeneration_health";
    private static final int REGENERATION = 1;
    private static final int REGENERATION_PER_TICKS = 100;

    protected RegenerationHealth() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (amplifier > REGENERATION_PER_TICKS) {
            amplifier = REGENERATION_PER_TICKS;
        }
        if (entity instanceof PlayerEntity player) {
            EffectAttributes effect_component = player.getComponent(EFFECT_ATTRIBUTES);
            HeroAttributes hero_component = player.getComponent(HERO_ATTRIBUTES);

            int tickCounter = effect_component.getTickHealth();
            if (tickCounter == 0) {
                int perTicks = REGENERATION_PER_TICKS - amplifier;
                if (perTicks != 0)
                    effect_component.setTickHealth(perTicks);

                if (hero_component.isNotFullHealth()) {
                    hero_component.setHealth(hero_component.getHealth() + REGENERATION);
                }
            }
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
//        if (entity instanceof PlayerEntity player) {
//            EffectAttributes effect_component = player.getComponent(EFFECT_ATTRIBUTES);
//            effect_component.setTickHealth(0);
//        }

        super.onRemoved(entity, attributes, amplifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}