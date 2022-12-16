package com.slimeist.gladiators_reborn.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class AdditiveArmorRendererRegistryImpl {
    private static final HashMap<Item, ArmorRenderer> RENDERERS = new HashMap<>();

    public static void register(ArmorRenderer renderer, ItemConvertible... items) {
        Objects.requireNonNull(renderer, "renderer is null");

        if (items.length == 0) {
            throw new IllegalArgumentException("Armor renderer registered for no item");
        }

        for (ItemConvertible item : items) {
            Objects.requireNonNull(item.asItem(), "armor item is null");

            if (RENDERERS.putIfAbsent(item.asItem(), renderer) != null) {
                throw new IllegalArgumentException("Custom armor renderer already exists for " + Registry.ITEM.getId(item.asItem()));
            }
        }
    }

    @Nullable
    public static ArmorRenderer get(Item item) {
        return RENDERERS.get(item);
    }
}
