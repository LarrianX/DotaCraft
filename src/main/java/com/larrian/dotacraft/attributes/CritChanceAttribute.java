package com.larrian.dotacraft.attributes;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.attribute.ClampedEntityAttribute;

public class CritChanceAttribute extends ClampedEntityAttribute implements Custom {
    public static final double MIN = 0;
    public static final double MAX = 100;
    private static final String ID = "crit_chance";
    private static final double FALLBACK = 0;


    public CritChanceAttribute() {
        super("attribute.name.generic.crit_chance",
                FALLBACK, MIN, MAX);
    }

    @Override
    public String getId() {
        return ID;
    }
}
