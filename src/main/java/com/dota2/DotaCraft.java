package com.dota2;

import com.dota2.attributes.ModAttributes;
import com.dota2.block.ModBlocks;
import com.dota2.command.ModCommands;
import com.dota2.component.hero.HeroComponent;
import com.dota2.component.hero.ValuesComponent;
import com.dota2.effect.CustomEffect;
import com.dota2.effect.ModEffects;
import com.dota2.event.ModEvents;
import com.dota2.item.ModItemGroups;
import com.dota2.item.ModItems;
import com.dota2.item.Weapon;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.dota2.attributes.ModAttributes.CRIT_CHANCE;
import static com.dota2.component.ModComponents.HERO_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public class DotaCraft implements ModInitializer {
    public static final String MOD_ID = "dotacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Random random = new Random();

    private static int calculateDamage(PlayerEntity provider) {
        int totalDamage = 0;

        for (int i = 0; i < provider.getInventory().size(); i++) {
            ItemStack stack = provider.getInventory().getStack(i);

            if (stack.getItem() instanceof Weapon weapon) {
                totalDamage += weapon.getDamage();
            }
        }

        return totalDamage;
    }

    public static ActionResult onAttackEntity(PlayerEntity playerSource, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if (entity instanceof PlayerEntity playerTarget) {
            HeroComponent heroComponentSource = playerSource.getComponent(HERO_COMPONENT);
            HeroComponent heroComponentTarget = playerTarget.getComponent(HERO_COMPONENT);
            if (heroComponentTarget.isHero() && heroComponentSource.isHero() &&
                    !playerSource.isTeammate(playerTarget)) {
                // Уменьшение хп
                int damage = calculateDamage(playerSource);
                EntityAttributeInstance critAttribute = playerSource.getAttributeInstance(CRIT_CHANCE);
                if (critAttribute != null) {
                    ValuesComponent valuesComponentTarget = playerTarget.getComponent(VALUES_COMPONENT);
                    valuesComponentTarget.addHealth(-damage); // TODO: доделать крит удар
                    valuesComponentTarget.sync();
                }
                // Убирание эффектов
                playerSource.removeStatusEffect(StatusEffects.INVISIBILITY);
                Collection<StatusEffectInstance> effects = playerTarget.getStatusEffects();
                for (StatusEffectInstance effectInstance : effects) {
                    if (effectInstance.getEffectType() instanceof CustomEffect effect &&
                        !effect.isPersistent()) {
                        playerTarget.removeStatusEffect(effect);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

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
        ModEvents.AllowDamage();
        ModItemGroups.registerItemGroups();
        ModEffects.registerModEffects();
        ModCommands.registerModCommands();
        ModAttributes.registerModAttributes();
        AttackEntityCallback.EVENT.register(DotaCraft::onAttackEntity);
        ServerLifecycleEvents.SERVER_STARTED.register(this::serverStarted);
    }
}