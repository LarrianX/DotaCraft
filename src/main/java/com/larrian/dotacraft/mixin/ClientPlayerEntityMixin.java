package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.component.HeroComponent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.RUNE_INVISIBILITY_EFFECT;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method="shouldSpawnSprintingParticles()Z", at = @At("HEAD"), cancellable = true)
    private void onShouldSpawnSprintingParticles(CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (player.getComponent(HERO_COMPONENT).isHero() && player.getStatusEffect(RUNE_INVISIBILITY_EFFECT) != null) {
            cir.setReturnValue(false);
        }
    }
}
