package net.minecraft.world.entity.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/monster/Zoglin.class */
public class Zoglin extends Monster implements HoglinBase {
    private static final int MAX_HEALTH = 40;
    private static final int ATTACK_KNOCKBACK = 1;
    private static final float KNOCKBACK_RESISTANCE = 0.6f;
    private static final int ATTACK_DAMAGE = 6;
    private static final float BABY_ATTACK_DAMAGE = 0.5f;
    private static final int ATTACK_INTERVAL = 40;
    private static final int BABY_ATTACK_INTERVAL = 15;
    private static final int ATTACK_DURATION = 200;
    private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.3f;
    private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.4f;
    private static final boolean DEFAULT_BABY = false;
    private int attackAnimationRemainingTicks;
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Zoglin.class, EntityDataSerializers.BOOLEAN);
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Zoglin>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS);
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN);

    public Zoglin(EntityType<? extends Zoglin> $$0, Level $$1) {
        super($$0, $$1);
        this.xpReward = 5;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected Brain.Provider<Zoglin> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected Brain<?> makeBrain(Dynamic<?> $$0) {
        Brain brainMakeBrain = brainProvider().makeBrain($$0);
        initCoreActivity(brainMakeBrain);
        initIdleActivity(brainMakeBrain);
        initFightActivity(brainMakeBrain);
        brainMakeBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brainMakeBrain.setDefaultActivity(Activity.IDLE);
        brainMakeBrain.useDefaultActivity();
        return brainMakeBrain;
    }

    private static void initCoreActivity(Brain<Zoglin> $$0) {
        $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    private static void initIdleActivity(Brain<Zoglin> $$0) {
        $$0.addActivity(Activity.IDLE, 10, ImmutableList.of(StartAttacking.create(($$02, $$1) -> {
            return $$1.findNearestValidAttackTarget($$02);
        }), SetEntityLookTargetSometimes.create(8.0f, UniformInt.of(30, 60)), new RunOne(ImmutableList.of(Pair.of(RandomStroll.stroll(0.4f), 2), Pair.of(SetWalkTargetFromLookTarget.create(0.4f, 3), 2), Pair.of(new DoNothing(30, 60), 1)))));
    }

    private static void initFightActivity(Brain<Zoglin> $$0) {
        $$0.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0f), BehaviorBuilder.triggerIf((v0) -> {
            return v0.isAdult();
        }, MeleeAttack.create(40)), BehaviorBuilder.triggerIf((v0) -> {
            return v0.isBaby();
        }, MeleeAttack.create(15)), StopAttackingIfTargetInvalid.create()), MemoryModuleType.ATTACK_TARGET);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel $$0) {
        return ((NearestVisibleLivingEntities) getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty())).findClosest($$1 -> {
            return isTargetable($$0, $$1);
        });
    }

    private boolean isTargetable(ServerLevel $$0, LivingEntity $$1) {
        EntityType<?> $$2 = $$1.getType();
        return ($$2 == EntityType.ZOGLIN || $$2 == EntityType.CREEPER || !Sensor.isEntityAttackable($$0, this, $$1)) ? false : true;
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void defineSynchedData(SynchedEntityData.Builder $$0) {
        super.defineSynchedData($$0);
        $$0.define(DATA_BABY_ID, false);
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity, net.minecraft.network.syncher.SyncedDataHolder
    public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
        super.onSyncedDataUpdated($$0);
        if (DATA_BABY_ID.equals($$0)) {
            refreshDimensions();
        }
    }

    @Override // net.minecraft.world.entity.Mob
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, EntitySpawnReason $$2, SpawnGroupData $$3) {
        if ($$0.getRandom().nextFloat() < 0.2f) {
            setBaby(true);
        }
        return super.finalizeSpawn($$0, $$1, $$2, $$3);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0d).add(Attributes.MOVEMENT_SPEED, 0.30000001192092896d).add(Attributes.KNOCKBACK_RESISTANCE, 0.6000000238418579d).add(Attributes.ATTACK_KNOCKBACK, 1.0d).add(Attributes.ATTACK_DAMAGE, 6.0d);
    }

    public boolean isAdult() {
        return !isBaby();
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity
    public boolean doHurtTarget(ServerLevel $$0, Entity $$1) {
        if (!($$1 instanceof LivingEntity)) {
            return false;
        }
        LivingEntity $$3 = (LivingEntity) $$1;
        this.attackAnimationRemainingTicks = 10;
        $$0.broadcastEntityEvent(this, (byte) 4);
        makeSound(SoundEvents.ZOGLIN_ATTACK);
        return HoglinBase.hurtAndThrowTarget($$0, this, $$3);
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.Leashable
    public boolean canBeLeashed() {
        return true;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected void blockedByItem(LivingEntity $$0) {
        if (!isBaby()) {
            HoglinBase.throwTarget(this, $$0);
        }
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public boolean hurtServer(ServerLevel $$0, DamageSource $$1, float $$2) {
        boolean $$3 = super.hurtServer($$0, $$1, $$2);
        if ($$3) {
            Entity entity = $$1.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity $$5 = (LivingEntity) entity;
                if (canAttack($$5) && !BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(this, $$5, 4.0d)) {
                    setAttackTarget($$5);
                    return true;
                }
                return true;
            }
        }
        return $$3;
    }

    private void setAttackTarget(LivingEntity $$0) {
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        this.brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, $$0, 200L);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public Brain<Zoglin> getBrain() {
        return super.getBrain();
    }

    protected void updateActivity() {
        Activity $$0 = this.brain.getActiveNonCoreActivity().orElse(null);
        this.brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity $$1 = this.brain.getActiveNonCoreActivity().orElse(null);
        if ($$1 == Activity.FIGHT && $$0 != Activity.FIGHT) {
            playAngrySound();
        }
        setAggressive(this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    @Override // net.minecraft.world.entity.Mob
    protected void customServerAiStep(ServerLevel $$0) {
        ProfilerFiller $$1 = Profiler.get();
        $$1.push("zoglinBrain");
        getBrain().tick($$0, this);
        $$1.pop();
        updateActivity();
    }

    @Override // net.minecraft.world.entity.Mob
    public void setBaby(boolean $$0) {
        getEntityData().set(DATA_BABY_ID, Boolean.valueOf($$0));
        if (!level().isClientSide() && $$0) {
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.5d);
        }
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public boolean isBaby() {
        return ((Boolean) getEntityData().get(DATA_BABY_ID)).booleanValue();
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity
    public void aiStep() {
        if (this.attackAnimationRemainingTicks > 0) {
            this.attackAnimationRemainingTicks--;
        }
        super.aiStep();
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void handleEntityEvent(byte $$0) {
        if ($$0 == 4) {
            this.attackAnimationRemainingTicks = 10;
            makeSound(SoundEvents.ZOGLIN_ATTACK);
        } else {
            super.handleEntityEvent($$0);
        }
    }

    @Override // net.minecraft.world.entity.monster.hoglin.HoglinBase
    public int getAttackAnimationRemainingTicks() {
        return this.attackAnimationRemainingTicks;
    }

    @Override // net.minecraft.world.entity.Mob
    protected SoundEvent getAmbientSound() {
        if (level().isClientSide()) {
            return null;
        }
        if (this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return SoundEvents.ZOGLIN_ANGRY;
        }
        return SoundEvents.ZOGLIN_AMBIENT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return SoundEvents.ZOGLIN_HURT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOGLIN_DEATH;
    }

    @Override // net.minecraft.world.entity.Entity
    protected void playStepSound(BlockPos $$0, BlockState $$1) {
        playSound(SoundEvents.ZOGLIN_STEP, 0.15f, 1.0f);
    }

    protected void playAngrySound() {
        makeSound(SoundEvents.ZOGLIN_ANGRY);
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.Targeting
    public LivingEntity getTarget() {
        return getTargetFromBrain();
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putBoolean("IsBaby", isBaby());
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        super.readAdditionalSaveData($$0);
        setBaby($$0.getBooleanOr("IsBaby", false));
    }
}
