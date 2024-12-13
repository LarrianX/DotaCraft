package com.larrian.dotacraft.attributes;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.attribute.ClampedEntityAttribute;

public class MaxHealthAttribute extends ClampedEntityAttribute implements Custom {
    public static final double MIN = 1;
    public static final double MAX = 30000;
    private static final String ID = "max_health";
    private static final double FALLBACK = 1;

    public MaxHealthAttribute() {
        super("attribute.dotacraft.generic." + ID,
                FALLBACK, MIN, MAX);
        setTracked(true);
    }

    @Override
    public String getId() {
        return ID;
    }
}
