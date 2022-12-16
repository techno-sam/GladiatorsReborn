package com.slimeist.gladiators_reborn.entities;

import com.google.common.collect.ImmutableList;
import com.slimeist.gladiators_reborn.GladiatorsReborn;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class RetesEntity extends LivingEntity {
    protected int cooldownTicks = 0;
    protected int escapeCounter = 0;
    protected boolean hasTrapped = false;
    protected LivingEntity owner = null;
    protected ItemStack retesStack = new ItemStack(GladiatorsReborn.RETES);

    public RetesEntity(EntityType<? extends RetesEntity> entityType, World world) {
        super(entityType, world);
    }

    public RetesEntity(World world, double x, double y, double z) {
        this(GladiatorsReborn.RETES_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
    }

    public RetesEntity(World world, LivingEntity owner, ItemStack stack) {
        super(GladiatorsReborn.RETES_ENTITY_TYPE, world);
        this.owner = owner;
        this.retesStack = stack.copy();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getName().equals("player") && source.getAttacker() == this.getVehicle()) {
            this.escapeCounter++;
            if (this.escapeCounter > 6) {
                this.dismountVehicle();
                this.setVelocity(0.0D, 1.0D, 0.0D);
                this.cooldownTicks = 60;
                this.hasTrapped = true;
            }
            return false;
        } else if (source.getName().equals("player") && source.getAttacker() != null && source.getAttacker().isSneaking() && (this.getVehicle() == null || !this.getVehicle().isAlive())) {
            this.dismountVehicle();
            this.setVelocity(0.0D, 1.0D, 0.0D);
            this.hasTrapped = true;
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource == DamageSource.FALL || super.isInvulnerableTo(damageSource);
    }

    public boolean isTrappable(Entity entity) {
        return entity instanceof LivingEntity && !(entity instanceof RetesEntity) &&
            EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity) &&
            entity.isAlive() && entity != this.owner;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.age % 10 == 0 && this.escapeCounter > 0) {
            this.escapeCounter--;
        }
        if (this.age >= 400 && this.hasVehicle()) {
            this.dismountVehicle();
            this.hasTrapped = true;
            this.setVelocity(0.0D, 1.0D, 0.0D);
            this.cooldownTicks = 6000;
        }
        if (this.cooldownTicks > 0)
            this.cooldownTicks--;
        if (this.owner != null && (!this.owner.isAlive() || this.owner.isRemoved())) {
            this.owner = null;
        }
        if (this.isAlive() && this.hasTrapped && this.onGround) {
            this.kill();
        }
        if (this.getVehicle() != null && this.getVehicle().isAlive() && this.getVehicle() instanceof LivingEntity livingVehicle) {
            livingVehicle.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 4), this);
            livingVehicle.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 0), this);
        }
        if (this.isAlive() && !this.hasVehicle() && this.cooldownTicks == 0) {
            Box box = this.getBoundingBox().expand(0.2D, 0.0D, 0.2D);
            List<Entity> trappablePlayers = this.world.getOtherEntities(this, box, this::isTrappable);
            if (!trappablePlayers.isEmpty()) {
                Entity target = trappablePlayers.get(this.random.nextInt(trappablePlayers.size()));
                this.startRiding(target, true);
            }
        }
    }

    @Override
    public double getHeightOffset() {
        if (this.getVehicle() != null) {
            return -this.getVehicle().getMountedHeightOffset();
        }
        return 0.0D;
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);
        if (!this.retesStack.damage(1, this.random, null)) {
            ItemEntity itemEntity = this.dropStack(this.retesStack);
            if (this.owner != null && itemEntity != null) {
                itemEntity.setOwner(this.owner.getUuid());
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("CooldownTicks", this.cooldownTicks);
        nbt.putBoolean("HasTrapped", this.hasTrapped);
        if (this.owner != null) {
            nbt.putUuid("Owner", this.owner.getUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("CooldownTicks"))
            this.cooldownTicks = nbt.getInt("CooldownTicks");
        else
            this.cooldownTicks = 0;
        if (nbt.contains("HasTrapped"))
            this.hasTrapped = nbt.getBoolean("HasTrapped");
        else
            this.hasTrapped = false;
        if (nbt.containsUuid("Owner")) {
            UUID ownerUUID = nbt.getUuid("Owner");
            this.owner = this.world.getClosestEntity(LivingEntity.class, TargetPredicate.createAttackable().setPredicate(entity -> entity.getUuid().equals(ownerUUID)), this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().expand(100.0D, 100.0D, 100.0D));
        }
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public static DefaultAttributeContainer.Builder createRetesAttributes() {
        return DefaultAttributeContainer.builder()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0D)
            .add(EntityAttributes.GENERIC_ARMOR, 0.0D)
            .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 0.0D)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
            .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D)
            .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.0D);
    }
}
