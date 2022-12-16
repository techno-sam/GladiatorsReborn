package com.slimeist.gladiators_reborn.client.render.model;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import com.slimeist.gladiators_reborn.entities.RetesEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;

//Net draping over player
public class RetesEntityModel extends EntityModel<RetesEntity> {
    private static final String CORE = "core";
    private static final String FLAP_1 = "flap_1";
    private static final String FLAP_2 = "flap_2";
    private static final String FLAP_3 = "flap_3";
    private static final String FLAP_4 = "flap_4";

    private final ModelPart root;
    private final ModelPart core;
    private final ModelPart flap_1;
    private final ModelPart flap_2;
    private final ModelPart flap_3;
    private final ModelPart flap_4;
    public RetesEntityModel(ModelPart root) {
        super(RenderLayer::getArmorCutoutNoCull);
        this.root = root;
        this.core = root.getChild(CORE);
        this.flap_1 = root.getChild(FLAP_1);
        this.flap_2 = root.getChild(FLAP_2);
        this.flap_3 = root.getChild(FLAP_3);
        this.flap_4 = root.getChild(FLAP_4);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(CORE, ModelPartBuilder.create().uv(0, 0)
            .cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 32.0F, 8.0F,
                new Dilation(0.0F)),
            ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        //basic size 0 cube for each flap for now
        ModelPartData flap_1 = modelPartData.addChild(FLAP_1, ModelPartBuilder.create().uv(32, 0)
                .cuboid(-6.0F, -32.0F, 0.0F, 12.0F, 32.0F, 0.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 32.0F, 4.0F, -0.5F, 0.0F, 0.0F));

        flap_1.addChild("flap_1_weight_1", ModelPartBuilder.create().uv(32, 32)
                .cuboid(4.5F, -33.0F, -1.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        flap_1.addChild("flap_1_weight_2", ModelPartBuilder.create().uv(32, 32)
                .cuboid(-7.5F, -33.0F, -1.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData flap_2 = modelPartData.addChild(FLAP_2, ModelPartBuilder.create().uv(32, 0)
                .cuboid(-6.0F, -32.0F, 0.0F, 12.0F, 32.0F, 0.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 32.0F, -4.0F, 0.5F, 0.0F, 0.0F));

        flap_2.addChild("flap_2_weight_1", ModelPartBuilder.create().uv(32, 32)
                .cuboid(4.5F, -33.0F, -1.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        flap_2.addChild("flap_2_weight_2", ModelPartBuilder.create().uv(32, 32)
                .cuboid(-7.5F, -33.0F, -1.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData flap_3 = modelPartData.addChild(FLAP_3, ModelPartBuilder.create().uv(32, -12)
                .cuboid(0.0F, -32.0F, -6.0F, 0.0F, 32.0F, 12.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(-4.0F, 32.0F, 0.0F, 0, 0.0F, -0.5F));

        flap_3.addChild("flap_3_weight_1", ModelPartBuilder.create().uv(32, 32)
                .cuboid(-1.5F, -33.0F, 4.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        flap_3.addChild("flap_3_weight_2", ModelPartBuilder.create().uv(32, 32)
                .cuboid(-1.5F, -33.0F, -7.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData flap_4 = modelPartData.addChild(FLAP_4, ModelPartBuilder.create().uv(32, -12)
                .cuboid(0.0F, -32.0F, -6.0F, 0.0F, 32.0F, 12.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(4.0F, 32.0F, 0.0F, 0, 0.0F, 0.5F));

        flap_4.addChild("flap_4_weight_1", ModelPartBuilder.create().uv(32, 32)
                .cuboid(-1.5F, -33.0F, 4.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        flap_4.addChild("flap_4_weight_2", ModelPartBuilder.create().uv(32, 32)
                .cuboid(-1.5F, -33.0F, -7.5F, 3.0F, 3.0F, 3.0F,
                    new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(RetesEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isAlive()) {
            this.flap_1.pitch = (float) Math.PI / 2;
            this.flap_2.pitch = (float) -Math.PI / 2;

            this.flap_3.roll = (float) Math.PI / 2;
            this.flap_4.roll = (float) -Math.PI / 2;
            this.core.hidden = true;
            this.root.pivotY = -32;
        } else {
            this.core.hidden = false;
            this.root.pivotY = 0;
            this.flap_1.pitch = (float) ((Math.sin((entity.age + entity.hashCode() + animationProgress) * 0.07) + 1) * 0.2 - 0.5);
            this.flap_2.pitch = (float) -((Math.sin((entity.age + entity.hashCode() + animationProgress + 0.1f) * 0.06) + 1) * 0.2 - 0.5);

            this.flap_3.roll = (float) ((Math.sin((entity.age + entity.hashCode() + animationProgress + 0.3) * 0.075) + 1) * 0.2 - 0.5);
            this.flap_4.roll = (float) -((Math.sin((entity.age + entity.hashCode() + animationProgress + 0.2f) * 0.055) + 1) * 0.2 - 0.5);
        }
    }
}
