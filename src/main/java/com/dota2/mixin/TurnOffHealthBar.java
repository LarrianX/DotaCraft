package com.dota2.mixin;

import com.dota2.DotaCraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class TurnOffHealthBar {
    @Unique
    private static final Identifier ICONS = new Identifier("dotacraft:textures/icons.png");
    @Unique
    private static final int MAX_PIXELS = 88;
    @Unique
    private static final double MAX_HEALTH = 20.0;

    @Unique
    private int calculateHealth(double health) {
        return calculatePixels(health / MAX_HEALTH);
    }

    @Unique
    private int calculatePixels(double state) {
        return (int) (MAX_PIXELS * state);
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V", at = @At("HEAD"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci) {
        ci.cancel();
        int x = context.getScaledWindowWidth() / 2 + DotaCraftClient.getX();
        int y = context.getScaledWindowHeight() + DotaCraftClient.getY();
        int pixels = calculateHealth(DotaCraftClient.getHealth());  // 45
        context.drawTexture(ICONS, x, y, 0, 0, pixels + 1, 9);
        context.drawTexture(ICONS, x + pixels + 1, y, pixels + 2, 9, MAX_PIXELS - pixels + 1, 9);
    }
}
