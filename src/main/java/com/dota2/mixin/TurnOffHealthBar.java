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

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"), cancellable = true)
    private void onRenderHealthBar(DrawContext context, CallbackInfo ci) {
        ci.cancel();
//        context.drawTexture(ICON, 40, 40, DotaCraftClient.getIndex(), 0, 40, 40);
        context.drawTexture(ICON, 40, 40, 0, (float)DotaCraftClient.getIndex(), (float)0, 40, 40, 40, 40);
    }
}
