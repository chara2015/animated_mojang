package net.minecraft.world.entity.animal.fish;

import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/fish/Salmon.class */
public class Salmon extends AbstractSchoolingFish {
    private static final String TAG_TYPE = "type";
    private static final EntityDataAccessor<Integer> DATA_TYPE = SynchedEntityData.defineId(Salmon.class, EntityDataSerializers.INT);

    public Salmon(EntityType<? extends Salmon> $$0, Level $$1) {
        super($$0, $$1);
        refreshDimensions();
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractSchoolingFish
    public int getMaxSchoolSize() {
        return 5;
    }

    @Override // net.minecraft.world.entity.animal.Bucketable
    public ItemStack getBucketItemStack() {
        return new ItemStack(Items.SALMON_BUCKET);
    }

    @Override // net.minecraft.world.entity.Mob
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return SoundEvents.SALMON_HURT;
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void defineSynchedData(SynchedEntityData.Builder $$0) {
        super.defineSynchedData($$0);
        $$0.define(DATA_TYPE, Integer.valueOf(Variant.DEFAULT.id()));
    }

    @Override // net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity, net.minecraft.network.syncher.SyncedDataHolder
    public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
        super.onSyncedDataUpdated($$0);
        if (DATA_TYPE.equals($$0)) {
            refreshDimensions();
        }
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        super.addAdditionalSaveData($$0);
        $$0.store("type", Variant.CODEC, getVariant());
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        super.readAdditionalSaveData($$0);
        setVariant((Variant) $$0.read("type", Variant.CODEC).orElse(Variant.DEFAULT));
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.animal.Bucketable
    public void saveToBucketTag(ItemStack $$0) {
        Bucketable.saveDefaultDataToBucketTag(this, $$0);
        $$0.copyFrom(DataComponents.SALMON_SIZE, this);
    }

    private void setVariant(Variant $$0) {
        this.entityData.set(DATA_TYPE, Integer.valueOf($$0.id));
    }

    public Variant getVariant() {
        return Variant.BY_ID.apply(((Integer) this.entityData.get(DATA_TYPE)).intValue());
    }

    @Override // net.minecraft.world.entity.Entity, net.minecraft.core.component.DataComponentGetter
    public <T> T get(DataComponentType<? extends T> dataComponentType) {
        if (dataComponentType == DataComponents.SALMON_SIZE) {
            return (T) castComponentValue(dataComponentType, getVariant());
        }
        return (T) super.get(dataComponentType);
    }

    @Override // net.minecraft.world.entity.Entity
    protected void applyImplicitComponents(DataComponentGetter $$0) {
        applyImplicitComponentIfPresent($$0, DataComponents.SALMON_SIZE);
        super.applyImplicitComponents($$0);
    }

    @Override // net.minecraft.world.entity.Entity
    protected <T> boolean applyImplicitComponent(DataComponentType<T> $$0, T $$1) {
        if ($$0 == DataComponents.SALMON_SIZE) {
            setVariant((Variant) castComponentValue(DataComponents.SALMON_SIZE, $$1));
            return true;
        }
        return super.applyImplicitComponent($$0, $$1);
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractSchoolingFish, net.minecraft.world.entity.Mob
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, EntitySpawnReason $$2, SpawnGroupData $$3) {
        WeightedList.Builder<Variant> $$4 = WeightedList.builder();
        $$4.add(Variant.SMALL, 30);
        $$4.add(Variant.MEDIUM, 50);
        $$4.add(Variant.LARGE, 15);
        $$4.build().getRandom(this.random).ifPresent(this::setVariant);
        return super.finalizeSpawn($$0, $$1, $$2, $$3);
    }

    public float getSalmonScale() {
        return getVariant().boundingBoxScale;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected EntityDimensions getDefaultDimensions(Pose $$0) {
        return super.getDefaultDimensions($$0).scale(getSalmonScale());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/fish/Salmon$Variant.class */
    public enum Variant implements StringRepresentable {
        SMALL("small", 0, 0.5f),
        MEDIUM("medium", 1, 1.0f),
        LARGE("large", 2, 1.5f);

        private final String name;
        final int id;
        final float boundingBoxScale;
        public static final Variant DEFAULT = MEDIUM;
        public static final StringRepresentable.EnumCodec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);
        static final IntFunction<Variant> BY_ID = ByIdMap.continuous((v0) -> {
            return v0.id();
        }, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        public static final StreamCodec<ByteBuf, Variant> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
            return v0.id();
        });

        Variant(String $$0, int $$1, float $$2) {
            this.name = $$0;
            this.id = $$1;
            this.boundingBoxScale = $$2;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }

        int id() {
            return this.id;
        }
    }
}
