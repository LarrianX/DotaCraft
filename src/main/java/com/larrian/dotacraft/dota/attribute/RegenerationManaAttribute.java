
package com.larrian.dotacraft.dota.attribute;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.dota.DotaAttribute;

import static com.larrian.dotacraft.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.ModEffects.RUNE_REGENERATION;

import com.larrian.dotacraft.effect.RegenerationManaEffect;
import com.larrian.dotacraft.dota.ModAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class RegenerationManaAttribute extends DotaAttribute {
    private static final String ID = "regeneration_mana";

    public RegenerationManaAttribute() {
        super(ID);
    }

    @Override
    public double get(PlayerEntity player, double value) {
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        double regenFromIntelligence = attributes.getAttribute(ModAttributes.INTELLIGENCE).get() * 0.05;
        double regenFromMana = player.hasStatusEffect(RUNE_REGENERATION)
                ? attributes.getAttribute(ModAttributes.MAX_MANA).get() * 0.06 : 0;
        double regenFromEffect = 0;
        for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
            if (effectInstance.getEffectType() instanceof RegenerationManaEffect effect) {
                regenFromEffect += effect.getAmplifier();
            }
        }
        return value + regenFromIntelligence + regenFromMana + regenFromEffect;
    }
}
