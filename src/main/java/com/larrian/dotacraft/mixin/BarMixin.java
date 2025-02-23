package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.component.hero.AttributesComponent;
import com.larrian.dotacraft.component.hero.HealthComponent;
import com.larrian.dotacraft.component.hero.HeroComponent;
import com.larrian.dotacraft.component.hero.ManaComponent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.Set;

import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.*;


@Mixin(InGameHud.class)
public class BarMixin {
    @Unique
    private static final Identifier ICONS = new Identifier("dotacraft:textures/icons.png");
    @Unique
    private static final int MAX_PIXELS = 88;
    @Unique
    private static final DecimalFormat outputFormat = new DecimalFormat("00");


    @Unique
    private int calculatePixels(int value, int maxValue) {
        if (maxValue == 0) {
            return 0;
        }
        return (int) Math.min(MAX_PIXELS * ((double) value / (double) maxValue), MAX_PIXELS);
    }

    @Unique
    private int correctPosition(int value) {
//        (MAX_PIXELS / 2) - (7 * 3)
        int correctLen = (3 - String.valueOf(value).length()) * 6;
        return (MAX_PIXELS / 2) - (3 * 6) + correctLen;
    }

    @Unique
    private void drawHealthBar(DrawContext context, int health, int max_health, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 91;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(health, max_health);

        context.drawTexture(ICONS, x, y, 0, 0, 1, 9);  // Левая граница
        x += 1;
        context.drawTexture(ICONS, x, y, 1, 0, pixels, 9); // Заполненная часть
        x += pixels;
        context.drawTexture(ICONS, x, y, 1 + pixels, 9, MAX_PIXELS - pixels, 9); // Незаполненная часть
        x += MAX_PIXELS - pixels;
        context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 0, 1, 9); // Правая граница
        // Надпись
        drawHealthText(context, health, max_health, client);
    }

    @Unique
    private void drawHealthText(DrawContext context, int health, int max_health, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 91 + correctPosition(health);
        int y = context.getScaledWindowHeight() - 39;
        String text = health + "/" + outputFormat.format(max_health);

        context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);

        if (client.player != null) {
            x = context.getScaledWindowWidth() / 2 - 91;
            text = "+" + Math.round(client.player.getAttributeValue(REGENERATION_HEALTH) * 20);

            context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);
        }
    }

    @Unique
    private void drawManaBar(DrawContext context, int mana, int max_mana, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + 1;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(mana, max_mana);

        // Полоска маны
        context.drawTexture(ICONS, x, y, 0, 18, 1, 9);  // Левая граница
        x += 1;
        context.drawTexture(ICONS, x, y, 1, 18, pixels, 9); // Заполненная часть
        x += pixels;
        context.drawTexture(ICONS, x, y, 1 + pixels, 27, MAX_PIXELS - pixels, 9); // Незаполненная часть
        x += MAX_PIXELS - pixels;
        context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 18, 1, 9); // Правая граница
        // Надпись
        drawManaText(context, mana, max_mana, client);
    }

    @Unique
    private void drawManaText(DrawContext context, int mana, int max_mana, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + correctPosition(mana);
        int y = context.getScaledWindowHeight() - 39;
        String text = mana + "/" + outputFormat.format(max_mana);

        context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);

        if (client.player != null) {
            x = context.getScaledWindowWidth() / 2;
            text = "+" + Math.round(client.player.getAttributeValue(REGENERATION_MANA) * 20);

            context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);
        }
    }

    @Unique
    private void drawTexts(DrawContext context, PlayerEntity player, Set<Integer> blockedSlots, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + 100;
        int y = context.getScaledWindowHeight() - 80;

        AbstractTeam team = player.getScoreboardTeam();
        if (team != null) {
            context.drawTextWithShadow(client.textRenderer, team.getName(), x, y, 16777215);
            y += 10;
        }
        for (Integer slot : blockedSlots) {
            context.drawTextWithShadow(client.textRenderer, slot.toString(), x, y, 16777215);
            y += 10;
        }
        AttributesComponent component = player.getComponent(ATTRIBUTES_COMPONENT);
        NbtCompound nbt = new NbtCompound();
        component.writeToNbt(nbt);
        for (String key : nbt.getKeys()) {
            context.drawTextWithShadow(client.textRenderer, key, x, y, 16777215);
            context.drawTextWithShadow(client.textRenderer, String.valueOf((int)nbt.getDouble(key)), x + 132, y, 16777215);
            y += 10;
        }
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci, @Local PlayerEntity player) {
        if (player != null) {
            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
            if (heroComponent.isHero()) {
                ci.cancel();

                ManaComponent manaComponent = player.getComponent(MANA_COMPONENT);
                HealthComponent healthComponent = player.getComponent(HEALTH_COMPONENT);

                int mana = (int) manaComponent.get();
                int health = (int) healthComponent.get();
                int maxMana = (int) player.getAttributeValue(MAX_MANA);
                int maxHealth = (int) player.getAttributeValue(MAX_HEALTH);
                Set<Integer> blockedSlots = heroComponent.getBlocked();
                MinecraftClient client = MinecraftClient.getInstance();

                drawManaBar(context, mana, maxMana, client);
                drawHealthBar(context, health, maxHealth, client);
                drawTexts(context, player, blockedSlots, client);
            }
        }
    }
}
