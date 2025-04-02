
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.RUNE_REGENERATION_EFFECT;

import com.larrian.dotacraft.effect.RegenerationHealth;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class RegenerationHealthAttribute extends DotaAttribute {
    private static final String ID = "regeneration_health";

    @Override
    public double get(PlayerEntity player, double value) {
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        double regenFromStrength = attributes.getAttribute(ModAttributes.STRENGTH).get() * 0.09;
        double regenFromRune = player.hasStatusEffect(RUNE_REGENERATION_EFFECT)
                ? attributes.getAttribute(ModAttributes.MAX_HEALTH).get() * 0.06 : 0;
        double regenFromEffect = 0;
        for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
            if (effectInstance.getEffectType() instanceof RegenerationHealth effect) {
                regenFromEffect += effect.getAmplifier();
            }
        }
        return value + regenFromStrength + regenFromRune + regenFromEffect;
    }

    @Override
    public String getId() {
        return ID;
    }
}
