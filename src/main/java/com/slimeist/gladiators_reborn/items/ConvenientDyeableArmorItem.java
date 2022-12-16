package com.slimeist.gladiators_reborn.items;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import com.slimeist.gladiators_reborn.gladiator_types.GladiatorType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

import java.util.Arrays;
import java.util.List;

public class ConvenientDyeableArmorItem extends DyeableArmorItem {
    public ConvenientDyeableArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Settings settings) {
        super(armorMaterial, equipmentSlot, settings);
    }

    public ItemStack makeStack(int color) {
        ItemStack stack = new ItemStack(this);
        this.setColor(stack, color);
        return stack;
    }

    public ItemStack makeStack(Item... dyes) {
        return makeStack(Arrays.stream(dyes).filter(item -> item instanceof DyeItem).map(item -> (DyeItem) item).toList().toArray(new DyeItem[0]));
    }

    public ItemStack makeStack(DyeItem... dyes) {
        return DyeableItem.blendAndSetColor(new ItemStack(this), List.of(dyes));
    }
}
