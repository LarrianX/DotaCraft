package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.component.HeroComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    private void injectDamage(DamageSource source, float amount, CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntity player) {
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            if (component.isHero()) {
                component.addHealth(-amount);
                ci.cancel();
            }
        }
    }
}
