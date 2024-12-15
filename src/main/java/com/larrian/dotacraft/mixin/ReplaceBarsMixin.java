package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.component.EffectComponent;
import com.larrian.dotacraft.component.hero.HeroComponent;
import com.larrian.dotacraft.component.hero.ValuesComponent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.*;


@Mixin(InGameHud.class)
public class ReplaceBarsMixin {
    @Unique
    private static final Identifier ICONS = new Identifier("dotacraft:textures/icons.png");
    @Unique
    private static final int MAX_PIXELS = 88;
    @Unique
    private static final DecimalFormat outputFormat = new DecimalFormat("000");


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
    private void drawTexts(DrawContext context, Map<String, Double> amplifiers, Set<Integer> blockedSlots, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + 100;
        int y = context.getScaledWindowHeight() - 50;

        for (Map.Entry<String, Double> entry : amplifiers.entrySet()) {
            context.drawTextWithShadow(client.textRenderer, entry.getKey(), x, y, 16777215);
            context.drawTextWithShadow(client.textRenderer, entry.getValue().toString(), x + 132, y, 16777215);
            y -= 10;
        }
        for (Integer slot : blockedSlots) {
            context.drawTextWithShadow(client.textRenderer, slot.toString(), x, y, 16777215);
            y -= 10;
        }
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci, @Local PlayerEntity playerEntity) {
        if (playerEntity != null) {
            HeroComponent heroComponent = playerEntity.getComponent(HERO_COMPONENT);
            if (heroComponent.isHero()) {
                ci.cancel();

                ValuesComponent valuesComponent = playerEntity.getComponent(VALUES_COMPONENT);
                EffectComponent effectComponent = playerEntity.getComponent(EFFECT_COMPONENT);

                int mana = (int) valuesComponent.getMana();
                int health = (int) valuesComponent.getHealth();
                int maxMana = (int) playerEntity.getAttributeValue(MAX_MANA);
                int maxHealth = (int) playerEntity.getAttributeValue(MAX_HEALTH);
                Map<String, Double> amplifiers = effectComponent.getAmplifiers();
                Set<Integer> blockedSlots = heroComponent.getBlocked();
                MinecraftClient client = MinecraftClient.getInstance();

                drawManaBar(context, mana, maxMana, client);
                drawHealthBar(context, health, maxHealth, client);
                drawTexts(context, amplifiers, blockedSlots, client);
            }
        }
    }
}
