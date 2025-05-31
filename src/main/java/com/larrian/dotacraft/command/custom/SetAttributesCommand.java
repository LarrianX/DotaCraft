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

        // Register generic attributes
        for (DotaAttribute type : ModRegistries.ATTRIBUTES) {
            command.then(
                    CommandManager.literal(type.getCustomId())
                            .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                    .executes(ctx -> executeAttribute(ctx, type.getCustomId())))
            );
        }

        // Register hero health with overload: no-arg and with value
        command.then(
                CommandManager.literal("health")
                        .executes(ctx -> executeHero(ctx, "health"))  // overload without value
                        .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                .executes(ctx -> executeHero(ctx, "health", DoubleArgumentType.getDouble(ctx, "value"))))
        );

        // Register hero mana with overload: no-arg and with value
        command.then(
                CommandManager.literal("mana")
                        .executes(ctx -> executeHero(ctx, "mana"))  // overload without value
                        .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                .executes(ctx -> executeHero(ctx, "mana", DoubleArgumentType.getDouble(ctx, "value"))))
        );

        dispatcher.register(command);
    }

    /**
     * Helper to retrieve the player or throw if console.
     */
    private static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> context)
            throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            throw new SimpleCommandExceptionType(Text.literal("This command can only be used by a player."))
                    .create();
        }
        return player;
    }

    /**
     * Execute setting health/mana without explicit value:
     * retrieves max via AttributesComponent and delegates.
     */
    private static int executeHero(CommandContext<ServerCommandSource> context, String type)
            throws CommandSyntaxException {
        ServerPlayerEntity player = getPlayer(context);
        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

        // Determine the max-attribute identifier
        Identifier maxId = new Identifier(DotaCraft.MOD_ID, "max_" + type);
        DotaAttribute maxAttrType = ModRegistries.ATTRIBUTES.get(maxId);
        double maxValue = attributes.getAttribute(maxAttrType).get();

        // Delegate to value-based overload
        return executeHero(context, type, maxValue);
    }

    /**
     * Execute setting health/mana with explicit value.
     */
    private static int executeHero(CommandContext<ServerCommandSource> context, String type, double value)
            throws CommandSyntaxException {
        ServerPlayerEntity player = getPlayer(context);
        HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);

        // Set the appropriate field
        switch (type.toLowerCase()) {
            case "health" -> heroComponent.setHealth(value);
            case "mana"   -> heroComponent.setMana(value);
            default -> throw new SimpleCommandExceptionType(
                    Text.literal("Invalid hero type: " + type)).create();
        }

        // Sync and feedback
        heroComponent.sync();
        context.getSource().sendFeedback(
                () -> Text.literal("Set " + type + " to " + value + " for " + player.getName().getString()),
                false
        );
        return Command.SINGLE_SUCCESS;
    }

    /**
     * Execute setting a general DotaAttribute with explicit value.
     * (unchanged logic, but uses helper getPlayer)
     */
    private static int executeAttribute(CommandContext<ServerCommandSource> context, String attributeName)
            throws CommandSyntaxException {
        ServerPlayerEntity player = getPlayer(context);
        double value = DoubleArgumentType.getDouble(context, "value");

        DotaAttribute attributeType;
        try {
            attributeType = ModRegistries.ATTRIBUTES
                    .get(new Identifier(DotaCraft.MOD_ID, attributeName.toLowerCase()));
        } catch (IllegalArgumentException e) {
            throw new SimpleCommandExceptionType(
                    Text.literal("Invalid attribute name: " + attributeName)).create();
        }

        AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
        attributes.getAttribute(attributeType).set(value);
        attributes.sync();

        context.getSource().sendFeedback(
                () -> Text.literal("Set " + attributeName + " to " + value + " for " + player.getName().getString()),
                false
        );
        return Command.SINGLE_SUCCESS;
    }
}
