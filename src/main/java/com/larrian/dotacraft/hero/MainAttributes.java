package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.attribute.ModAttributes;

public enum MainAttributes {
    STRENGTH(ModAttributes.STRENGTH),
    AGILITY(ModAttributes.AGILITY),
    INTELLIGENCE(ModAttributes.INTELLIGENCE),
    UNIVERSAL(null);

    private final DotaAttribute associatedAttribute;

    MainAttributes(DotaAttribute attribute) {
        this.associatedAttribute = attribute;
    }

    public DotaAttribute getAssociatedAttribute() {
        return associatedAttribute;
    }
}
