package com.dota2.mixin;

import com.dota2.DotaCraft;
import com.dota2.components.HeroComponents.HeroComponent;
import com.dota2.components.HeroComponents.MaxValuesComponent;
import com.dota2.components.HeroComponents.ValuesComponent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

import static com.dota2.components.ModComponents.*;


@Mixin(InGameHud.class)
public class ReplaceBars {
    @Unique
    private static final Logger LOGGER = DotaCraft.LOGGER;
    @Unique
    private static final Identifier ICONS = new Identifier("dotacraft:textures/icons.png");
    @Unique
    private static final int MAX_PIXELS = 88;
    @Unique
    private static final DecimalFormat outputFormat = new DecimalFormat("000");
    @Shadow
    @Final
    private PlayerListHud playerListHud;

    @Unique
    private int calculatePixels(int value, int max_value) {
        if (max_value == 0) {
            return 0;
        }
        return (int) (MAX_PIXELS * ((double) value / (double) max_value));
    }

    @Unique
    private int correctPosition(int value) {
//        (MAX_PIXELS / 2) - (7 * 3)
        int correct_len = (3 - String.valueOf(value).length()) * 6;
        return (MAX_PIXELS / 2) - (3 * 6) + correct_len;
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
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci, @Local PlayerEntity playerEntity) {
        if (playerEntity != null) {
            HeroComponent heroComponent = playerEntity.getComponent(HERO_COMPONENT);
            if (heroComponent.isHero()) {
                ci.cancel();

                ValuesComponent valuesComponent = playerEntity.getComponent(VALUES_COMPONENT);
                MaxValuesComponent maxValuesComponent = playerEntity.getComponent(MAX_VALUES_COMPONENT);

                int mana = (int) valuesComponent.getMana();
                int health = (int) valuesComponent.getHealth();
                int max_mana = maxValuesComponent.getMaxMana();
                int max_health = maxValuesComponent.getMaxHealth();
                MinecraftClient client = MinecraftClient.getInstance();

                drawManaBar(context, mana, max_mana, client);
                drawHealthBar(context, health, max_health, client);
            }
        }
    }
}
