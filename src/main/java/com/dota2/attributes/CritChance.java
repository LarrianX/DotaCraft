package com.dota2.attributes;

import com.dota2.Custom;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

public class CritChance extends ClampedEntityAttribute implements Custom {
    private static final String ID = "crit_chance";
    private static final double FALLBACK = 0;
    public static final double MIN = 0;
    public static final double MAX = 100;


    public CritChance() {
        super("attribute.name.generic.crit_chance",
                FALLBACK, MIN, MAX);
    }

    @Override
    public String getId() {
        return ID;
    }
}
