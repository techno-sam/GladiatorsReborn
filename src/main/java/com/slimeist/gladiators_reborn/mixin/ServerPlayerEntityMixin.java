package com.slimeist.gladiators_reborn.mixin;

import com.slimeist.gladiators_reborn.mixin_interfaces.PlayerGladiatorData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyGladiatorData(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ((PlayerGladiatorData.Transfer) oldPlayer).transferGladiatorData((PlayerEntity) (Object) this);
    }
}
