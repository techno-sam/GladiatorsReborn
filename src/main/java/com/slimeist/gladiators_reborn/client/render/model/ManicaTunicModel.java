package com.slimeist.gladiators_reborn.client.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class ManicaTunicModel extends Model {
    private static final String BODY = "body";
    private static final String LEFT_SHOULDER = "left_shoulder";

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart left_shoulder;

    public ManicaTunicModel(ModelPart root) {
        super(RenderLayer::getArmorCutoutNoCull);
        this.root = root;
        this.body = root.getChild(BODY);
        this.left_shoulder = root.getChild(LEFT_SHOULDER);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

/*        modelPartData.addChild(CENTER_SPIKE,
            ModelPartBuilder.create().uv(0, 0)
                .cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -16.0F, -8.0F, -0.5672F, 0.0F, 0.0F));*/
        modelPartData.addChild(BODY,
            ModelPartBuilder.create().uv(0, 0)
                .cuboid(1.0F, -24.0F, 4.0F, 14.0F, 24.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        modelPartData.addChild(LEFT_SHOULDER,
            ModelPartBuilder.create().uv(0, 32)
                .cuboid(-5.0F, -5.5F, 4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.01F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
