package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.RegenerationHealth;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;
import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_HEALTH;

public class BottleRegenerationHealthEffect extends RegenerationHealth implements Custom {
    private static final String ID = "bottle_regeneration_health";
    private static final double amplifier = ((double) 110 / 54) + ERROR;

    public BottleRegenerationHealthEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, amplifier);
        addAttributeModifier(REGENERATION_HEALTH, "017BAB6C-7FC5-4116-95FC-45005D59F1A0", amplifier, EntityAttributeModifier.Operation.ADDITION);
    }

    public String getId() {
        return ID;
    }
}
