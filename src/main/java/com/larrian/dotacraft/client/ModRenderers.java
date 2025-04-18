package com.larrian.dotacraft.client;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.ModEntities;
import com.larrian.dotacraft.client.renderer.MeatHookRenderer;
import com.larrian.dotacraft.entity.MeatHookEntity;
import com.larrian.dotacraft.entity.model.MeatHookEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModRenderers {
    public static final EntityModelLayer MEAT_HOOK = new EntityModelLayer(new Identifier(DotaCraft.MOD_ID, MeatHookEntity.ID), "main");

    public static void registerModRenderers() {
        EntityRendererRegistry.register(ModEntities.MEAT_HOOK, MeatHookRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MEAT_HOOK, MeatHookEntityModel::getTexturedModelData);


    }
}
