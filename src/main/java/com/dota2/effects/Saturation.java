package com.dota2.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.entity.effect.StatusEffects.SATURATION;


public class Saturation extends StatusEffect implements CustomEffect {
    private static final String ID = "saturation";
    private static final int PER_TICK = 100;
    private final Map<PlayerEntity, Integer> tickCounters = new HashMap<>();

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
        if (amplifier > 100) {
            amplifier = 100;
        }
        if (entity instanceof PlayerEntity player) {
            int perTick = PER_TICK - amplifier;

            // Получаем или устанавливаем начальное значение TICK_COUNTER для игрока
            int tickCounter = tickCounters.getOrDefault(player, 0);

            if (tickCounter == 0) {
                tickCounters.put(player, perTick);
                HungerManager hunger = player.getHungerManager();
                if (hunger.isNotFull()) {
                    hunger.setFoodLevel(hunger.getFoodLevel() + 1);
                }
            } else {
                tickCounters.put(player, tickCounter - 1);
            }

            // Обязательно удаляем игрока из карты, если он больше не имеет эффекта
            if (player.getStatusEffect(this) == null) {
                tickCounters.remove(player);
            }
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public String getId() {
        return ID;
    }
}