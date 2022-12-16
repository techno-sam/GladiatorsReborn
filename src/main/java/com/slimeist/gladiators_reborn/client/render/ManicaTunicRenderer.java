package com.slimeist.gladiators_reborn.client.render;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import com.slimeist.gladiators_reborn.client.GladiatorsRebornClient;
import com.slimeist.gladiators_reborn.client.render.model.ManicaTunicModel;
import com.slimeist.gladiators_reborn.client.render.model.PlumeModel;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class ManicaTunicRenderer implements ArmorRenderer {
    /*@Override
    public void render(MatrixStack ms, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ms.push();

        ModelPart head = contextModel.getHead();

        //ms.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        /*head.rotate(ms);
        ms.multiply(Vec3f.NEGATIVE_Y.getRadialQuaternion(head.yaw));
        ms.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(head.pitch));
        ms.translate(0, 1.9, 0);
        boolean isVillager = entity instanceof VillagerEntity || entity instanceof ZombieVillagerEntity;
        float m;
        if (entity.isBaby() && !(entity instanceof VillagerEntity)) {
            m = 2.0f;
            float n = 1.4f;
            ms.translate(0.0, 0.03125, 0.0);
            ms.scale(0.7f, 0.7f, 0.7f);
            ms.translate(0.0, 1.0, 0.0);
        }
        head.rotate(ms);
        ms.scale(-1, -1, 1);
        HeadFeatureRenderer.translate(ms, isVillager);

        PlumeModel plumeModel = new PlumeModel(mc.getEntityModelLoader().getModelPart(GladiatorsRebornClient.GALEA_PLUME_LAYER));
        plumeModel.render(ms, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(GladiatorsReborn.id("textures/plumes/red_plume.png"))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        //VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(new Identifier("models/armor/secutor_layer_1")));
        mc.getItemRenderer().renderItem(new ItemStack(Items.ACACIA_BOAT), ModelTransformation.Mode.GROUND, light, OverlayTexture.DEFAULT_UV, ms, vertexConsumers, 0);
        ms.pop();
    }*/

//    private final HeldItemRenderer heldItemRenderer;

    @Override
//    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, ItemStack itemStack, LivingEntity livingEntity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        float m;
        boolean bl;
        if (itemStack.isEmpty()) {
            return;
        }
        Item item = itemStack.getItem();
        matrices.push();
        float scaleX = 1.0F;
        float scaleY = 1.0F;
        float scaleZ = 1.0F;
        matrices.scale(scaleX, scaleY, scaleZ);
        boolean bl2 = bl = livingEntity instanceof VillagerEntity || livingEntity instanceof ZombieVillagerEntity;
        if (((LivingEntity)livingEntity).isBaby() && !(livingEntity instanceof VillagerEntity)) {
            m = 2.0f;
            float n = 1.4f;
            matrices.translate(0.0, 0.03125, 0.0);
            matrices.scale(0.7f, 0.7f, 0.7f);
            matrices.translate(0.0, 1.0, 0.0);
        }
//        contextModel.getHead().rotate(matrices);
        contextModel.body.rotate(matrices);
        //!(item instanceof ArmorItem) || ((ArmorItem)item).getSlotType() != EquipmentSlot.HEAD) {
        HeadFeatureRenderer.translate(matrices, bl);

        matrices.push();
        matrices.push();
        //MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumerProvider, 0);
        matrices.pop();
//////        matrices.scale(1.5f, 1.5f, 1.5f);
//        matrices.scale(1.0f, -1.0f, -1.0f);
        matrices.translate(-0.5, -0.5, -0.5);
//        PlumeModel manicaTunicModel = new PlumeModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(GladiatorsRebornClient.GALEA_PLUME_LAYER));
//        manicaTunicModel.render(matrices, vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(GladiatorsReborn.id("textures/plumes/red_plume.png"))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        //ItemStack testStack = new ItemStack(Items.CARVED_PUMPKIN);
        //BakedModel testModel = MinecraftClient.getInstance().getItemRenderer().getModel(testStack, livingEntity.world, livingEntity, 0);
        //renderBakedItemModel(testModel, testStack, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumerProvider.getBuffer(RenderLayer.getSolid()));
        ManicaTunicModel manicaTunicModel = new ManicaTunicModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(GladiatorsRebornClient.MANICA_TUNIC_LAYER));
        manicaTunicModel.render(matrices, vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(GladiatorsReborn.id("textures/tunics/red_tunic.png"))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();
        matrices.pop();
    }
    private void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {
        Random random = Random.create();
        long l = 42L;
        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, direction, random), stack, light, overlay);
        }
        random.setSeed(42L);
        this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), stack, light, overlay);
    }

    private void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
        boolean bl = !stack.isEmpty();
        MatrixStack.Entry entry = matrices.peek();
        for (BakedQuad bakedQuad : quads) {
            int i = -1;
            /*if (bl && bakedQuad.hasColor()) {
                i = this.colors.getColor(stack, bakedQuad.getColorIndex());
            }*/
            float f = (float)(i >> 16 & 0xFF) / 255.0f;
            float g = (float)(i >> 8 & 0xFF) / 255.0f;
            float h = (float)(i & 0xFF) / 255.0f;
            vertices.quad(entry, bakedQuad, f, g, h, light, overlay);
        }
    }
}
