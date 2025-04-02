package com.larrian.dotacraft.command;

import com.larrian.dotacraft.attributes.DotaAttribute;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.init.ModRegistries;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

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
            heroComponent.sync();

            AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
            attributes.setLevel(1);
            for (DotaAttribute attribute : ModRegistries.ATTRIBUTES) {
                attributes.getAttribute(attribute).clearModifiers();
                attributes.getAttribute(attribute).set(0);
            }
            DotaHero hero = heroComponent.getHero();
            if (hero != null) {
//                hero.setAttributes(attributes);
            }

            attributes.sync();
        }

        return 1;
    }
}
