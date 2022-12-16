package com.slimeist.gladiators_reborn.client;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import com.slimeist.gladiators_reborn.client.render.AdditiveArmorRendererRegistryImpl;
import com.slimeist.gladiators_reborn.client.render.GaleaHelmetRenderer;
import com.slimeist.gladiators_reborn.client.render.ManicaTunicRenderer;
import com.slimeist.gladiators_reborn.client.render.entity.RetesEntityRenderer;
import com.slimeist.gladiators_reborn.client.render.entity.TridensEntityRenderer;
import com.slimeist.gladiators_reborn.client.render.model.ManicaTunicModel;
import com.slimeist.gladiators_reborn.client.render.model.PlumeModel;
import com.slimeist.gladiators_reborn.client.render.model.RetesEntityModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class GladiatorsRebornClient implements ClientModInitializer {
  public static final EntityModelLayer GALEA_PLUME_LAYER = new EntityModelLayer(GladiatorsReborn.id("galea_plume"), "main");
  public static final EntityModelLayer MANICA_TUNIC_LAYER = new EntityModelLayer(GladiatorsReborn.id("manica_tunic"), "main");
  public static final EntityModelLayer RETES_LAYER = new EntityModelLayer(GladiatorsReborn.id("retes"), "main");
  @Override
  public void onInitializeClient() {
    AdditiveArmorRendererRegistryImpl.register(new GaleaHelmetRenderer(), GladiatorsReborn.GALEA_HELMET);
    AdditiveArmorRendererRegistryImpl.register(new ManicaTunicRenderer(), GladiatorsReborn.MANICA_WITH_TUNIC);
    EntityModelLayerRegistry.registerModelLayer(GALEA_PLUME_LAYER, PlumeModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(MANICA_TUNIC_LAYER, ManicaTunicModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(RETES_LAYER, RetesEntityModel::getTexturedModelData);
    EntityRendererRegistry.register(GladiatorsReborn.TRIDENS_ENTITY_TYPE, TridensEntityRenderer::new);
    EntityRendererRegistry.register(GladiatorsReborn.RETES_ENTITY_TYPE, RetesEntityRenderer::new);
    EntityRendererRegistry.register(GladiatorsReborn.THROWN_RETES_ENTITY_TYPE, FlyingItemEntityRenderer::new);
    ModelPredicateProviderRegistry.register(GladiatorsReborn.TRIDENS, new Identifier("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    /*ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
      registry.register(GladiatorsReborn.id("plumes/red_plume"));
    });*/
  }
}
