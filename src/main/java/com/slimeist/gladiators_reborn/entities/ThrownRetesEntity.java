package com.slimeist.gladiators_reborn.entities;

import com.slimeist.gladiators_reborn.GladiatorsReborn;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThrownRetesEntity extends ThrownItemEntity {
    public ThrownRetesEntity(EntityType<? extends ThrownRetesEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownRetesEntity(World world, LivingEntity owner) {
        super(GladiatorsReborn.THROWN_RETES_ENTITY_TYPE, owner, world);
    }

    public ThrownRetesEntity(World world, double x, double y, double z) {
        super(GladiatorsReborn.THROWN_RETES_ENTITY_TYPE, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return GladiatorsReborn.RETES;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        if (itemStack.isEmpty())
            itemStack = new ItemStack(this.getDefaultItem());
        return new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        LivingEntity livingOwner = null;
        if (this.getOwner() instanceof LivingEntity) {
            livingOwner = (LivingEntity) this.getOwner();
        }
        if (!this.world.isClient) {
            RetesEntity retesEntity = new RetesEntity(this.world, livingOwner, this.getItem().copy());
            //retesEntity.refreshPositionAndAngles(this.getPos().x, this.getPos().y, this.getPos().z, 0.0F, 0.0F);
            if (retesEntity.isTrappable(entity)) {
                retesEntity.startRiding(entity, true);
                this.world.spawnEntity(retesEntity);
                this.setItem(ItemStack.EMPTY);
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            if (!this.getItem().damage(1, this.random, null)) {
                ItemEntity itemEntity = this.dropStack(this.getItem());
                if (itemEntity != null && this.getOwner() != null)
                    itemEntity.setOwner(this.getOwner().getUuid());
            }
            this.discard();
        }
    }
}
