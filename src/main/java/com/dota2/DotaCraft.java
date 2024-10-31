package com.dota2;

import com.dota2.attributes.ModAttributes;
import com.dota2.block.ModBlocks;
import com.dota2.command.ModCommands;
import com.dota2.effect.ModEffects;
import com.dota2.event.ModEvents;
import com.dota2.item.ModItemGroups;
import com.dota2.item.ModItems;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DotaCraft implements ModInitializer {
    public static final String MOD_ID = "dotacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Random random = new Random();

    public static Entity getTargetedEntity(World world, PlayerEntity user, double reachDistance) {
        // Выполняем raycast (луч) для поиска объекта, на который смотрит игрок
        Vec3d cameraPos = user.getCameraPosVec(1.0F);
        Vec3d lookVec = user.getRotationVec(1.0F);
        Vec3d reachVec = cameraPos.add(lookVec.multiply(reachDistance));

        // Поиск ближайшей сущности в пределах досягаемости
        Entity targetedEntity = null;
        List<Entity> entities = world.getEntitiesByClass(Entity.class, user.getBoundingBox().stretch(lookVec.multiply(reachDistance)).expand(1.0D), e -> e != user);

        for (Entity entity : entities) {
            Box entityBox = entity.getBoundingBox().expand(entity.getTargetingMargin());
            Optional<Vec3d> optional = entityBox.raycast(cameraPos, reachVec);

            if (entityBox.contains(cameraPos) || optional.isPresent()) {
                targetedEntity = entity;
                break;
            }
        }
        return targetedEntity;
    }

    public static void executeCommand(MinecraftServer server, String command) throws CommandSyntaxException {
        ServerCommandSource source = server.getCommandSource();
        ParseResults<ServerCommandSource> parseResults = server.getCommandManager().getDispatcher().parse(command, source);
        server.getCommandManager().getDispatcher().execute(parseResults);
    }

    private Team createTeam(MinecraftServer server, String teamName) {
        Scoreboard scoreboard = server.getScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.addTeam(teamName);
            team.setFriendlyFireAllowed(false);
        }
        return team;
    }

    private void serverStarted(MinecraftServer server) {
        createTeams(server);
    }

    private void createTeams(MinecraftServer server) {
        Team light = createTeam(server, "Radiant");
        light.setColor(Formatting.WHITE);
        Team black = createTeam(server, "Dire");
        black.setColor(Formatting.BLACK);
    }

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ModEffects.registerModEffects();
        ModCommands.registerModCommands();
        ModAttributes.registerModAttributes();
        ModEvents.register();
        ServerLifecycleEvents.SERVER_STARTED.register(this::serverStarted);
    }
}