package com.larrian.dotacraft.effect;

import com.larrian.dotacraft.component.hero.HealthComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.HEALTH_COMPONENT;

public abstract class RegenerationHealth extends StatusEffect {
    private final double amplifier;

    public RegenerationHealth(StatusEffectCategory category, int color, double amplifier) {
        super(category, color);
        this.amplifier = amplifier;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int effectAmplifier) {
        super.onRemoved(entity, attributes, effectAmplifier);
        if (entity instanceof ServerPlayerEntity player) {
            // +amplifier прибавится на клиенте
            HealthComponent component = player.getComponent(HEALTH_COMPONENT);
            component.add(-amplifier);
            component.sync();
            component.add(amplifier);
        }
    }
}
