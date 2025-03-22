package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.attributes.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.*;

public class ResetCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("reset")
                        .executes(ResetCommand::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
            heroComponent.setHealth(0);
            heroComponent.setMana(0);
            heroComponent.setHero(true);

            AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
            attributes.setLevel(0);
            for (DotaAttributeType attribute : DotaAttributeType.values()) {
                attributes.getAttribute(attribute).clearModifiers();
                attributes.getAttribute(attribute).set(0);
            }
            attributes.getAttribute(DotaAttributeType.MAX_HEALTH).set(120);
            attributes.getAttribute(DotaAttributeType.MAX_MANA).set(75);
            attributes.sync();
        }

        return 1;
    }
}
