package com.larrian.dotacraft.hero;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.attributes.IDotaAttribute;
import com.larrian.dotacraft.component.AttributesComponent;

import java.util.EnumMap;

public abstract class DotaHero implements Custom {
    private final String id;
    private final double strengthBonus;
    private final double agilityBonus;
    private final double intelligenceBonus;

    abstract public void setAttributes(AttributesComponent attributes);

    public double getStrengthLevelBonus() {
        return strengthBonus;
    }

    public double getAgilityLevelBonus() {
        return agilityBonus;
    }

    public double getIntelligenceLevelBonus() {
        return intelligenceBonus;
    }

    protected DotaHero(String id, double strengthBonus, double agilityBonus, double intelligenceBonus) {
        this.id = id;
        this.strengthBonus = strengthBonus;
        this.agilityBonus = agilityBonus;
        this.intelligenceBonus = intelligenceBonus;
    }

    @Override
    public String getId() {
        return id;
    }
}
