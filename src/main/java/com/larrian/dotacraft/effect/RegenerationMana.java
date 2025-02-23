package com.larrian.dotacraft.effect;

import com.larrian.dotacraft.component.ManaComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.*;

public abstract class RegenerationMana extends StatusEffect {
    private final double amplifier;

    public RegenerationMana(StatusEffectCategory category, int color, double amplifier) {
        super(category, color);
        this.amplifier = amplifier;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int effectAmplifier) {
        super.onRemoved(entity, attributes, effectAmplifier);
        if (entity instanceof ServerPlayerEntity player) {
            // +amplifier прибавится на клиенте
            ManaComponent component = player.getComponent(MANA_COMPONENT);
            component.add(-amplifier);
            component.sync();
            component.add(amplifier);
        }
    }
}
