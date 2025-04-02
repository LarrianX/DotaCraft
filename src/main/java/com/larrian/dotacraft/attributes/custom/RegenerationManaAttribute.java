
package com.larrian.dotacraft.attributes.custom;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttribute;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.RUNE_REGENERATION_EFFECT;

import com.larrian.dotacraft.effect.RegenerationHealth;
import com.larrian.dotacraft.effect.RegenerationMana;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class RegenerationManaAttribute extends DotaAttribute {
    private static final String ID = "regeneration_mana";

    @Override
    public double get(PlayerEntity player, double value) {
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        double regenFromIntelligence = attributes.getAttribute(ModAttributes.INTELLIGENCE).get() * 0.05;
        double regenFromMana = player.hasStatusEffect(RUNE_REGENERATION_EFFECT)
                ? attributes.getAttribute(ModAttributes.MAX_MANA).get() * 0.06 : 0;
        double regenFromEffect = 0;
        for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
            if (effectInstance.getEffectType() instanceof RegenerationMana effect) {
                regenFromEffect += effect.getAmplifier();
            }
        }
        return value + regenFromIntelligence + regenFromMana + regenFromEffect;
    }

    @Override
    public String getId() {
        return ID;
    }
}
