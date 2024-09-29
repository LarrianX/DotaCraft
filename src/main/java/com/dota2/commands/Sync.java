package com.dota2.commands;

import com.dota2.components.HeroComponents.HeroComponent;
import com.dota2.components.HeroComponents.OldValuesComponent;
import com.dota2.components.ModComponents;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.components.ModComponents.*;

public class Sync {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("sync")
                        .executes(Sync::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        HERO_COMPONENT.sync(player);
        VALUES_COMPONENT.sync(player);
        MAX_VALUES_COMPONENT.sync(player);

        return 1;
    }
}
