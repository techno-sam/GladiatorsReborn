package com.slimeist.gladiators_reborn.mixin;

import com.slimeist.gladiators_reborn.gladiator_types.GladiatorType;
import com.slimeist.gladiators_reborn.mixin_interfaces.PlayerGladiatorData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerGladiatorData, PlayerGladiatorData.Transfer {

    protected GladiatorType gladiatorType = GladiatorType.NONE;
    protected NbtList normalInventory = null;

    @Override
    public GladiatorType getGladiatorType() {
        return gladiatorType;
    }

    @Override
    public void setGladiatorType(GladiatorType gladiatorType) {
        if (((PlayerEntity) (Object) this).world.isClient)
            return;
        if (gladiatorType != this.gladiatorType) {
            if (this.gladiatorType == GladiatorType.NONE) {
                this.normalInventory = ((PlayerEntity) (Object) this).getInventory().writeNbt(new NbtList());
            } else if (gladiatorType == GladiatorType.NONE) {
                ((PlayerEntity) (Object) this).getInventory().readNbt(this.normalInventory);
                this.normalInventory = null;
            }
            this.gladiatorType = gladiatorType;
            if (this.gladiatorType != GladiatorType.NONE) {
                ((PlayerEntity) (Object) this).getInventory().clear();
                ((PlayerEntity) (Object) this).getInventory().selectedSlot = 0;
                if ((Object) this instanceof ServerPlayerEntity serverPlayer)
                    serverPlayer.networkHandler.sendPacket(new UpdateSelectedSlotS2CPacket(0));
                this.gladiatorType.applyLoadout((PlayerEntity) (Object) this);
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeGladiatorData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("GladiatorType", gladiatorType.getId().toString());
        if (this.normalInventory != null)
            nbt.put("NormalInventory", this.normalInventory);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readGladiatorData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("GladiatorType", NbtElement.STRING_TYPE))
            gladiatorType = GladiatorType.get(Identifier.tryParse(nbt.getString("GladiatorType"))).orElse(GladiatorType.NONE);
        else
            gladiatorType = GladiatorType.NONE;
        if (nbt.contains("NormalInventory"))
            this.normalInventory = nbt.getList("NormalInventory", NbtElement.COMPOUND_TYPE);
        else
            this.normalInventory = null;
    }

    @Override
    public GladiatorType getGladiatorTypeRaw() {
        return this.gladiatorType;
    }

    @Override
    public void setGladiatorTypeRaw(GladiatorType gladiatorType) {
        this.gladiatorType = gladiatorType;
    }

    @Override
    public NbtList getNormalInventoryRaw() {
        return this.normalInventory;
    }

    @Override
    public void setNormalInventoryRaw(NbtList normalInventory) {
        this.normalInventory = normalInventory;
    }

    @Override
    public void transferGladiatorData(PlayerEntity newPlayer) {
        ((PlayerGladiatorData.Transfer) newPlayer).setGladiatorTypeRaw(this.gladiatorType);
        ((PlayerGladiatorData.Transfer) newPlayer).setNormalInventoryRaw(this.normalInventory);
    }
}
