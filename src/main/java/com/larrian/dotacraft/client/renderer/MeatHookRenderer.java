package com.larrian.dotacraft.client.renderer;

import com.larrian.dotacraft.entity.MeatHookEntity;
import com.larrian.dotacraft.entity.model.MeatHookEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;

import static com.larrian.dotacraft.client.ModRenderers.MODEL_CUBE_LAYER;

public class MeatHookRenderer extends LivingEntityRenderer<MeatHookEntity, MeatHookEntityModel> {

    public MeatHookRenderer(EntityRendererFactory.Context context) {
        super(context, new MeatHookEntityModel(context.getPart(MODEL_CUBE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(MeatHookEntity entity) {
        return new Identifier("entitytesting", "textures/entity/meat_hook.png");
    }
}