package com.slimeist.gladiators_reborn.mixin.client;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.DyeableItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public class ItemColorsMixin {
    @Inject(method = "create", at = @At("RETURN"))
    private static void addCustomColors(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir) {
        cir.getReturnValue().register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack),
            GladiatorsReborn.GALEA_HELMET, GladiatorsReborn.MANICA_CHESTPLATE, GladiatorsReborn.GREAVES_LEGGINGS, GladiatorsReborn.MANICA_WITH_TUNIC);
    }
}
