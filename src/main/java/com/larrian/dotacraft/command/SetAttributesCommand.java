package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.larrian.dotacraft.component.attributes.AttributesComponent;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class SetAttributesCommand {
    private static final SuggestionProvider<ServerCommandSource> SUGGESTION_ATTRIBUTES = (context, builder) -> {
        for (DotaAttributeType type : DotaAttributeType.values()) {
            builder.suggest(type.name());
        }
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set")
                        .then(CommandManager.argument("attribute", StringArgumentType.string())
                                .suggests(SUGGESTION_ATTRIBUTES)
                                .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                        .executes(SetAttributesCommand::execute))));
    }

    private static int execute(com.mojang.brigadier.context.CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) {
            throw new SimpleCommandExceptionType(Text.literal("This command can only be used by a player.")).create();
        }

        String attributeName = StringArgumentType.getString(context, "attribute");
        double value = DoubleArgumentType.getDouble(context, "value");

        DotaAttributeType attributeType;
        try {
            attributeType = DotaAttributeType.valueOf(attributeName.toUpperCase());
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
