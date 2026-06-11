package net.minecraft.world.entity.monster.skeleton;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/monster/skeleton/WitherSkeleton.class */
public class WitherSkeleton extends AbstractSkeleton {
    public WitherSkeleton(EntityType<? extends WitherSkeleton> $$0, Level $$1) {
        super($$0, $$1);
        setPathfindingMalus(PathType.LAVA, 8.0f);
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton, net.minecraft.world.entity.Mob
    protected void registerGoals() {
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractPiglin.class, true));
        super.registerGoals();
    }

    @Override // net.minecraft.world.entity.Mob
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    @Override // net.minecraft.world.entity.monster.Monster, net.minecraft.world.entity.LivingEntity
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton
    SoundEvent getStepSound() {
        return SoundEvents.WITHER_SKELETON_STEP;
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton, net.minecraft.world.entity.Mob
    public TagKey<Item> getPreferredWeaponType() {
        return null;
    }

    @Override // net.minecraft.world.entity.Mob
    public boolean canHoldItem(ItemStack $$0) {
        return !$$0.is(ItemTags.WITHER_SKELETON_DISLIKED_WEAPONS) && super.canHoldItem($$0);
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton, net.minecraft.world.entity.Mob
    protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    @Override // net.minecraft.world.entity.Mob
    protected void populateDefaultEquipmentEnchantments(ServerLevelAccessor $$0, RandomSource $$1, DifficultyInstance $$2) {
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton, net.minecraft.world.entity.Mob
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, EntitySpawnReason $$2, SpawnGroupData $$3) {
        SpawnGroupData $$4 = super.finalizeSpawn($$0, $$1, $$2, $$3);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0d);
        reassessWeaponGoal();
        return $$4;
    }

    @Override // net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity
    public boolean doHurtTarget(ServerLevel $$0, Entity $$1) {
        if (!super.doHurtTarget($$0, $$1)) {
            return false;
        }
        if ($$1 instanceof LivingEntity) {
            ((LivingEntity) $$1).addEffect(new MobEffectInstance(MobEffects.WITHER, 200), this);
            return true;
        }
        return true;
    }

    @Override // net.minecraft.world.entity.monster.skeleton.AbstractSkeleton
    protected AbstractArrow getArrow(ItemStack $$0, float $$1, ItemStack $$2) {
        AbstractArrow $$3 = super.getArrow($$0, $$1, $$2);
        $$3.igniteForSeconds(100.0f);
        return $$3;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public boolean canBeAffected(MobEffectInstance $$0) {
        if ($$0.is(MobEffects.WITHER)) {
            return false;
        }
        return super.canBeAffected($$0);
    }
}
