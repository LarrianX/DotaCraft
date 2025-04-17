package com.larrian.dotacraft.client;

import com.larrian.dotacraft.client.renderer.MeatHookRenderer;
import com.larrian.dotacraft.entity.model.MeatHookEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static com.larrian.dotacraft.ModEntities.MEAT_HOOK;

public class ModRenderers {
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier("entitytesting", "cube"), "main");

    public static void registerModRenderers() {
        EntityRendererRegistry.register(MEAT_HOOK, MeatHookRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, MeatHookEntityModel::getTexturedModelData);
    }
}
