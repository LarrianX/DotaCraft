package com.larrian.dotacraft.command.custom;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.ModRegistries;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

public class SetAttributesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("set");
        for (DotaAttribute type : ModRegistries.ATTRIBUTES) {
            command.then(
                    CommandManager.literal(type.getCustomId())
                            .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                    .executes((context -> execute(context, type.getCustomId())))));
        }
        command.then(
                CommandManager.literal("health")
                        .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                .executes((context -> executeHero(context, "health")))));
        command.then(
                CommandManager.literal("mana")
                        .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                .executes((context -> executeHero(context, "mana")))));


        dispatcher.register(command);
    }

    private static int executeHero(CommandContext<ServerCommandSource> context, String type) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) {
            throw new SimpleCommandExceptionType(Text.literal("This command can only be used by a player.")).create();
        }

        double value = DoubleArgumentType.getDouble(context, "value");

        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
        switch (type.toLowerCase()) {
            case ("health") -> heroComponent.setHealth(value);
            case ("mana") -> heroComponent.setMana(value);
        }
        heroComponent.sync();

        context.getSource().sendFeedback(() -> Text.literal("Set " + type + " to " + value + " for " + player.getName().getString()), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int execute(CommandContext<ServerCommandSource> context, String attributeName) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) {
            throw new SimpleCommandExceptionType(Text.literal("This command can only be used by a player.")).create();
        }

        double value = DoubleArgumentType.getDouble(context, "value");

        DotaAttribute attributeType;
        try {
            attributeType = ModRegistries.ATTRIBUTES.get(new Identifier(DotaCraft.MOD_ID, attributeName.toLowerCase()));
        } catch (IllegalArgumentException e) {
            throw new SimpleCommandExceptionType(Text.literal("Invalid attribute name: " + attributeName)).create();
        }

        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
        attributes.getAttribute(attributeType).set(value);
        attributes.sync();

        context.getSource().sendFeedback(() -> Text.literal("Set " + attributeName + " to " + value + " for " + player.getName().getString()), false);
        return Command.SINGLE_SUCCESS;
    }
}
