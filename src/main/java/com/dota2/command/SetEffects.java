package com.dota2.command;

import com.dota2.component.EffectComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;

import static com.dota2.component.ModComponents.EFFECT_COMPONENT;

public class SetEffects {

    // Список доступных эффектов
    private static final List<String> ALLOWED_EFFECTS = Arrays.asList(
            "regeneration_health",
            "regeneration_mana"
    );

    // Создаем SuggestionProvider, который будет предоставлять наши эффекты
    private static final SuggestionProvider<ServerCommandSource> EFFECT_SUGGESTIONS = (context, builder) -> {
        for (String effect : ALLOWED_EFFECTS) {
            builder.suggest(effect);
        }
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set_effect")
                        .then(CommandManager.argument("name", StringArgumentType.string())
                                // Добавляем подсказки для аргумента "name"
                                .suggests(EFFECT_SUGGESTIONS)
                                .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                        .executes(SetEffects::execute)))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            String name = StringArgumentType.getString(context, "name");
            double value = DoubleArgumentType.getDouble(context, "value");

            // Проверяем, что введенное имя содержится в списке разрешенных эффектов
            if (!ALLOWED_EFFECTS.contains(name)) {
                throw new SimpleCommandExceptionType(Text.literal("Недопустимый эффект: " + name)).create();
            }

            EffectComponent component = player.getComponent(EFFECT_COMPONENT);
            component.getAmplifiers().put(name, value);
            component.sync();
        }

        return 1;
    }
}