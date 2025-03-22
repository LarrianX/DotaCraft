package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.component.attributes.AttributesComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.attributes.DotaAttribute;
import com.larrian.dotacraft.component.attributes.DotaAttributeType;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.Set;

import static com.larrian.dotacraft.init.ModComponents.*;

@Mixin(InGameHud.class)
public class BarsMixin {

    @Unique
    private static final Identifier ICONS = new Identifier("dotacraft:textures/icons.png");

    @Unique
    private static final int MAX_PIXELS = 88;

    @Unique
    private static final DecimalFormat OUTPUT_FORMAT = new DecimalFormat("00");
    @Unique
    private static final DecimalFormat ATTRIBUTES_FORMAT = new DecimalFormat("#.##########");

    // Calculates the number of pixels for the filled part of the bar.
    @Unique
    private int calculatePixels(int value, int maxValue) {
        if (maxValue == 0) {
            return 0;
        }
        return (int) Math.min(MAX_PIXELS * ((double) value / maxValue), MAX_PIXELS);
    }

    @Unique
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char first = Character.toUpperCase(str.charAt(0));
        return first + str.substring(1);
    }

    @Unique
    private int correctPosition(int value) {
        int correctLen = (3 - String.valueOf(value).length()) * 6;
        return (MAX_PIXELS / 2) - (3 * 6) + correctLen;
    }

    @Unique
    private void drawHealthBar(DrawContext context, int health, int maxHealth, double regenHealth, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 91;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(health, maxHealth);

        // Left border
        context.drawTexture(ICONS, x, y, 0, 0, 1, 9);
        x += 1;
        // Filled part
        context.drawTexture(ICONS, x, y, 1, 0, pixels, 9);
        x += pixels;
        // Unfilled part
        context.drawTexture(ICONS, x, y, 1 + pixels, 9, MAX_PIXELS - pixels, 9);
        x += MAX_PIXELS - pixels;
        // Right border
        context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 0, 1, 9);

        drawHealthText(context, health, maxHealth, regenHealth, client);
    }

    @Unique
    private void drawHealthText(DrawContext context, int health, int maxHealth, double regenHealth, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 91 + correctPosition(health);
        int y = context.getScaledWindowHeight() - 39;
        String text = health + "/" + OUTPUT_FORMAT.format(maxHealth);
        context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);

        if (client.player != null) {
            x = context.getScaledWindowWidth() / 2 - 91;
            text = "+" + Math.round(regenHealth);
            context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);
        }
    }

    @Unique
    private void drawManaBar(DrawContext context, int mana, int maxMana, double regenMana, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + 1;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(mana, maxMana);

        // Left border
        context.drawTexture(ICONS, x, y, 0, 18, 1, 9);
        x += 1;
        // Filled part
        context.drawTexture(ICONS, x, y, 1, 18, pixels, 9);
        x += pixels;
        // Unfilled part
        context.drawTexture(ICONS, x, y, 1 + pixels, 27, MAX_PIXELS - pixels, 9);
        x += MAX_PIXELS - pixels;
        // Right border
        context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 18, 1, 9);

        drawManaText(context, mana, maxMana, regenMana, client);
    }

    @Unique
    private void drawManaText(DrawContext context, int mana, int maxMana, double regenMana, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + correctPosition(mana);
        int y = context.getScaledWindowHeight() - 39;
        String text = mana + "/" + OUTPUT_FORMAT.format(maxMana);
        context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);

        if (client.player != null) {
            x = context.getScaledWindowWidth() / 2;
            text = "+" + Math.round(regenMana);
            context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);
        }
    }

    @Unique
    private void drawTextPair(DrawContext context, MinecraftClient client, String key, String value, int x, int y) {
        context.drawTextWithShadow(client.textRenderer, key, x, y, 16777215);
        context.drawTextWithShadow(client.textRenderer, String.valueOf(value), x + 125, y, 16777215);
    }

    @Unique
    private void drawTexts(DrawContext context, AbstractTeam team, Set<Integer> blockedSlots, AttributesComponent attributes, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + 110;
        int y = context.getScaledWindowHeight() - 150;

        // Draw team name if exists.
        if (team != null) {
            context.drawTextWithShadow(client.textRenderer, team.getName(), x, y, 16777215);
            y += 10;
        }

        // Draw blocked slots.
        for (Integer slot : blockedSlots) {
            context.drawTextWithShadow(client.textRenderer, slot.toString(), x, y, 16777215);
            y += 10;
        }

        // Manually query AttributesComponent for each attribute.
        for (DotaAttributeType type : DotaAttributeType.values()) {
            DotaAttribute attribute = attributes.getAttribute(type);
            drawTextPair(context, client, type.name().toLowerCase() + ": ",ATTRIBUTES_FORMAT.format(attribute.getBase()) + " -> " + ATTRIBUTES_FORMAT.format(attribute.get()), x, y);
            y += 10;
        }
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"),
            cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci, @Local PlayerEntity player) {
        if (player != null) {
            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
            if (heroComponent.isHero()) {
                ci.cancel();
                AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

                int health = (int) heroComponent.getHealth();
                int mana = (int) heroComponent.getMana();
                int maxHealth = (int) attributes.getAttribute(DotaAttributeType.MAX_HEALTH).get();
                int maxMana = (int) attributes.getAttribute(DotaAttributeType.MAX_MANA).get();
                double regenHealth = attributes.getAttribute(DotaAttributeType.REGENERATION_HEALTH).get();
                double regenMana = attributes.getAttribute(DotaAttributeType.REGENERATION_MANA).get();
                Set<Integer> blockedSlots = heroComponent.getBlocked();
                MinecraftClient client = MinecraftClient.getInstance();

                drawHealthBar(context, health, maxHealth, regenHealth, client);
                drawManaBar(context, mana, maxMana, regenMana, client);
                drawTexts(context, player.getScoreboardTeam(), blockedSlots, attributes, client);
            }
        }
    }
}
