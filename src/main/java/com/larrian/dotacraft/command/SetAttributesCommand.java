package com.larrian.dotacraft.command;

import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.component.AttributesComponent;
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

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class SetAttributesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("set");
        for (DotaAttributes type : DotaAttributes.values()) {
            command.then(
                    CommandManager.literal(type.name())
                            .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                    .executes((context -> execute(context, type.name())))));
        }

        dispatcher.register(command);
    }

    private static int execute(CommandContext<ServerCommandSource> context, String attributeName) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) {
            throw new SimpleCommandExceptionType(Text.literal("This command can only be used by a player.")).create();
        }

        double value = DoubleArgumentType.getDouble(context, "value");

        DotaAttributes attributeType;
        try {
            attributeType = DotaAttributes.valueOf(attributeName.toUpperCase());
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
