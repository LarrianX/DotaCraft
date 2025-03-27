package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.attributes.AttributesComponent;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
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

import static com.larrian.dotacraft.init.ModComponents.*;

public class BecomeHeroCommand {
    private static final List<String> TEAMS = List.of("Radiant", "Dire");

    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS_TEAMS = (context, builder) -> {
        TEAMS.forEach(builder::suggest);
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                    .then(CommandManager.argument("team", StringArgumentType.string())
                            .suggests(SUGGESTIONS_TEAMS)
                            .executes(BecomeHeroCommand::execute))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        String teamName = StringArgumentType.getString(context, "team");

        if (!TEAMS.contains(teamName)) {
            throw new SimpleCommandExceptionType(Text.literal("Invalid team: " + teamName)).create();
        }

        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        heroComponent.setHealth(0);
        heroComponent.setMana(0);
        heroComponent.setHero(true);
        heroComponent.sync();

        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
        attributes.setLevel(0);
        for (DotaAttributeType attribute : DotaAttributeType.values()) {
            attributes.getAttribute(attribute).clearModifiers();
            attributes.getAttribute(attribute).set(0);
        }
        attributes.getAttribute(DotaAttributeType.MAX_HEALTH).set(120);
        attributes.getAttribute(DotaAttributeType.MAX_MANA).set(75);
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
