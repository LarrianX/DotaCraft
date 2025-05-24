package com.larrian.dotacraft.client.renderer.custom;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.client.renderer.ModRenderers;
import com.larrian.dotacraft.entity.custom.MeatHookEntity;
import com.larrian.dotacraft.entity.model.MeatHookEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class MeatHookRenderer extends EntityRenderer<MeatHookEntity> {
    private final MeatHookEntityModel model;
    private static final Identifier TEXTURE = new Identifier(DotaCraft.MOD_ID, "textures/entity/" + MeatHookEntity.ID + ".png");

    public MeatHookRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new MeatHookEntityModel(context.getPart(ModRenderers.MEAT_HOOK));
    }

    @Override
    public void render(MeatHookEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        float interpolatedYaw = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());
        float interpolatedPitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(interpolatedYaw));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(interpolatedPitch));

        float shake = entity.shake - tickDelta;
        if (shake > 0.0F) {
            float shakeAngle = -MathHelper.sin(shake * 3.0F) * shake;
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(shakeAngle));
        }

        matrices.translate(0.0, 1.5, 0.0);
        matrices.scale(-1.0F, -1.0F, 1.0F);

        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(TEXTURE)), light,
                net.minecraft.client.render.OverlayTexture.DEFAULT_UV,
                1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }


    @Override
    public Identifier getTexture(MeatHookEntity entity) {
        return TEXTURE;
    }
}