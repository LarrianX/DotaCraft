package com.larrian.dotacraft;

import com.larrian.dotacraft.dota.DotaAttribute;
import com.larrian.dotacraft.dota.attribute.*;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModAttributes {
    public static final DamageAttribute DAMAGE = register(new DamageAttribute());
    public static final CritChanceAttribute CRIT_CHANCE = register(new CritChanceAttribute());
    public static final AttackSpeedAttribute ATTACK_SPEED = register(new AttackSpeedAttribute());
    public static final AttackIntervalAttribute ATTACK_INTERVAL = register(new AttackIntervalAttribute());
    public static final MovementSpeedAttribute MOVEMENT_SPEED = register(new MovementSpeedAttribute());
    public static final ArmorAttribute ARMOR = register(new ArmorAttribute());
    public static final StrengthAttribute STRENGTH = register(new StrengthAttribute());
    public static final AgilityAttribute AGILITY = register(new AgilityAttribute());
    public static final IntelligenceAttribute INTELLIGENCE = register(new IntelligenceAttribute());
    public static final MaxHealthAttribute MAX_HEALTH = register(new MaxHealthAttribute());
    public static final MaxManaAttribute MAX_MANA = register(new MaxManaAttribute());
    public static final RegenerationHealthAttribute REGENERATION_HEALTH = register(new RegenerationHealthAttribute());
    public static final RegenerationManaAttribute REGENERATION_MANA = register(new RegenerationManaAttribute());

    private static <T extends DotaAttribute> T register(T attribute) {
        String id = attribute.getCustomId();
        Registry.register(ModRegistries.ATTRIBUTES, new Identifier(DotaCraft.MOD_ID, id), attribute);
        return attribute;
    }

    public static void registerModAttributes() {}
}