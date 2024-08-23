package com.dota2.mixin;

import com.dota2.DotaCraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
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

    @Unique
    private PlayerEntity getCameraPlayer() {
        MinecraftClient client = ((InGameHudAccessor) this).getClient();
        return !(client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity)client.getCameraEntity();
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V", at = @At("HEAD"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci) {
        ci.cancel();
        PlayerEntity playerEntity = getCameraPlayer();
        if (playerEntity != null) {
            int x = context.getScaledWindowWidth() / 2 + DotaCraftClient.getX();
            int y = context.getScaledWindowHeight() + DotaCraftClient.getY();

            double health = playerEntity.getHealth();
            int pixels = calculateHealth(health);

            context.drawTexture(ICONS, x, y, 0, 0, 1, 9);
            x += 1;
            context.drawTexture(ICONS, x, y, 1, 0, pixels, 9);
            x += pixels;
            context.drawTexture(ICONS, x, y, 1 + pixels, 9, MAX_PIXELS - pixels, 9);
            x += MAX_PIXELS - pixels;
            context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 0, 1, 9);
        }
    }
}
