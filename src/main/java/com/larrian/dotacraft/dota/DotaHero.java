package com.larrian.dotacraft.dota;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.AttributesComponent;

import java.util.EnumMap;

public abstract class DotaHero implements Custom {
    private final String id;

    private final DotaAttribute mainAttribute;
    private final EnumMap<Skill.Type, Skill> skills;
    private final double strengthBonus;
    private final double agilityBonus;
    private final double intelligenceBonus;

    protected DotaHero(String id,
                       EnumMap<Skill.Type, Skill> skills, DotaAttribute mainAttribute, double strengthBonus, double agilityBonus, double intelligenceBonus) {
        this.id = id;
        this.skills = skills;
        this.mainAttribute = mainAttribute;
        this.strengthBonus = strengthBonus;
        this.agilityBonus = agilityBonus;
        this.intelligenceBonus = intelligenceBonus;
    }

    abstract public void setAttributes(AttributesComponent attributes);

    @Override
    public String getCustomId() {
        return id;
    }

    public Skill getSkill(Skill.Type type) {
        return skills.get(type);
    }

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
}
