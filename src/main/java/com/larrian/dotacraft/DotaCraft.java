package com.larrian.dotacraft;

import com.larrian.dotacraft.dota.ModAttributes;
import com.larrian.dotacraft.dota.ModHeroes;
import com.larrian.dotacraft.event.ServerEvents;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import static com.larrian.dotacraft.ModItems.*;

public class DotaCraft implements ModInitializer {
    public static final String MOD_ID = "dotacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Constants
    public static final boolean DEBUG = false;
    public static final boolean AUTO_CRAFT = true;
    public static final double ERROR = 0.00000000001;

    public static final HashMap<Item[], Item> RECIPES = new HashMap<>();
    static {
        RECIPES.put(new Item[]{DEMON_EDGE, CRYSTALYS, REC_DAEDALUS}, DAEDALUS);
//        RECIPES.put(new Item[]{DEMON_EDGE}, DEMON_EDGE);

    }

    public static Entity getTargetedEntity(World world, Entity entity, double reachDistance) {
        HitResult crosshairTarget = entity.raycast(reachDistance, 1.0F, false);
        Vec3d vec3d = entity.getCameraPosVec(1.0F);
        double e = reachDistance;

        e *= e;
        if (crosshairTarget != null) {
            e = crosshairTarget.getPos().squaredDistanceTo(vec3d);
        }

        Vec3d vec3d2 = entity.getRotationVec(1.0F);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * reachDistance, vec3d2.y * reachDistance, vec3d2.z * reachDistance);
        Box box = entity.getBoundingBox().stretch(vec3d2.multiply(reachDistance)).expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(entity, vec3d, vec3d3, box, entityx -> !entityx.isSpectator() && entityx.canHit() || entityx instanceof ItemEntity, e);
        if (entityHitResult != null) {
            return entityHitResult.getEntity();
        } else {
            return null;
        }
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
        ModRegistries.registerModRegistries();
        ModAttributes.registerModAttributes();
        ModHeroes.registerModHeroes();
        ModEntities.registerModEntities();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ModEffects.registerModEffects();
        ModCommands.registerModCommands();
        ServerEvents.register();
        ServerLifecycleEvents.SERVER_STARTED.register(this::serverStarted);
    }
}