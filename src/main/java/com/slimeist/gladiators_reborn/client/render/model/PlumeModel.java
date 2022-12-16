package com.slimeist.gladiators_reborn.client.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class PlumeModel extends Model {
    private static final String FRONT_SPIKE = "front_spike";
    private static final String BACK_SPIKE = "back_spike";
    private static final String CENTER_PLATE = "center_plate";

    private final ModelPart root;
    private final ModelPart front_spike;
    private final ModelPart back_spike;
    private final ModelPart center_plate;

    public PlumeModel(ModelPart root) {
        super(RenderLayer::getArmorCutoutNoCull);
        this.root = root;
        this.front_spike = root.getChild(FRONT_SPIKE);
        this.back_spike = root.getChild(BACK_SPIKE);
        this.center_plate = root.getChild(CENTER_PLATE);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

/*        modelPartData.addChild(CENTER_SPIKE,
            ModelPartBuilder.create().uv(0, 0)
                .cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -16.0F, -8.0F, -0.5672F, 0.0F, 0.0F));*/
        modelPartData.addChild(FRONT_SPIKE,
            ModelPartBuilder.create().uv(0, 0)
                .cuboid(7.0F, 0.0F, 0.0F, 2.0F, 13.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 13.5F, 0.0F, -0.5672F, 0.0F, 0.0F));

        modelPartData.addChild(BACK_SPIKE,
            ModelPartBuilder.create().uv(0, 0)
                .cuboid(7.0F, 0.0F, 0.0F, 2.0F, 13.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 13.5F, 10.0F, 0.5672F, 0.0F, 0.0F));

        modelPartData.addChild(CENTER_PLATE,
            ModelPartBuilder.create().uv(0, 0)
                .cuboid(8.0F, 16.0F, -5.0F, 0.0F, 16.0F, 24.0F, new Dilation(0.0F), 1F, 1F),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
//        modelPartData.addChild(CENTER_SPIKE, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
