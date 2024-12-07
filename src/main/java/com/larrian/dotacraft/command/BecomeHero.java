package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.EffectComponent;
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

import static com.larrian.dotacraft.init.ModComponents.*;
import static com.larrian.dotacraft.component.hero.SyncedHeroComponent.HEALTH;

public class BecomeHero {
    private static final List<String> TEAMS = List.of("Radiant", "Dire");

    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS_TEAMS = (context, builder) -> {
        for (String team : TEAMS) {
            builder.suggest(team);
        }
        return builder.buildFuture();
    };
    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS_VALUES = (context, builder) -> {
        builder.suggest(String.valueOf((int) SyncedMaxValuesComponent.MAX));
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                        .then(CommandManager.argument("max health", DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                                .suggests(SUGGESTIONS_VALUES)
                                .then(CommandManager.argument("max mana", DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                                        .suggests(SUGGESTIONS_VALUES)
                                        .then(CommandManager.argument("team", StringArgumentType.string())
                                                .suggests(SUGGESTIONS_TEAMS)
                                                .executes(BecomeHero::execute))))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);

            if (attribute != null && !heroComponent.isHero()) {
                double maxHealth = DoubleArgumentType.getDouble(context, "max health");
                double maxMana = DoubleArgumentType.getDouble(context, "max mana");
                String teamName = StringArgumentType.getString(context, "team");

                // Проверка на валидность
                if (!TEAMS.contains(teamName)) {
                    throw new SimpleCommandExceptionType(Text.literal("Недопустимая команда: " + teamName)).create();
                }
                // Выставление старых значений
                OldValuesComponent oldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);
                oldValuesComponent.setOldHealth((int) player.getHealth());
                oldValuesComponent.setOldMaxHealth((int) attribute.getBaseValue());
                oldValuesComponent.sync();
                // Выставление макс. хп
                attribute.setBaseValue(HEALTH);
                player.setHealth(HEALTH);
                // Выставление максимальных значений
                MaxValuesComponent maxValuesComponent = player.getComponent(MAX_VALUES_COMPONENT);
                maxValuesComponent.setMaxHealth(maxHealth);
                maxValuesComponent.setMaxMana(maxMana);
                maxValuesComponent.sync();
                // Выставление значений хп и маны
                ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
                valuesComponent.setHealth(maxHealth);
                valuesComponent.setMana(maxMana);
                valuesComponent.sync();
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
                // Временно: показ в amplifiers к какой команде вы принадлежите
                EffectComponent effectComponent = player.getComponent(EFFECT_COMPONENT);
                effectComponent.getAmplifiers().put(teamName, 1.0);
                effectComponent.sync();
            }
        }
        return 1;
    }
}
