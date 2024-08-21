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
    private static final Identifier ICON = new Identifier("dotacraft:textures/test.png");

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V", at = @At("HEAD"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci) {
        ci.cancel();
        int x = context.getScaledWindowWidth() / 2 + DotaCraftClient.getX();
        int y = context.getScaledWindowHeight() + DotaCraftClient.getY();
        context.drawTexture(ICON, x, y, 0, 0, 0, 40, 40, 40, 40);
    }
}
