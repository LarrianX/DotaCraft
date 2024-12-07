package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.init.ModAttributes;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "createPlayerAttributes", at = @At("RETURN"))
    private static void injectCustomAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder callback = cir.getReturnValue();
        for (ClampedEntityAttribute attribute : ModAttributes.ATTRIBUTES)
            callback.add(attribute);
    }
}
