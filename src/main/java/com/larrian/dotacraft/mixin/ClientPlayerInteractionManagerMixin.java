package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.component.hero.HeroComponent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void attackEntity(CallbackInfo ci, @Local(argsOnly = true) PlayerEntity player, @Local(argsOnly = true) Entity target) {
        if (target instanceof ItemEntity && player.getComponent(HERO_COMPONENT).isHero()) {
            ci.cancel();
            // Предмет невозможно ударить: игрока кикнет. Фикс этого
        }
    }
}
