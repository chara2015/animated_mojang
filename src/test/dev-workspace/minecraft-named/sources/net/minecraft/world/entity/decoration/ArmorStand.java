package net.minecraft.world.entity.decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Rotations;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/decoration/ArmorStand.class */
public class ArmorStand extends LivingEntity {
    public static final int WOBBLE_TIME = 5;
    private static final boolean ENABLE_ARMS = true;
    private static final double FEET_OFFSET = 0.1d;
    private static final double CHEST_OFFSET = 0.9d;
    private static final double LEGS_OFFSET = 0.4d;
    private static final double HEAD_OFFSET = 1.6d;
    public static final int DISABLE_TAKING_OFFSET = 8;
    public static final int DISABLE_PUTTING_OFFSET = 16;
    public static final int CLIENT_FLAG_SMALL = 1;
    public static final int CLIENT_FLAG_SHOW_ARMS = 4;
    public static final int CLIENT_FLAG_NO_BASEPLATE = 8;
    public static final int CLIENT_FLAG_MARKER = 16;
    private static final boolean DEFAULT_INVISIBLE = false;
    private static final int DEFAULT_DISABLED_SLOTS = 0;
    private static final boolean DEFAULT_SMALL = false;
    private static final boolean DEFAULT_SHOW_ARMS = false;
    private static final boolean DEFAULT_NO_BASE_PLATE = false;
    private static final boolean DEFAULT_MARKER = false;
    private boolean invisible;
    public long lastHit;
    private int disabledSlots;
    public static final Rotations DEFAULT_HEAD_POSE = new Rotations(0.0f, 0.0f, 0.0f);
    public static final Rotations DEFAULT_BODY_POSE = new Rotations(0.0f, 0.0f, 0.0f);
    public static final Rotations DEFAULT_LEFT_ARM_POSE = new Rotations(-10.0f, 0.0f, -10.0f);
    public static final Rotations DEFAULT_RIGHT_ARM_POSE = new Rotations(-15.0f, 0.0f, 10.0f);
    public static final Rotations DEFAULT_LEFT_LEG_POSE = new Rotations(-1.0f, 0.0f, -1.0f);
    public static final Rotations DEFAULT_RIGHT_LEG_POSE = new Rotations(1.0f, 0.0f, 1.0f);
    private static final EntityDimensions MARKER_DIMENSIONS = EntityDimensions.fixed(0.0f, 0.0f);
    private static final EntityDimensions BABY_DIMENSIONS = EntityType.ARMOR_STAND.getDimensions().scale(0.5f).withEyeHeight(0.9875f);
    public static final EntityDataAccessor<Byte> DATA_CLIENT_FLAGS = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Rotations> DATA_HEAD_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_BODY_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_LEFT_ARM_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_RIGHT_ARM_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_LEFT_LEG_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_RIGHT_LEG_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
    private static final Predicate<Entity> RIDABLE_MINECARTS = $$0 -> {
        if ($$0 instanceof AbstractMinecart) {
            AbstractMinecart $$1 = (AbstractMinecart) $$0;
            if ($$1.isRideable()) {
                return true;
            }
        }
        return false;
    };

    public ArmorStand(EntityType<? extends ArmorStand> $$0, Level $$1) {
        super($$0, $$1);
        this.invisible = false;
        this.disabledSlots = 0;
    }

    public ArmorStand(Level $$0, double $$1, double $$2, double $$3) {
        this(EntityType.ARMOR_STAND, $$0);
        setPos($$1, $$2, $$3);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes().add(Attributes.STEP_HEIGHT, Density.SURFACE);
    }

    @Override // net.minecraft.world.entity.Entity
    public void refreshDimensions() {
        double $$0 = getX();
        double $$1 = getY();
        double $$2 = getZ();
        super.refreshDimensions();
        setPos($$0, $$1, $$2);
    }

    private boolean hasPhysics() {
        return (isMarker() || isNoGravity()) ? false : true;
    }

