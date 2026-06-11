package net.minecraft.world.entity.monster.skeleton;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/monster/skeleton/Skeleton.class */
public class Skeleton extends AbstractSkeleton {
    private static final int TOTAL_CONVERSION_TIME = 300;
    private static final EntityDataAccessor<Boolean> DATA_STRAY_CONVERSION_ID = SynchedEntityData.defineId(Skeleton.class, EntityDataSerializers.BOOLEAN);
    public static final String CONVERSION_TAG = "StrayConversionTime";
    private static final int NOT_CONVERTING = -1;
    private int inPowderSnowTime;
    private int conversionTime;

    public Skeleton(EntityType<? extends Skeleton> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void defineSynchedData(SynchedEntityData.Builder $$0) {
        super.defineSynchedData($$0);
        $$0.define(DATA_STRAY_CONVERSION_ID, false);
    }

    public boolean isFreezeConverting() {
        return ((Boolean) getEntityData().get(DATA_STRAY_CONVERSION_ID)).booleanValue();
    }

    public void setFreezeConverting(boolean $$0) {
        this.entityData.set(DATA_STRAY_CONVERSION_ID, Boolean.valueOf($$0));
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton
    public boolean isShaking() {
        return isFreezeConverting();
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void tick() {
        if (!level().isClientSide() && isAlive() && !isNoAi()) {
            if (this.isInPowderSnow) {
                if (isFreezeConverting()) {
                    this.conversionTime--;
                    if (this.conversionTime < 0) {
                        doFreezeConversion();
                    }
                } else {
                    this.inPowderSnowTime++;
                    if (this.inPowderSnowTime >= 140) {
                        startFreezeConversion(300);
                    }
                }
            } else {
                this.inPowderSnowTime = -1;
                setFreezeConverting(false);
            }
        }
        super.tick();
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putInt(CONVERSION_TAG, isFreezeConverting() ? this.conversionTime : -1);
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        super.readAdditionalSaveData($$0);
        int $$1 = $$0.getIntOr(CONVERSION_TAG, -1);
        if ($$1 != -1) {
            startFreezeConversion($$1);
        } else {
            setFreezeConverting(false);
        }
    }

    @VisibleForTesting
    public void startFreezeConversion(int $$0) {
        this.conversionTime = $$0;
        setFreezeConverting(true);
    }

    protected void doFreezeConversion() {
        convertTo(EntityType.STRAY, ConversionParams.single(this, true, true), $$0 -> {
            if (!isSilent()) {
                level().levelEvent(null, LevelEvent.SOUND_SKELETON_TO_STRAY, blockPosition(), 0);
            }
        });
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public boolean canFreeze() {
        return false;
    }

    @Override // net.minecraft.world.entity.Mob
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton
    SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }
}
