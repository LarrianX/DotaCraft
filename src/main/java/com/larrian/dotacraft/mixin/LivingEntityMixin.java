package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "updatePotionVisibility", at = @At("TAIL"))
    private void addRuneInvisibilityEffect(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.hasStatusEffect(StatusEffects.INVISIBILITY) || self.hasStatusEffect(ModEffects.RUNE_INVISIBILITY_EFFECT)) {
            self.setInvisible(true);
        }
    }
}

