package com.larrian.dotacraft.hero.custom;

import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.hero.Skill;
import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.EnumMap;

import static com.larrian.dotacraft.init.ModAttributes.STRENGTH;

public class Pudge extends DotaHero {
    private static final String ID = "pudge";

    private static class MeatHook extends Skill {
        private static final Skill.Type type = Skill.Type.FIRST;
        private static final double mana = 110;

        public MeatHook() {
            super(type, mana);
        }

        @Override
        public int getCooldown(int level) {
            return 18 * 20;
        }

        @Override
        public boolean use(PlayerEntity source) {
            source.sendMessage(Text.literal("first skill"));
            return true;
        }
    }

    private static final DotaAttribute MAIN_ATTRIBUTE = STRENGTH;
    private static final double STRENGTH_BONUS = 3.0;
    private static final double AGILITY_BONUS = 1.4;
    private static final double INTELLIGENCE_BONUS = 1.8;

    private static final Skill FIRST = new MeatHook();
    private static final Skill SECOND = new MeatHook();
    private static final Skill THIRD = new MeatHook();
    private static final Skill SUPER = new MeatHook();

    private static final EnumMap<Skill.Type, Skill> skills = new EnumMap<>(Skill.Type.class);

    static {
        skills.put(Skill.Type.FIRST, FIRST);
        skills.put(Skill.Type.SECOND, SECOND);
        skills.put(Skill.Type.THIRD, THIRD);
        skills.put(Skill.Type.SUPER, SUPER);
    }

    public Pudge() {
        super(skills, MAIN_ATTRIBUTE, STRENGTH_BONUS, AGILITY_BONUS, INTELLIGENCE_BONUS);
    }

    @Override
    public void setAttributes(AttributesComponent attributes) {
        attributes.getAttribute(ModAttributes.DAMAGE).set(48); // average
        attributes.getAttribute(ModAttributes.ARMOR).set(1.8);
        attributes.getAttribute(ModAttributes.MOVEMENT_SPEED).set(280);
        attributes.getAttribute(ModAttributes.ATTACK_SPEED).set(111);
        attributes.getAttribute(ModAttributes.ATTACK_INTERVAL).set(1.7);

        attributes.getAttribute(STRENGTH).set(25);
        attributes.getAttribute(ModAttributes.AGILITY).set(11);
        attributes.getAttribute(ModAttributes.INTELLIGENCE).set(16);

        attributes.getAttribute(ModAttributes.MAX_HEALTH).set(120);
        attributes.getAttribute(ModAttributes.MAX_MANA).set(75);
        attributes.getAttribute(ModAttributes.REGENERATION_HEALTH).set(2.75);
//        attributes.getAttribute(ModAttributes.REGENERATION_MANA).set(0);
    }

    @Override
    public String getId() {
        return ID;
    }
}
