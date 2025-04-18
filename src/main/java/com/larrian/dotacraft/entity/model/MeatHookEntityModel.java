package com.larrian.dotacraft.entity.model;

import com.larrian.dotacraft.entity.MeatHookEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

public class MeatHookEntityModel extends EntityModel<MeatHookEntity> {
    private final ModelPart bb_main;

    public MeatHookEntityModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(1.7627F, 2.9F, -0.5F, 0.5F, 0.25F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.2626F, 3.0F, -0.5F, 2.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(3.0126F, 3.5F, -0.5F, 1.25F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(4.2626F, 2.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(4.2626F, 3.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.2626F, 2.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.2626F, 2.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.7626F, 1.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.7626F, 1.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(6.2626F, 0.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(6.2626F, 0.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(6.2626F, -0.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(6.2626F, -1.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.7626F, -1.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.7626F, -2.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.2626F, -2.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(1.2627F, -3.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(4.2626F, -3.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.2626F, -3.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(3.2626F, -3.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(4.2626F, -3.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.2626F, -4.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.2626F, -3.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(1.2627F, -3.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.2626F, -2.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.2626F, -3.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.2374F, -2.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.2374F, -1.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.7373F, -1.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.7373F, -0.5F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.9873F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.7373F, 0.0F, -0.6F, 0.75F, 1.0F, 0.1F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.7373F, 0.0F, 0.5F, 0.75F, 1.0F, 0.1F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.7373F, -0.1F, -0.5F, 0.75F, 0.1F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.7373F, 1.0F, -0.5F, 0.75F, 0.1F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.7373F, 0.0F, -0.5F, 0.75F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.7374F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.0126F, -4.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(3.2626F, -4.0F, -0.5F, 1.0F, 0.5F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.0126F, -4.0F, 0.0F, -1.5708F, -1.5272F, 1.5708F));

        ModelPartData cube_r3 = bb_main.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-5.4873F, -3.5266F, -0.5F, 0.9F, 0.25F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.4837F, -4.8801F, -0.5F, 0.25F, 1.35F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.2337F, -4.8801F, -0.5F, 1.0F, 0.25F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-4.2337F, -4.8801F, -0.5F, 0.25F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.0126F, -4.0F, 0.0F, 1.5708F, -0.7854F, -1.5708F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(MeatHookEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
