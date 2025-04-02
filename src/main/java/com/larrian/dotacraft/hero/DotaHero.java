package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.AttributesComponent;

public abstract class DotaHero implements Custom {
    private final DotaAttribute mainAttribute;
    private final double strengthBonus;
    private final double agilityBonus;
    private final double intelligenceBonus;

    abstract public void setAttributes(AttributesComponent attributes);

    public DotaAttribute getMainAttribute() {
        return mainAttribute;
    }

    public double getStrengthLevelBonus() {
        return strengthBonus;
    }

    public double getAgilityLevelBonus() {
        return agilityBonus;
    }

    public double getIntelligenceLevelBonus() {
        return intelligenceBonus;
    }

    protected DotaHero(DotaAttribute mainAttribute, double strengthBonus, double agilityBonus, double intelligenceBonus) {
        this.mainAttribute = mainAttribute;
        this.strengthBonus = strengthBonus;
        this.agilityBonus = agilityBonus;
        this.intelligenceBonus = intelligenceBonus;
    }
}
