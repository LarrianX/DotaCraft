package com.dota2.mixin;

import com.dota2.components.HeroAttributes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.dota2.components.ModComponents.HERO_ATTRIBUTES;

@Mixin(InGameHud.class)
public class ReplaceBars {
    @Unique
    private static final Identifier ICONS = new Identifier("dotacraft:textures/icons.png");
    @Unique
    private static final int MAX_PIXELS = 88;

    @Unique
    private int calculatePixels(int value, int max_value) {
        return MAX_PIXELS * (value / max_value);
    }

    @Unique
    private void drawHealthBar(DrawContext context, int pixels) {
        int x = context.getScaledWindowWidth() / 2 - 91;
        int y = context.getScaledWindowHeight() - 39;

        context.drawTexture(ICONS, x, y, 0, 0, 1, 9);  // Левая граница
        x += 1;
        context.drawTexture(ICONS, x, y, 1, 0, pixels, 9); // Заполненная часть
        x += pixels;
        context.drawTexture(ICONS, x, y, 1 + pixels, 9, MAX_PIXELS - pixels, 9); // Незаполненная часть
        x += MAX_PIXELS - pixels;
        context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 0, 1, 9); // Правая граница
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci, @Local PlayerEntity playerEntity) {
        if (playerEntity != null) {
            HeroAttributes component = playerEntity.getComponent(HERO_ATTRIBUTES);
            if (component.isHero()) {
                ci.cancel();

                int health = component.getHealth();
                int max_health = component.getMaxHealth();
                int mana = component.getMana();
                int max_mana = component.getMaxMana();

                int pixels = calculatePixels(health, max_health);
                drawHealthBar(context, pixels);
            }
        }
    }
}
