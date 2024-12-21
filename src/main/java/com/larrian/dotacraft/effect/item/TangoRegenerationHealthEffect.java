package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.RegenerationHealth;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;
import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_HEALTH;

public class TangoRegenerationHealthEffect extends RegenerationHealth implements Custom {
    private static final String ID = "tango_regeneration_health";
    private static final double amplifier = 0.35 + ERROR;

    public TangoRegenerationHealthEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, amplifier);
        addAttributeModifier(REGENERATION_HEALTH, "55E23AAC-C778-4DE8-96EF-3EE2AEE75017", amplifier, EntityAttributeModifier.Operation.ADDITION);
    }

    public String getId() {
        return ID;
    }
}
