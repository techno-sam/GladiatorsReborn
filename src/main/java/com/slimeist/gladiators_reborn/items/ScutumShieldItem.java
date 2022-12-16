package com.slimeist.gladiators_reborn.items;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;

import java.util.UUID;

public class ScutumShieldItem extends FabricShieldItem {
    private static final UUID MODIFIER_UUID = UUID.fromString("3F6599E2-2BA4-423F-B3F9-CE4ECC23BAAF");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    public ScutumShieldItem(Settings settings, int cooldownTicks, int enchantability, Item... repairItems) {
        super(settings, cooldownTicks, enchantability, repairItems);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(MODIFIER_UUID, "Shield modifier", 0.05, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.OFFHAND) {
            return attributeModifiers;
        } else {
            return super.getAttributeModifiers(slot);
        }
    }


}
