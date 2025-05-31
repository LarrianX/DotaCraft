package com.larrian.dotacraft.command.custom;

import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.hero.Skill;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public class LevelCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Root literal: /level
        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("level")
                .executes(LevelCommand::executeAllMax);

        // Skill branches: /level <skillType> <level 1–4>
        for (Skill.Type type : Skill.Type.values()) {
            command.then(
                    CommandManager.literal(type.name().toLowerCase())
                            .executes(context -> executeSkill(context, type, 4))
                            .then(
                                    CommandManager.argument("level", IntegerArgumentType.integer(1, 4))
                                            .executes(ctx -> executeSkill(ctx, type))
                            )
            );
        }

        // Player branch: /level player <level 1–30>
        command.then(
                CommandManager.literal("player")
                        .executes(context -> executePlayerLevel(context, 30))
                        .then(
                                CommandManager.argument("level", IntegerArgumentType.integer(1, 30))
                                        .executes(LevelCommand::executePlayerLevel)
                        )
        );

        dispatcher.register(command);
    }

    private static int executeAllMax(CommandContext<ServerCommandSource> context) {
        executePlayerLevel(context, 30);
        for (Skill.Type type : Skill.Type.values()) {
            executeSkill(context, type, 4);
        }
        return 1;
    }

    private static int executePlayerLevel(CommandContext<ServerCommandSource> context) {
        return executePlayerLevel(context, IntegerArgumentType.getInteger(context, "level"));
    }

    private static int executePlayerLevel(CommandContext<ServerCommandSource> context, int lvl) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            // Set hero's overall level
            component.setLevel(lvl);
            component.sync();
        }
        return 1;
    }

    private static int executeSkill(CommandContext<ServerCommandSource> context, Skill.Type type) {
        return executeSkill(context, type, IntegerArgumentType.getInteger(context, "level"));
    }

    private static int executeSkill(CommandContext<ServerCommandSource> context, Skill.Type type, int lvl) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            // Set specific skill level
            component.getSkillInstance(type).setLevel(lvl);
            component.sync();
        }
        return 1;
    }
}
