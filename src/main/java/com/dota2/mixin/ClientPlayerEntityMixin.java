package com.dota2.mixin;

import com.dota2.component.hero.HeroComponent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.dota2.component.ModComponents.HERO_COMPONENT;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method="shouldSpawnSprintingParticles()Z", at = @At("HEAD"), cancellable = true)
    private void onShouldSpawnSprintingParticles(CallbackInfoReturnable<Boolean> cir) {
        // Удаление частиц бега если игрок невидим
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (player.getComponent(HERO_COMPONENT).isHero() && player.getStatusEffect(StatusEffects.INVISIBILITY) != null) {
            cir.setReturnValue(false);
        }
    }
}