    @Override // net.minecraft.world.entity.Entity
    public boolean isEffectiveAi() {
        return super.isEffectiveAi() && hasPhysics();
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void defineSynchedData(SynchedEntityData.Builder $$0) {
        super.defineSynchedData($$0);
        $$0.define(DATA_CLIENT_FLAGS, (byte) 0);
        $$0.define(DATA_HEAD_POSE, DEFAULT_HEAD_POSE);
        $$0.define(DATA_BODY_POSE, DEFAULT_BODY_POSE);
        $$0.define(DATA_LEFT_ARM_POSE, DEFAULT_LEFT_ARM_POSE);
        $$0.define(DATA_RIGHT_ARM_POSE, DEFAULT_RIGHT_ARM_POSE);
        $$0.define(DATA_LEFT_LEG_POSE, DEFAULT_LEFT_LEG_POSE);
        $$0.define(DATA_RIGHT_LEG_POSE, DEFAULT_RIGHT_LEG_POSE);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public boolean canUseSlot(EquipmentSlot $$0) {
        return ($$0 == EquipmentSlot.BODY || $$0 == EquipmentSlot.SADDLE || isDisabled($$0)) ? false : true;
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putBoolean("Invisible", isInvisible());
        $$0.putBoolean("Small", isSmall());
        $$0.putBoolean("ShowArms", showArms());
        $$0.putInt("DisabledSlots", this.disabledSlots);
        $$0.putBoolean("NoBasePlate", !showBasePlate());
        if (isMarker()) {
            $$0.putBoolean("Marker", isMarker());
        }
        $$0.store("Pose", ArmorStandPose.CODEC, getArmorStandPose());
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        super.readAdditionalSaveData($$0);
        setInvisible($$0.getBooleanOr("Invisible", false));
        setSmall($$0.getBooleanOr("Small", false));
        setShowArms($$0.getBooleanOr("ShowArms", false));
        this.disabledSlots = $$0.getIntOr("DisabledSlots", 0);
        setNoBasePlate($$0.getBooleanOr("NoBasePlate", false));
        setMarker($$0.getBooleanOr("Marker", false));
        this.noPhysics = !hasPhysics();
        $$0.read("Pose", ArmorStandPose.CODEC).ifPresent(this::setArmorStandPose);
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public boolean isPushable() {
        return false;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected void doPush(Entity $$0) {
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected void pushEntities() {
        List<Entity> $$0 = level().getEntities(this, getBoundingBox(), RIDABLE_MINECARTS);
        for (Entity $$1 : $$0) {
            if (distanceToSqr($$1) <= 0.2d) {
                $$1.push(this);
            }
        }
    }

    @Override // net.minecraft.world.entity.Entity
    public InteractionResult interactAt(Player $$0, Vec3 $$1, InteractionHand $$2) {
        ItemStack $$3 = $$0.getItemInHand($$2);
        if (isMarker() || $$3.is(Items.NAME_TAG)) {
            return InteractionResult.PASS;
        }
        if ($$0.isSpectator()) {
            return InteractionResult.SUCCESS;
        }
        if ($$0.level().isClientSide()) {
            return InteractionResult.SUCCESS_SERVER;
        }
        EquipmentSlot $$4 = getEquipmentSlotForItem($$3);
        if ($$3.isEmpty()) {
            EquipmentSlot $$5 = getClickedSlot($$1);
            EquipmentSlot $$6 = isDisabled($$5) ? $$4 : $$5;
            if (hasItemInSlot($$6) && swapItem($$0, $$6, $$3, $$2)) {
                return InteractionResult.SUCCESS_SERVER;
            }
        } else {
            if (isDisabled($$4)) {
                return InteractionResult.FAIL;
            }
            if ($$4.getType() == EquipmentSlot.Type.HAND && !showArms()) {
                return InteractionResult.FAIL;
            }
            if (swapItem($$0, $$4, $$3, $$2)) {
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return InteractionResult.PASS;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private net.minecraft.world.entity.EquipmentSlot getClickedSlot(net.minecraft.world.phys.Vec3 r8) {
        /*
            Method dump skipped, instruction units count: 237
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.minecraft.world.entity.decoration.ArmorStand.getClickedSlot(net.minecraft.world.phys.Vec3):net.minecraft.world.entity.EquipmentSlot");
    }

    private boolean isDisabled(EquipmentSlot $$0) {
        return (this.disabledSlots & (1 << $$0.getFilterBit(0))) != 0 || ($$0.getType() == EquipmentSlot.Type.HAND && !showArms());
    }

    private boolean swapItem(Player $$0, EquipmentSlot $$1, ItemStack $$2, InteractionHand $$3) {
        ItemStack $$4 = getItemBySlot($$1);
        if (!$$4.isEmpty() && (this.disabledSlots & (1 << $$1.getFilterBit(8))) != 0) {
            return false;
        }
        if ($$4.isEmpty() && (this.disabledSlots & (1 << $$1.getFilterBit(16))) != 0) {
            return false;
        }
        if ($$0.hasInfiniteMaterials() && $$4.isEmpty() && !$$2.isEmpty()) {
            setItemSlot($$1, $$2.copyWithCount(1));
            return true;
        }
        if (!$$2.isEmpty() && $$2.getCount() > 1) {
            if (!$$4.isEmpty()) {
                return false;
            }
            setItemSlot($$1, $$2.split(1));
            return true;
        }
        setItemSlot($$1, $$2);
        $$0.setItemInHand($$3, $$4);
        return true;
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public boolean hurtServer(ServerLevel $$0, DamageSource $$1, float $$2) {
        if (isRemoved()) {
            return false;
        }
        if (!((Boolean) $$0.getGameRules().get(GameRules.MOB_GRIEFING)).booleanValue() && ($$1.getEntity() instanceof Mob)) {
            return false;
        }
        if ($$1.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            kill($$0);
            return false;
        }
        if (isInvulnerableTo($$0, $$1) || this.invisible || isMarker()) {
            return false;
        }
        if ($$1.is(DamageTypeTags.IS_EXPLOSION)) {
            brokenByAnything($$0, $$1);
            kill($$0);
            return false;
        }
        if ($$1.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
            if (isOnFire()) {
                causeDamage($$0, $$1, 0.15f);
                return false;
            }
            igniteForSeconds(5.0f);
            return false;
        }
        if ($$1.is(DamageTypeTags.BURNS_ARMOR_STANDS) && getHealth() > 0.5f) {
            causeDamage($$0, $$1, 4.0f);
            return false;
        }
        boolean $$3 = $$1.is(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
        boolean $$4 = $$1.is(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);
        if (!$$3 && !$$4) {
            return false;
        }
        Entity entity = $$1.getEntity();
        if (entity instanceof Player) {
            Player $$5 = (Player) entity;
            if (!$$5.getAbilities().mayBuild) {
                return false;
            }
        }
        if ($$1.isCreativePlayer()) {
            playBrokenSound();
            showBreakingParticles();
            kill($$0);
            return true;
        }
        long $$6 = $$0.getGameTime();
        if ($$6 - this.lastHit <= 5 || $$4) {
            brokenByPlayer($$0, $$1);
            showBreakingParticles();
            kill($$0);
            return true;
        }
        $$0.broadcastEntityEvent(this, (byte) 32);
        gameEvent(GameEvent.ENTITY_DAMAGE, $$1.getEntity());
        this.lastHit = $$6;
        return true;
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void handleEntityEvent(byte $$0) {
        if ($$0 == 32) {
            if (level().isClientSide()) {
                level().playLocalSound(getX(), getY(), getZ(), SoundEvents.ARMOR_STAND_HIT, getSoundSource(), 0.3f, 1.0f, false);
                this.lastHit = level().getGameTime();
                return;
            }
            return;
        }
        super.handleEntityEvent($$0);
    }

    @Override // net.minecraft.world.entity.Entity
    public boolean shouldRenderAtSqrDistance(double $$0) {
        double $$1 = getBoundingBox().getSize() * 4.0d;
        if (Double.isNaN($$1) || $$1 == Density.SURFACE) {
            $$1 = 4.0d;
        }
        double $$12 = $$1 * 64.0d;
        return $$0 < $$12 * $$12;
    }

    private void showBreakingParticles() {
        if (level() instanceof ServerLevel) {
            ((ServerLevel) level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), getX(), getY(0.6666666666666666d), getZ(), 10, getBbWidth() / 4.0f, getBbHeight() / 4.0f, getBbWidth() / 4.0f, 0.05d);
        }
    }

    private void causeDamage(ServerLevel $$0, DamageSource $$1, float $$2) {
        float $$3 = getHealth() - $$2;
        if ($$3 <= 0.5f) {
            brokenByAnything($$0, $$1);
            kill($$0);
        } else {
            setHealth($$3);
            gameEvent(GameEvent.ENTITY_DAMAGE, $$1.getEntity());
        }
    }

    private void brokenByPlayer(ServerLevel $$0, DamageSource $$1) {
        ItemStack $$2 = new ItemStack(Items.ARMOR_STAND);
        $$2.set(DataComponents.CUSTOM_NAME, getCustomName());
        Block.popResource(level(), blockPosition(), $$2);
        brokenByAnything($$0, $$1);
    }

    private void brokenByAnything(ServerLevel $$0, DamageSource $$1) {
        playBrokenSound();
        dropAllDeathLoot($$0, $$1);
        for (EquipmentSlot $$2 : EquipmentSlot.VALUES) {
            ItemStack $$3 = this.equipment.set($$2, ItemStack.EMPTY);
            if (!$$3.isEmpty()) {
                Block.popResource(level(), blockPosition().above(), $$3);
            }
        }
    }

    private void playBrokenSound() {
        level().playSound((Entity) null, getX(), getY(), getZ(), SoundEvents.ARMOR_STAND_BREAK, getSoundSource(), 1.0f, 1.0f);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected void tickHeadTurn(float $$0) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = getYRot();
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public void travel(Vec3 $$0) {
        if (!hasPhysics()) {
            return;
        }
        super.travel($$0);
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void setYBodyRot(float $$0) {
        this.yRotO = $$0;
        this.yBodyRotO = $$0;
        this.yHeadRot = $$0;
        this.yHeadRotO = $$0;
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void setYHeadRot(float $$0) {
        this.yRotO = $$0;
        this.yBodyRotO = $$0;
        this.yHeadRot = $$0;
        this.yHeadRotO = $$0;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected void updateInvisibilityStatus() {
        setInvisible(this.invisible);
    }

    @Override // net.minecraft.world.entity.Entity
    public void setInvisible(boolean $$0) {
        this.invisible = $$0;
        super.setInvisible($$0);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public boolean isBaby() {
        return isSmall();
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public void kill(ServerLevel $$0) {
        remove(Entity.RemovalReason.KILLED);
        gameEvent(GameEvent.ENTITY_DIE);
    }

    @Override // net.minecraft.world.entity.Entity
    public boolean ignoreExplosion(Explosion $$0) {
        if ($$0.shouldAffectBlocklikeEntities()) {
            return isInvisible();
        }
        return true;
    }

    @Override // net.minecraft.world.entity.Entity
    public PushReaction getPistonPushReaction() {
        if (isMarker()) {
            return PushReaction.IGNORE;
        }
        return super.getPistonPushReaction();
    }

    @Override // net.minecraft.world.entity.Entity
    public boolean isIgnoringBlockTriggers() {
        return isMarker();
    }

    private void setSmall(boolean $$0) {
        this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 1, $$0)));
    }

    public boolean isSmall() {
        return (((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 1) != 0;
    }

    public void setShowArms(boolean $$0) {
        this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 4, $$0)));
    }

    public boolean showArms() {
        return (((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 4) != 0;
    }

    public void setNoBasePlate(boolean $$0) {
        this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 8, $$0)));
    }

    public boolean showBasePlate() {
        return (((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 8) == 0;
    }

    private void setMarker(boolean $$0) {
        this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 16, $$0)));
    }

    public boolean isMarker() {
        return (((Byte) this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 16) != 0;
    }

    private byte setBit(byte $$0, int $$1, boolean $$2) {
        byte $$02;
        if ($$2) {
            $$02 = (byte) ($$0 | $$1);
        } else {
            $$02 = (byte) ($$0 & ($$1 ^ (-1)));
        }
        return $$02;
    }

    public void setHeadPose(Rotations $$0) {
        this.entityData.set(DATA_HEAD_POSE, $$0);
    }

    public void setBodyPose(Rotations $$0) {
        this.entityData.set(DATA_BODY_POSE, $$0);
    }

    public void setLeftArmPose(Rotations $$0) {
        this.entityData.set(DATA_LEFT_ARM_POSE, $$0);
    }

    public void setRightArmPose(Rotations $$0) {
        this.entityData.set(DATA_RIGHT_ARM_POSE, $$0);
    }

    public void setLeftLegPose(Rotations $$0) {
        this.entityData.set(DATA_LEFT_LEG_POSE, $$0);
    }

    public void setRightLegPose(Rotations $$0) {
        this.entityData.set(DATA_RIGHT_LEG_POSE, $$0);
    }

    public Rotations getHeadPose() {
        return (Rotations) this.entityData.get(DATA_HEAD_POSE);
    }

    public Rotations getBodyPose() {
        return (Rotations) this.entityData.get(DATA_BODY_POSE);
    }

    public Rotations getLeftArmPose() {
        return (Rotations) this.entityData.get(DATA_LEFT_ARM_POSE);
    }

    public Rotations getRightArmPose() {
        return (Rotations) this.entityData.get(DATA_RIGHT_ARM_POSE);
    }

    public Rotations getLeftLegPose() {
        return (Rotations) this.entityData.get(DATA_LEFT_LEG_POSE);
    }

    public Rotations getRightLegPose() {
        return (Rotations) this.entityData.get(DATA_RIGHT_LEG_POSE);
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    public boolean isPickable() {
        return super.isPickable() && !isMarker();
    }

    @Override // net.minecraft.world.entity.Entity
    public boolean skipAttackInteraction(Entity $$0) {
        if ($$0 instanceof Player) {
            Player $$1 = (Player) $$0;
            if (!level().mayInteract($$1, blockPosition())) {
                return true;
            }
        }
        return false;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public LivingEntity.Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.ARMOR_STAND_FALL, SoundEvents.ARMOR_STAND_FALL);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return SoundEvents.ARMOR_STAND_HIT;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected SoundEvent getDeathSound() {
        return SoundEvents.ARMOR_STAND_BREAK;
    }

    @Override // net.minecraft.world.entity.Entity
    public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity, net.minecraft.network.syncher.SyncedDataHolder
    public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
        if (DATA_CLIENT_FLAGS.equals($$0)) {
            refreshDimensions();
            this.blocksBuilding = !isMarker();
        }
        super.onSyncedDataUpdated($$0);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public boolean attackable() {
        return false;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public EntityDimensions getDefaultDimensions(Pose $$0) {
        return getDimensionsMarker(isMarker());
    }

    private EntityDimensions getDimensionsMarker(boolean $$0) {
        if ($$0) {
            return MARKER_DIMENSIONS;
        }
        return isBaby() ? BABY_DIMENSIONS : getType().getDimensions();
    }

    @Override // net.minecraft.world.entity.Entity
    public Vec3 getLightProbePosition(float $$0) {
        if (isMarker()) {
            AABB $$1 = getDimensionsMarker(false).makeBoundingBox(position());
            BlockPos $$2 = blockPosition();
            int $$3 = Integer.MIN_VALUE;
            for (BlockPos $$4 : BlockPos.betweenClosed(BlockPos.containing($$1.minX, $$1.minY, $$1.minZ), BlockPos.containing($$1.maxX, $$1.maxY, $$1.maxZ))) {
                int $$5 = Math.max(level().getBrightness(LightLayer.BLOCK, $$4), level().getBrightness(LightLayer.SKY, $$4));
                if ($$5 == 15) {
                    return Vec3.atCenterOf($$4);
                }
                if ($$5 > $$3) {
                    $$3 = $$5;
                    $$2 = $$4.immutable();
                }
            }
            return Vec3.atCenterOf($$2);
        }
        return super.getLightProbePosition($$0);
    }

    @Override // net.minecraft.world.entity.Entity
    public ItemStack getPickResult() {
        return new ItemStack(Items.ARMOR_STAND);
    }

    @Override // net.minecraft.world.entity.LivingEntity
    public boolean canBeSeenByAnyone() {
        return (isInvisible() || isMarker()) ? false : true;
    }

    public void setArmorStandPose(ArmorStandPose $$0) {
        setHeadPose($$0.head());
        setBodyPose($$0.body());
        setLeftArmPose($$0.leftArm());
        setRightArmPose($$0.rightArm());
        setLeftLegPose($$0.leftLeg());
        setRightLegPose($$0.rightLeg());
    }

    public ArmorStandPose getArmorStandPose() {
        return new ArmorStandPose(getHeadPose(), getBodyPose(), getLeftArmPose(), getRightArmPose(), getLeftLegPose(), getRightLegPose());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose.class */
    public static final class ArmorStandPose extends Record {
        private final Rotations head;
        private final Rotations body;
        private final Rotations leftArm;
        private final Rotations rightArm;
        private final Rotations leftLeg;
        private final Rotations rightLeg;
        public static final ArmorStandPose DEFAULT = new ArmorStandPose(ArmorStand.DEFAULT_HEAD_POSE, ArmorStand.DEFAULT_BODY_POSE, ArmorStand.DEFAULT_LEFT_ARM_POSE, ArmorStand.DEFAULT_RIGHT_ARM_POSE, ArmorStand.DEFAULT_LEFT_LEG_POSE, ArmorStand.DEFAULT_RIGHT_LEG_POSE);
        public static final Codec<ArmorStandPose> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Rotations.CODEC.optionalFieldOf("Head", ArmorStand.DEFAULT_HEAD_POSE).forGetter((v0) -> {
                return v0.head();
            }), Rotations.CODEC.optionalFieldOf("Body", ArmorStand.DEFAULT_BODY_POSE).forGetter((v0) -> {
                return v0.body();
            }), Rotations.CODEC.optionalFieldOf("LeftArm", ArmorStand.DEFAULT_LEFT_ARM_POSE).forGetter((v0) -> {
                return v0.leftArm();
            }), Rotations.CODEC.optionalFieldOf("RightArm", ArmorStand.DEFAULT_RIGHT_ARM_POSE).forGetter((v0) -> {
                return v0.rightArm();
            }), Rotations.CODEC.optionalFieldOf("LeftLeg", ArmorStand.DEFAULT_LEFT_LEG_POSE).forGetter((v0) -> {
                return v0.leftLeg();
            }), Rotations.CODEC.optionalFieldOf("RightLeg", ArmorStand.DEFAULT_RIGHT_LEG_POSE).forGetter((v0) -> {
                return v0.rightLeg();
            })).apply($$0, ArmorStandPose::new);
        });

        public ArmorStandPose(Rotations $$0, Rotations $$1, Rotations $$2, Rotations $$3, Rotations $$4, Rotations $$5) {
            this.head = $$0;
            this.body = $$1;
            this.leftArm = $$2;
            this.rightArm = $$3;
            this.leftLeg = $$4;
            this.rightLeg = $$5;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ArmorStandPose.class), ArmorStandPose.class, "head;body;leftArm;rightArm;leftLeg;rightLeg", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->head:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->body:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->leftArm:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->rightArm:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->leftLeg:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->rightLeg:Lnet/minecraft/core/Rotations;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ArmorStandPose.class), ArmorStandPose.class, "head;body;leftArm;rightArm;leftLeg;rightLeg", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->head:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->body:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->leftArm:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->rightArm:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->leftLeg:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->rightLeg:Lnet/minecraft/core/Rotations;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ArmorStandPose.class, Object.class), ArmorStandPose.class, "head;body;leftArm;rightArm;leftLeg;rightLeg", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->head:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->body:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->leftArm:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->rightArm:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->leftLeg:Lnet/minecraft/core/Rotations;", "FIELD:Lnet/minecraft/world/entity/decoration/ArmorStand$ArmorStandPose;->rightLeg:Lnet/minecraft/core/Rotations;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Rotations head() {
            return this.head;
        }

        public Rotations body() {
            return this.body;
        }

        public Rotations leftArm() {
            return this.leftArm;
        }

        public Rotations rightArm() {
            return this.rightArm;
        }

        public Rotations leftLeg() {
            return this.leftLeg;
        }

        public Rotations rightLeg() {
            return this.rightLeg;
        }
    }
}
