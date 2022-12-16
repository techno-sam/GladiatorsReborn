/*package com.slimeist.gladiators_reborn.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public class TempMinecraftClientMixin {
    @Inject(method = "reloadResourcesConcurrently", at = @At("HEAD"), cancellable = true)
    private void reloadResourcesConcurrently(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.setReturnValue(CompletableFuture.runAsync(() -> {}));
    }
}
*/