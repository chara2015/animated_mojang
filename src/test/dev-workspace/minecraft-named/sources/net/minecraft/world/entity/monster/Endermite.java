package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/monster/Endermite.class */
public class Endermite extends Monster {
    private static final int MAX_LIFE = 2400;
    private static final int DEFAULT_LIFE = 0;
    private int life;

    public Endermite(EntityType<? extends Endermite> $$0, Level $$1) {
        super($$0, $$1);
        this.life = 0;
        this.xpReward = 3;
    }

    @Override // net.minecraft.world.entity.Mob
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, level()));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0d, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 8.0d).add(Attributes.MOVEMENT_SPEED, 0.25d).add(Attributes.ATTACK_DAMAGE, 2.0d);
    }

    @Override // net.minecraft.world.entity.Entity
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override // net.minecraft.world.entity.Mob
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDERMITE_AMBIENT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return SoundEvents.ENDERMITE_HURT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMITE_DEATH;
    }

    @Override // net.minecraft.world.entity.Entity
    protected void playStepSound(BlockPos $$0, BlockState $$1) {
        playSound(SoundEvents.ENDERMITE_STEP, 0.15f, 1.0f);
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        super.readAdditionalSaveData($$0);
        this.life = $$0.getIntOr("Lifetime", 0);
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putInt("Lifetime", this.life);
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void tick() {
        this.yBodyRot = getYRot();
        super.tick();
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void setYBodyRot(float $$0) {
        setYRot($$0);
        super.setYBodyRot($$0);
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide()) {
            for (int $$0 = 0; $$0 < 2; $$0++) {
                level().addParticle(ParticleTypes.PORTAL, getRandomX(0.5d), getRandomY(), getRandomZ(0.5d), (this.random.nextDouble() - 0.5d) * 2.0d, -this.random.nextDouble(), (this.random.nextDouble() - 0.5d) * 2.0d);
            }
            return;
        }
        if (!isPersistenceRequired()) {
            this.life++;
        }
        if (this.life >= 2400) {
            discard();
        }
    }

    public static boolean checkEndermiteSpawnRules(EntityType<Endermite> $$0, LevelAccessor $$1, EntitySpawnReason $$2, BlockPos $$3, RandomSource $$4) {
        if (!checkAnyLightMonsterSpawnRules($$0, $$1, $$2, $$3, $$4)) {
            return false;
        }
        if (EntitySpawnReason.isSpawner($$2)) {
            return true;
        }
        Player $$5 = $$1.getNearestPlayer(((double) $$3.getX()) + 0.5d, ((double) $$3.getY()) + 0.5d, ((double) $$3.getZ()) + 0.5d, 5.0d, true);
        return $$5 == null;
    }
}
