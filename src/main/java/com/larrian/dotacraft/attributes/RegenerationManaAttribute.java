package com.larrian.dotacraft.attributes;

import com.larrian.dotacraft.Custom;
import net.minecraft.entity.attribute.ClampedEntityAttribute;

public class RegenerationManaAttribute extends ClampedEntityAttribute implements Custom {
    public static final double MIN = 0;
    public static final double MAX = 100;
    private static final String ID = "regeneration_mana";
    private static final double FALLBACK = 0.05;

    public RegenerationManaAttribute() {
        super("attribute.dotacraft.generic." + ID,
                FALLBACK, MIN, MAX);
    }

    @Override
    public String getId() {
        return ID;
    }
}
