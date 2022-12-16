package com.slimeist.gladiators_reborn.util;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class Registration {
    public static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registry.ITEM, GladiatorsReborn.id(name), item);
    }

    public static <T extends Entity> EntityType<T> registerEntityType(String name, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, GladiatorsReborn.id(name), entityType);
    }
}
