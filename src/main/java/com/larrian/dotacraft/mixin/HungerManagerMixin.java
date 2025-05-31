package com.larrian.dotacraft.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void onUpdate(PlayerEntity player, CallbackInfo ci) {
        if (player.getComponent(HERO_COMPONENT).isHero())
            ci.cancel();
    }
}