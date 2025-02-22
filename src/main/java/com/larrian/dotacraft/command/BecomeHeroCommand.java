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

import static com.larrian.dotacraft.component.hero.SyncedHeroComponent.HEALTH;
import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.*;

public class BecomeHeroCommand {
    private static final List<String> TEAMS = List.of("Radiant", "Dire");

    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS_TEAMS = (context, builder) -> {
        TEAMS.forEach(builder::suggest);
        return builder.buildFuture();
    };

    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS_VALUES = (context, builder) -> {
        builder.suggest("10000");
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                        .then(CommandManager.argument("max_health", DoubleArgumentType.doubleArg(MaxHealthAttribute.MIN, MaxHealthAttribute.MAX))
                                .suggests(SUGGESTIONS_VALUES)
                                .then(CommandManager.argument("max_mana", DoubleArgumentType.doubleArg(MaxManaAttribute.MIN, MaxManaAttribute.MAX))
                                        .suggests(SUGGESTIONS_VALUES)
                                        .then(CommandManager.argument("team", StringArgumentType.string())
                                                .suggests(SUGGESTIONS_TEAMS)
                                                .executes(BecomeHeroCommand::execute))))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

        double maxHealth = DoubleArgumentType.getDouble(context, "max_health");
        double maxMana = DoubleArgumentType.getDouble(context, "max_mana");
        String teamName = StringArgumentType.getString(context, "team");

        if (!TEAMS.contains(teamName)) {
            throw new SimpleCommandExceptionType(Text.literal("Недопустимая команда: " + teamName)).create();
        }

        applyHeroAttributes(player, maxHealth, maxMana);
        assignPlayerToTeam(context.getSource().getServer(), player, teamName);

        return 1;
    }

    private static void applyHeroAttributes(ServerPlayerEntity player, double maxHealth, double maxMana) {
        EntityAttributeInstance vanillaMaxHealthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        EntityAttributeInstance maxHealthAttribute = player.getAttributeInstance(MAX_HEALTH);
        EntityAttributeInstance maxManaAttribute = player.getAttributeInstance(MAX_MANA);

        if (vanillaMaxHealthAttribute == null || maxHealthAttribute == null || maxManaAttribute == null) {
            return;
        }

        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        if (heroComponent.isHero()) {
            return;
        }

        OldValuesComponent oldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);
        oldValuesComponent.setOldHealth((int) player.getHealth());
        oldValuesComponent.setOldMaxHealth((int) vanillaMaxHealthAttribute.getBaseValue());
        oldValuesComponent.sync();

        vanillaMaxHealthAttribute.setBaseValue(HEALTH);
        maxHealthAttribute.setBaseValue(maxHealth);
        maxManaAttribute.setBaseValue(maxMana);

        player.getComponent(MANA_COMPONENT).set(maxMana);
        player.getComponent(HEALTH_COMPONENT).set(maxHealth);

        heroComponent.setHero(true);
        heroComponent.sync();
    }

    private static void assignPlayerToTeam(MinecraftServer server, ServerPlayerEntity player, String teamName) {
        Scoreboard scoreboard = server.getScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team != null) {
            scoreboard.addPlayerToTeam(player.getEntityName(), team);
        }
    }
}
