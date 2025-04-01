package com.larrian.dotacraft.hero.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.attributes.IDotaAttribute;
import com.larrian.dotacraft.attributes.custom.StrengthAttribute;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.hero.DotaHero;

import java.util.EnumMap;

public class Pudge extends DotaHero {
    private static final double STRENGTH_BONUS = 3.0;
    private static final double AGILITY_BONUS = 1.4;
    private static final double INTELLIGENCE_BONUS = 1.8;

    public Pudge(String id) {
        super(id, STRENGTH_BONUS, AGILITY_BONUS, INTELLIGENCE_BONUS);
    }

    @Override
    public void setAttributes(AttributesComponent attributes) {
        attributes.getAttribute(DotaAttributes.DAMAGE).set(73); // average
        attributes.getAttribute(DotaAttributes.ARMOR).set(1.8);
        attributes.getAttribute(DotaAttributes.MOVEMENT_SPEED).set(280);
        attributes.getAttribute(DotaAttributes.ATTACK_SPEED).set(111);
        attributes.getAttribute(DotaAttributes.ATTACK_INTERVAL).set(1.7);

        attributes.getAttribute(DotaAttributes.STRENGTH).set(25);
        attributes.getAttribute(DotaAttributes.AGILITY).set(11);
        attributes.getAttribute(DotaAttributes.INTELLIGENCE).set(16.2);

        attributes.getAttribute(DotaAttributes.MAX_HEALTH).set(54);
        attributes.getAttribute(DotaAttributes.MAX_MANA).set(54);
        attributes.getAttribute(DotaAttributes.REGENERATION_HEALTH).set(2.75);
//        attributes.getAttribute(DotaAttributes.REGENERATION_MANA).set(0);
    }
}
