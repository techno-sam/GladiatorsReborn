package com.slimeist.gladiators_reborn.mixin;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getProtectionAmount", at = @At("RETURN"), cancellable = true)
    private static void reduceTridentDamage(Iterable<ItemStack> equipment, DamageSource source, CallbackInfoReturnable<Integer> cir) {
        if (source.getName().equals("trident")) {
            for (ItemStack stack : equipment) {
                if (stack.getItem() == GladiatorsReborn.GALEA_HELMET) {
                    cir.setReturnValue(cir.getReturnValue() + 4);
                    break;
                }
            }
        }
    }
}
