package com.slimeist.gladiators_reborn.mixin_interfaces;

import com.slimeist.gladiators_reborn.gladiator_types.GladiatorType;
import com.slimeist.gladiators_reborn.mixin.ServerPlayerEntityMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtList;

public interface PlayerGladiatorData {
    GladiatorType getGladiatorType();
    void setGladiatorType(GladiatorType gladiatorType);

    public interface Transfer {
        GladiatorType getGladiatorTypeRaw();
        void setGladiatorTypeRaw(GladiatorType gladiatorType);
        NbtList getNormalInventoryRaw();
        void setNormalInventoryRaw(NbtList normalInventory);
        void transferGladiatorData(PlayerEntity newPlayer);
    }
}
