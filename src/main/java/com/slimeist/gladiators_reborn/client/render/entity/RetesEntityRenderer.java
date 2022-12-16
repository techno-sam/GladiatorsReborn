package com.slimeist.gladiators_reborn.client.render.entity;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import com.slimeist.gladiators_reborn.client.GladiatorsRebornClient;
import com.slimeist.gladiators_reborn.client.render.model.RetesEntityModel;
import com.slimeist.gladiators_reborn.entities.RetesEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class RetesEntityRenderer extends EntityRenderer<RetesEntity> {
    public static final Identifier TEXTURE = GladiatorsReborn.id("textures/entity/retes.png");
    private final RetesEntityModel model;

    public RetesEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new RetesEntityModel(context.getPart(GladiatorsRebornClient.RETES_LAYER));
    }

    @Override
    public void render(RetesEntity retesEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, retesEntity.prevYaw, retesEntity.getYaw())));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, retesEntity.prevPitch, retesEntity.getPitch())));
        this.model.setAngles(retesEntity, 0.0F, 0.0F, tickDelta, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(getTexture(retesEntity)));
        this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(retesEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    public Identifier getTexture(RetesEntity retesEntity) {
        return TEXTURE;
    }
}
