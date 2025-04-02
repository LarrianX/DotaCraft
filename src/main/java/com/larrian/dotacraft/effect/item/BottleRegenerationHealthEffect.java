package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.effect.RegenerationHealth;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectCategory;

import static com.larrian.dotacraft.DotaCraft.ERROR;

public class BottleRegenerationHealthEffect extends RegenerationHealth implements Custom {
    private static final String ID = "bottle_regeneration_health";
    private static final double amplifier = ((double) 110 / 2.7) + ERROR;

    public BottleRegenerationHealthEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3, amplifier);
    }

    public String getId() {
        return ID;
    }
}
