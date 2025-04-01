package com.larrian.dotacraft.command;

import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.hero.Heroes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class BecomeHeroCommand {
    private static final List<String> TEAMS = List.of("Radiant", "Dire");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("become_hero");

        for (Heroes hero : Heroes.values()) {
            LiteralArgumentBuilder<ServerCommandSource> heroCommand = CommandManager.literal(hero.name());

            for (String team : TEAMS) {
                heroCommand = heroCommand.then(
                        CommandManager.literal(team)
                                .executes((context) -> execute(context, hero.name(), team))
                );
            }

            command = command.then(heroCommand);
        }

        dispatcher.register(command);
    }


    private static int execute(CommandContext<ServerCommandSource> context, String heroName, String teamName) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

        if (!TEAMS.contains(teamName)) {
            throw new SimpleCommandExceptionType(Text.literal("Invalid team: " + teamName)).create();
        }

        DotaHero hero = Heroes.valueOf(heroName).getHero();

        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        heroComponent.setHealth(0);
        heroComponent.setMana(0);
        heroComponent.setHero(hero);
        heroComponent.sync();

        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
        attributes.setLevel(1);
        for (DotaAttributes attribute : DotaAttributes.values()) {
            attributes.getAttribute(attribute).clearModifiers();
            attributes.getAttribute(attribute).set(0);
        }
        heroComponent.getHero().setAttributes(attributes);
        attributes.sync();

        assignPlayerToTeam(context.getSource().getServer(), player, teamName);

        return 1;
    }

    private static void assignPlayerToTeam(MinecraftServer server, ServerPlayerEntity player, String teamName) {
        Scoreboard scoreboard = server.getScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team != null) {
            scoreboard.addPlayerToTeam(player.getEntityName(), team);
        }
    }
}
