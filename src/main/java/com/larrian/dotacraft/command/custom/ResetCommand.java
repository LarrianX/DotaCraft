package com.larrian.dotacraft.command.custom;

import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.ModRegistries;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

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
            heroComponent.setLevel(0);
            heroComponent.sync();

            AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
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
