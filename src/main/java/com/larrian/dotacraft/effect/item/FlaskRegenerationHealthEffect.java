package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.RegenerationHealth;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;
import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_HEALTH;

public class FlaskRegenerationHealthEffect extends RegenerationHealth implements Custom {
    private static final String ID = "flask_regeneration_health";
    private static final double amplifier = ((double) 390 / 260) + ERROR;

    public FlaskRegenerationHealthEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, amplifier);
        addAttributeModifier(REGENERATION_HEALTH, "60AF4C41-AE0E-4C28-BE39-EEEFD62ACC2C", amplifier, EntityAttributeModifier.Operation.ADDITION);
    }

    public String getId() {
        return ID;
    }
}
