package com.larrian.dotacraft.dota.hero;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.dota.DotaHero;
import com.larrian.dotacraft.dota.Skill;
import com.larrian.dotacraft.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.EnumMap;

import static com.larrian.dotacraft.ModAttributes.STRENGTH;

public class PudgeHero extends DotaHero {
    private static final String ID = "pudge";

    private static class MeatHookSkill extends Skill {
        private static final double MANA = 110;
        private static final int COOLDOWN = 12;

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            return COOLDOWN * 20;
        }

        @Override
        public void use(PlayerEntity source) {
            source.sendMessage(Text.literal("first skill"));
        }
    }

    private static class RotSkill extends Skill {
        private static final double MANA = 80;
        private static final int COOLDOWN = 1;

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            return COOLDOWN * 20;
        }

        @Override
        public void use(PlayerEntity source) {
            source.sendMessage(Text.literal("second skill"));
        }
    }

    private static class MeatShieldSkill extends Skill {
        private static final double MANA = 80;
        private static final int COOLDOWN = 17;

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            return COOLDOWN * 20;
        }

        @Override
        public void use(PlayerEntity source) {
            source.sendMessage(Text.literal("third skill"));
        }
    }

    private static class DismemberSkill extends Skill {
        private static final double MANA = 170;
        private static final int COOLDOWN = 20;

        @Override
        public double getMana(int level) {
            return MANA;
        }

        @Override
        public int getCooldown(int level) {
            return COOLDOWN * 20;
        }

        @Override
        public void use(PlayerEntity source) {
            source.sendMessage(Text.literal("super"));
        }
    }

    private static final DotaAttribute MAIN_ATTRIBUTE = STRENGTH;
    private static final double STRENGTH_BONUS = 3.0;
    private static final double AGILITY_BONUS = 1.4;
    private static final double INTELLIGENCE_BONUS = 1.8;

    private static final EnumMap<Skill.Type, Skill> SKILLS = new EnumMap<>(Skill.Type.class);

    private static final MeatHookSkill FIRST = put(Skill.Type.FIRST, new MeatHookSkill());
    private static final RotSkill SECOND = put(Skill.Type.SECOND, new RotSkill());
    private static final MeatShieldSkill THIRD = put(Skill.Type.THIRD, new MeatShieldSkill());
    private static final DismemberSkill SUPER = put(Skill.Type.SUPER, new DismemberSkill());

    private static <T extends Skill> T put(Skill.Type type, T skill) {
        SKILLS.put(type, skill);
        return skill;
    }

    public PudgeHero() {
        super(ID, SKILLS, MAIN_ATTRIBUTE, STRENGTH_BONUS, AGILITY_BONUS, INTELLIGENCE_BONUS);
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
}
