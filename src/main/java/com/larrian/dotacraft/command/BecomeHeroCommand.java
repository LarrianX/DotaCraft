package com.larrian.dotacraft.command;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.init.ModRegistries;
import com.mojang.brigadier.CommandDispatcher;
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
import net.minecraft.util.Identifier;

import java.util.List;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class BecomeHeroCommand {
    private static final List<String> TEAMS = List.of("Radiant", "Dire");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("become_hero");

        for (DotaHero hero : ModRegistries.HEROES) {
            LiteralArgumentBuilder<ServerCommandSource> heroCommand = CommandManager.literal(hero.getId());

            for (String team : TEAMS) {
                heroCommand = heroCommand.then(
                        CommandManager.literal(team)
                                .executes((context) -> execute(context, hero.getId(), team))
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

        DotaHero hero = ModRegistries.HEROES.get(new Identifier(DotaCraft.MOD_ID, heroName.toLowerCase()));

        HeroComponent component = player.getComponent(HERO_COMPONENT);
        component.setHealth(0);
        component.setMana(0);
        component.setHero(hero);
        component.setLevel(1);
        component.sync();

        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
        for (DotaAttribute attribute : ModRegistries.ATTRIBUTES) {
            attributes.getAttribute(attribute).clearModifiers();
            attributes.getAttribute(attribute).set(0);
        }
        component.getHero().setAttributes(attributes);
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
