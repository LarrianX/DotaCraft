package com.larrian.dotacraft.effect.rune;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class RuneInvisibilityEffect extends StatusEffect implements Custom {
    private static final String ID = "rune_invisibility";

    public RuneInvisibilityEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3402751);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // Получаем текущий эффект
        StatusEffectInstance currentEffect = entity.getStatusEffect(ModEffects.RUNE_INVISIBILITY_EFFECT);

        // Проверяем, если длительность меньше 20 тиков
        if (currentEffect != null && currentEffect.getDuration() < 20) {
            // Удаляем эффект
            entity.removeStatusEffect(ModEffects.RUNE_INVISIBILITY_EFFECT);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Обновляем эффект на каждом тике
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean isInstant() {
        return false; // Эффект не мгновенный
    }
}
