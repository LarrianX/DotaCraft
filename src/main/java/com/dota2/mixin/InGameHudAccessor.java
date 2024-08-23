package com.dota2.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.MinecraftClient;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {
    @Accessor("client")
    MinecraftClient getClient();
}
