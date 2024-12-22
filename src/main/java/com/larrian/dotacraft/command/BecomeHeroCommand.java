package com.larrian.dotacraft.command;

import com.larrian.dotacraft.attributes.MaxHealthAttribute;
import com.larrian.dotacraft.attributes.MaxManaAttribute;
import com.larrian.dotacraft.component.hero.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

import static com.larrian.dotacraft.init.ModAttributes.MAX_HEALTH;
import static com.larrian.dotacraft.init.ModAttributes.MAX_MANA;
import static com.larrian.dotacraft.init.ModComponents.*;
import static com.larrian.dotacraft.component.hero.SyncedHeroComponent.HEALTH;

public class BecomeHeroCommand {
    private static final List<String> TEAMS = List.of("Radiant", "Dire");

    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS_TEAMS = (context, builder) -> {
        for (String team : TEAMS) {
            builder.suggest(team);
        }
        return builder.buildFuture();
    };
    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS_VALUES = (context, builder) -> {
        builder.suggest("10000");
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                        .then(CommandManager.argument("max health", DoubleArgumentType.doubleArg(MaxHealthAttribute.MIN, MaxHealthAttribute.MAX))
                                .suggests(SUGGESTIONS_VALUES)
                                .then(CommandManager.argument("max mana", DoubleArgumentType.doubleArg(MaxManaAttribute.MIN, MaxManaAttribute.MAX))
                                        .suggests(SUGGESTIONS_VALUES)
                                        .then(CommandManager.argument("team", StringArgumentType.string())
                                                .suggests(SUGGESTIONS_TEAMS)
                                                .executes(BecomeHeroCommand::execute))))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            EntityAttributeInstance vanillaMaxHealthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            EntityAttributeInstance maxHealthAttribute = player.getAttributeInstance(MAX_HEALTH);
            EntityAttributeInstance maxManaAttribute = player.getAttributeInstance(MAX_MANA);

            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);

            if (vanillaMaxHealthAttribute != null &&
                    maxManaAttribute != null &&
                    maxHealthAttribute != null &&
                    !heroComponent.isHero()) {
                double maxHealth = DoubleArgumentType.getDouble(context, "max health");
                double maxMana = DoubleArgumentType.getDouble(context, "max mana");
                String teamName = StringArgumentType.getString(context, "team");

                // valid check
                if (!TEAMS.contains(teamName)) {
                    throw new SimpleCommandExceptionType(Text.literal("Недопустимая команда: " + teamName)).create();
                }
                // set old values
                OldValuesComponent oldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);
                oldValuesComponent.setOldHealth((int) player.getHealth());
                oldValuesComponent.setOldMaxHealth((int) vanillaMaxHealthAttribute.getBaseValue());
                oldValuesComponent.sync();
                // set vanilla max health
                vanillaMaxHealthAttribute.setBaseValue(HEALTH);
                // set max health and mana
                maxHealthAttribute.setBaseValue(maxHealth);
                maxManaAttribute.setBaseValue(maxMana);
                // set health and mana
                ManaComponent manaComponent = player.getComponent(MANA_COMPONENT);
                manaComponent.setMana(maxMana);
                HealthComponent healthComponent = player.getComponent(HEALTH_COMPONENT);
                healthComponent.setHealth(maxHealth);
                // Выставление героем
                heroComponent.setHero(true);
                heroComponent.sync();
                // Переход в команду
                MinecraftServer server = context.getSource().getServer();
                Scoreboard scoreboard = server.getScoreboard();
                Team team = scoreboard.getTeam(teamName);
                if (team != null) {
                    scoreboard.addPlayerToTeam(player.getEntityName(), team);
                }
            }
        }
        return 1;
    }
}
