package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.OldValuesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.*;

public class UndoCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("dotacraft:undo")
                        .executes(UndoCommand::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (attribute != null) {
                // Выставление старых значений
                OldValuesComponent OldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);
                int maxHealth = OldValuesComponent.getOldMaxHealth();
                attribute.setBaseValue((maxHealth == 0.0) ? 20.0F : maxHealth);
                float health = OldValuesComponent.getOldHealth();
                player.setHealth((health == 0.0) ? 20.0F : health);
                // Удаление у игрока поле героя
                HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
                heroComponent.setHero(false);
                heroComponent.sync();
            }
        }

        return 1;
    }
}
