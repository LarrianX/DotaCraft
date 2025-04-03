package com.larrian.dotacraft.event;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.event.server.AllowDamageEvent;
import com.larrian.dotacraft.event.server.ReturnItemsEvent;
import com.larrian.dotacraft.hero.Skill;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class ServerEvents {
    public static final Identifier AUTO_CRAFT_PACKET = new Identifier(DotaCraft.MOD_ID, "auto_craft");
    public static final Identifier SKILL_PACKET = new Identifier(DotaCraft.MOD_ID, "hero_action");

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(AllowDamageEvent::event);
        ServerTickEvents.START_WORLD_TICK.register(ReturnItemsEvent::event);
        ServerPlayNetworking.registerGlobalReceiver(AUTO_CRAFT_PACKET, ServerEvents::handleAutoCraft);
        ServerPlayNetworking.registerGlobalReceiver(SKILL_PACKET, ServerEvents::handleSkill);
    }

    private static void handleAutoCraft(MinecraftServer server, ServerPlayerEntity player,
                                        ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);
        if (component.isHero()) {
            new AutoCraftPacket(buf).craft(player);
        }
    }

    private static void handleSkill(MinecraftServer server, ServerPlayerEntity player,
                                  ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        player.getComponent(HERO_COMPONENT).useSkill(new SkillPacket(buf).getType());
    }
}
