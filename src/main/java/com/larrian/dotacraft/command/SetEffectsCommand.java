package com.larrian.dotacraft.command;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.component.EffectComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static com.larrian.dotacraft.init.ModComponents.EFFECT_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.EFFECTS;

public class SetEffectsCommand {
    private static final List<String> IDS;
    static {
        IDS = new ArrayList<>();
        for (StatusEffect effect : EFFECTS) {
            if (effect instanceof Custom) {
                IDS.add(((Custom) effect).getId());
            }
        }
    }
    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS = (context, builder) -> {
        for (String effect : IDS) {
            builder.suggest(effect);
        }
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set_effect")
                        .then(CommandManager.argument("name", StringArgumentType.string())
                                // Добавляем подсказки для аргумента "name"
                                .suggests(SUGGESTIONS)
                                .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                        .executes(SetEffectsCommand::execute)))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            String name = StringArgumentType.getString(context, "name");
            double value = DoubleArgumentType.getDouble(context, "value");

            // Проверяем, что введенное имя содержится в списке разрешенных эффектов
            if (!IDS.contains(name)) {
                throw new SimpleCommandExceptionType(Text.literal("Недопустимый эффект: " + name)).create();
            }

            EffectComponent component = player.getComponent(EFFECT_COMPONENT);
            component.getAmplifiers().put(name, value);
            component.sync();
        }

        return 1;
    }
}