package net.minecraft.world.entity.animal.fish;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.fish.AbstractSchoolingFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/fish/TropicalFish.class */
public class TropicalFish extends AbstractSchoolingFish {
    public static final Variant DEFAULT_VARIANT = new Variant(Pattern.KOB, DyeColor.WHITE, DyeColor.WHITE);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(TropicalFish.class, EntityDataSerializers.INT);
    public static final List<Variant> COMMON_VARIANTS = List.of((Object[]) new Variant[]{new Variant(Pattern.STRIPEY, DyeColor.ORANGE, DyeColor.GRAY), new Variant(Pattern.FLOPPER, DyeColor.GRAY, DyeColor.GRAY), new Variant(Pattern.FLOPPER, DyeColor.GRAY, DyeColor.BLUE), new Variant(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.GRAY), new Variant(Pattern.SUNSTREAK, DyeColor.BLUE, DyeColor.GRAY), new Variant(Pattern.KOB, DyeColor.ORANGE, DyeColor.WHITE), new Variant(Pattern.SPOTTY, DyeColor.PINK, DyeColor.LIGHT_BLUE), new Variant(Pattern.BLOCKFISH, DyeColor.PURPLE, DyeColor.YELLOW), new Variant(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.RED), new Variant(Pattern.SPOTTY, DyeColor.WHITE, DyeColor.YELLOW), new Variant(Pattern.GLITTER, DyeColor.WHITE, DyeColor.GRAY), new Variant(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.ORANGE), new Variant(Pattern.DASHER, DyeColor.CYAN, DyeColor.PINK), new Variant(Pattern.BRINELY, DyeColor.LIME, DyeColor.LIGHT_BLUE), new Variant(Pattern.BETTY, DyeColor.RED, DyeColor.WHITE), new Variant(Pattern.SNOOPER, DyeColor.GRAY, DyeColor.RED), new Variant(Pattern.BLOCKFISH, DyeColor.RED, DyeColor.WHITE), new Variant(Pattern.FLOPPER, DyeColor.WHITE, DyeColor.YELLOW), new Variant(Pattern.KOB, DyeColor.RED, DyeColor.WHITE), new Variant(Pattern.SUNSTREAK, DyeColor.GRAY, DyeColor.WHITE), new Variant(Pattern.DASHER, DyeColor.CYAN, DyeColor.YELLOW), new Variant(Pattern.FLOPPER, DyeColor.YELLOW, DyeColor.YELLOW)});
    private boolean isSchool;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/fish/TropicalFish$Base.class */
    public enum Base {
        SMALL(0),
        LARGE(1);

        final int id;

        Base(int $$0) {
            this.id = $$0;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/fish/TropicalFish$Variant.class */
    public static final class Variant extends Record {
        private final Pattern pattern;
        private final DyeColor baseColor;
        private final DyeColor patternColor;
        public static final Codec<Variant> CODEC = Codec.INT.xmap((v1) -> {
            return new Variant(v1);
        }, (v0) -> {
            return v0.getPackedId();
        });

        public Variant(Pattern $$0, DyeColor $$1, DyeColor $$2) {
            this.pattern = $$0;
            this.baseColor = $$1;
            this.patternColor = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Variant.class), Variant.class, "pattern;baseColor;patternColor", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->pattern:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Pattern;", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->baseColor:Lnet/minecraft/world/item/DyeColor;", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->patternColor:Lnet/minecraft/world/item/DyeColor;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Variant.class), Variant.class, "pattern;baseColor;patternColor", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->pattern:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Pattern;", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->baseColor:Lnet/minecraft/world/item/DyeColor;", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->patternColor:Lnet/minecraft/world/item/DyeColor;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Variant.class, Object.class), Variant.class, "pattern;baseColor;patternColor", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->pattern:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Pattern;", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->baseColor:Lnet/minecraft/world/item/DyeColor;", "FIELD:Lnet/minecraft/world/entity/animal/fish/TropicalFish$Variant;->patternColor:Lnet/minecraft/world/item/DyeColor;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Pattern pattern() {
            return this.pattern;
        }

        public DyeColor baseColor() {
            return this.baseColor;
        }

        public DyeColor patternColor() {
            return this.patternColor;
        }

        public Variant(int $$0) {
            this(TropicalFish.getPattern($$0), TropicalFish.getBaseColor($$0), TropicalFish.getPatternColor($$0));
        }

        public int getPackedId() {
            return TropicalFish.packVariant(this.pattern, this.baseColor, this.patternColor);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/fish/TropicalFish$Pattern.class */
    public enum Pattern implements StringRepresentable, TooltipProvider {
        KOB("kob", Base.SMALL, 0),
        SUNSTREAK("sunstreak", Base.SMALL, 1),
        SNOOPER("snooper", Base.SMALL, 2),
        DASHER("dasher", Base.SMALL, 3),
        BRINELY("brinely", Base.SMALL, 4),
        SPOTTY("spotty", Base.SMALL, 5),
        FLOPPER("flopper", Base.LARGE, 0),
        STRIPEY("stripey", Base.LARGE, 1),
        GLITTER("glitter", Base.LARGE, 2),
        BLOCKFISH("blockfish", Base.LARGE, 3),
        BETTY("betty", Base.LARGE, 4),
        CLAYFISH("clayfish", Base.LARGE, 5);

        private final String name;
        private final Component displayName;
        private final Base base;
        private final int packedId;
        public static final Codec<Pattern> CODEC = StringRepresentable.fromEnum(Pattern::values);
        private static final IntFunction<Pattern> BY_ID = ByIdMap.sparse((v0) -> {
            return v0.getPackedId();
        }, values(), KOB);
        public static final StreamCodec<ByteBuf, Pattern> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
            return v0.getPackedId();
        });

        Pattern(String $$0, Base $$1, int $$2) {
            this.name = $$0;
            this.base = $$1;
            this.packedId = $$1.id | ($$2 << 8);
            this.displayName = Component.translatable("entity.minecraft.tropical_fish.type." + this.name);
        }

        public static Pattern byId(int $$0) {
            return BY_ID.apply($$0);
        }

        public Base base() {
            return this.base;
        }

        public int getPackedId() {
            return this.packedId;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }

        public Component displayName() {
            return this.displayName;
        }

        @Override // net.minecraft.world.item.component.TooltipProvider
        public void addToTooltip(Item.TooltipContext $$0, Consumer<Component> $$1, TooltipFlag $$2, DataComponentGetter $$3) {
            DyeColor $$4 = (DyeColor) $$3.getOrDefault(DataComponents.TROPICAL_FISH_BASE_COLOR, TropicalFish.DEFAULT_VARIANT.baseColor());
            DyeColor $$5 = (DyeColor) $$3.getOrDefault(DataComponents.TROPICAL_FISH_PATTERN_COLOR, TropicalFish.DEFAULT_VARIANT.patternColor());
            ChatFormatting[] $$6 = {ChatFormatting.ITALIC, ChatFormatting.GRAY};
            int $$7 = TropicalFish.COMMON_VARIANTS.indexOf(new Variant(this, $$4, $$5));
            if ($$7 != -1) {
                $$1.accept(Component.translatable(TropicalFish.getPredefinedName($$7)).withStyle($$6));
                return;
            }
            $$1.accept(this.displayName.plainCopy().withStyle($$6));
            MutableComponent $$8 = Component.translatable("color.minecraft." + $$4.getName());
            if ($$4 != $$5) {
                $$8.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT).append(Component.translatable("color.minecraft." + $$5.getName()));
            }
            $$8.withStyle($$6);
            $$1.accept($$8);
        }
    }

    public TropicalFish(EntityType<? extends TropicalFish> $$0, Level $$1) {
        super($$0, $$1);
        this.isSchool = true;
    }

    public static String getPredefinedName(int $$0) {
        return "entity.minecraft.tropical_fish.predefined." + $$0;
    }

    static int packVariant(Pattern $$0, DyeColor $$1, DyeColor $$2) {
        return ($$0.getPackedId() & 65535) | (($$1.getId() & 255) << 16) | (($$2.getId() & 255) << 24);
    }

    public static DyeColor getBaseColor(int $$0) {
        return DyeColor.byId(($$0 >> 16) & 255);
    }

    public static DyeColor getPatternColor(int $$0) {
        return DyeColor.byId(($$0 >> 24) & 255);
    }

    public static Pattern getPattern(int $$0) {
        return Pattern.byId($$0 & 65535);
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void defineSynchedData(SynchedEntityData.Builder $$0) {
        super.defineSynchedData($$0);
        $$0.define(DATA_ID_TYPE_VARIANT, Integer.valueOf(DEFAULT_VARIANT.getPackedId()));
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        super.addAdditionalSaveData($$0);
        $$0.store(Axolotl.VARIANT_TAG, Variant.CODEC, new Variant(getPackedVariant()));
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.Mob, net.minecraft.world.entity.LivingEntity, net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        super.readAdditionalSaveData($$0);
        Variant $$1 = (Variant) $$0.read(Axolotl.VARIANT_TAG, Variant.CODEC).orElse(DEFAULT_VARIANT);
        setPackedVariant($$1.getPackedId());
    }

    private void setPackedVariant(int $$0) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, Integer.valueOf($$0));
    }

    @Override // net.minecraft.world.entity.Mob
    public boolean isMaxGroupSizeReached(int $$0) {
        return !this.isSchool;
    }

    private int getPackedVariant() {
        return ((Integer) this.entityData.get(DATA_ID_TYPE_VARIANT)).intValue();
    }

    public DyeColor getBaseColor() {
        return getBaseColor(getPackedVariant());
    }

    public DyeColor getPatternColor() {
        return getPatternColor(getPackedVariant());
    }

    public Pattern getPattern() {
        return getPattern(getPackedVariant());
    }

    private void setPattern(Pattern $$0) {
        int $$1 = getPackedVariant();
        DyeColor $$2 = getBaseColor($$1);
        DyeColor $$3 = getPatternColor($$1);
        setPackedVariant(packVariant($$0, $$2, $$3));
    }

    private void setBaseColor(DyeColor $$0) {
        int $$1 = getPackedVariant();
        Pattern $$2 = getPattern($$1);
        DyeColor $$3 = getPatternColor($$1);
        setPackedVariant(packVariant($$2, $$0, $$3));
    }

    private void setPatternColor(DyeColor $$0) {
        int $$1 = getPackedVariant();
        Pattern $$2 = getPattern($$1);
        DyeColor $$3 = getBaseColor($$1);
        setPackedVariant(packVariant($$2, $$3, $$0));
    }

    @Override // net.minecraft.world.entity.Entity, net.minecraft.core.component.DataComponentGetter
    public <T> T get(DataComponentType<? extends T> dataComponentType) {
        if (dataComponentType == DataComponents.TROPICAL_FISH_PATTERN) {
            return (T) castComponentValue(dataComponentType, getPattern());
        }
        if (dataComponentType == DataComponents.TROPICAL_FISH_BASE_COLOR) {
            return (T) castComponentValue(dataComponentType, getBaseColor());
        }
        if (dataComponentType == DataComponents.TROPICAL_FISH_PATTERN_COLOR) {
            return (T) castComponentValue(dataComponentType, getPatternColor());
        }
        return (T) super.get(dataComponentType);
    }

    @Override // net.minecraft.world.entity.Entity
    protected void applyImplicitComponents(DataComponentGetter $$0) {
        applyImplicitComponentIfPresent($$0, DataComponents.TROPICAL_FISH_PATTERN);
        applyImplicitComponentIfPresent($$0, DataComponents.TROPICAL_FISH_BASE_COLOR);
        applyImplicitComponentIfPresent($$0, DataComponents.TROPICAL_FISH_PATTERN_COLOR);
        super.applyImplicitComponents($$0);
    }

    @Override // net.minecraft.world.entity.Entity
    protected <T> boolean applyImplicitComponent(DataComponentType<T> $$0, T $$1) {
        if ($$0 == DataComponents.TROPICAL_FISH_PATTERN) {
            setPattern((Pattern) castComponentValue(DataComponents.TROPICAL_FISH_PATTERN, $$1));
            return true;
        }
        if ($$0 == DataComponents.TROPICAL_FISH_BASE_COLOR) {
            setBaseColor((DyeColor) castComponentValue(DataComponents.TROPICAL_FISH_BASE_COLOR, $$1));
            return true;
        }
        if ($$0 == DataComponents.TROPICAL_FISH_PATTERN_COLOR) {
            setPatternColor((DyeColor) castComponentValue(DataComponents.TROPICAL_FISH_PATTERN_COLOR, $$1));
            return true;
        }
        return super.applyImplicitComponent($$0, $$1);
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish, net.minecraft.world.entity.animal.Bucketable
    public void saveToBucketTag(ItemStack $$0) {
        super.saveToBucketTag($$0);
        $$0.copyFrom(DataComponents.TROPICAL_FISH_PATTERN, this);
        $$0.copyFrom(DataComponents.TROPICAL_FISH_BASE_COLOR, this);
        $$0.copyFrom(DataComponents.TROPICAL_FISH_PATTERN_COLOR, this);
    }

    @Override // net.minecraft.world.entity.animal.Bucketable
    public ItemStack getBucketItemStack() {
        return new ItemStack(Items.TROPICAL_FISH_BUCKET);
    }

    @Override // net.minecraft.world.entity.Mob
    protected SoundEvent getAmbientSound() {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    @Override // net.minecraft.world.entity.LivingEntity
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractFish
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override // net.minecraft.world.entity.animal.fish.AbstractSchoolingFish, net.minecraft.world.entity.Mob
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, EntitySpawnReason $$2, SpawnGroupData $$3) {
        Variant $$13;
        SpawnGroupData $$32 = super.finalizeSpawn($$0, $$1, $$2, $$3);
        RandomSource $$4 = $$0.getRandom();
        if ($$32 instanceof TropicalFishGroupData) {
            TropicalFishGroupData $$5 = (TropicalFishGroupData) $$32;
            $$13 = $$5.variant;
        } else if ($$4.nextFloat() < 0.9d) {
            $$13 = (Variant) Util.getRandom(COMMON_VARIANTS, $$4);
            $$32 = new TropicalFishGroupData(this, $$13);
        } else {
            this.isSchool = false;
            Pattern[] $$8 = Pattern.values();
            DyeColor[] $$9 = DyeColor.values();
            Pattern $$10 = (Pattern) Util.getRandom($$8, $$4);
            DyeColor $$11 = (DyeColor) Util.getRandom($$9, $$4);
            DyeColor $$12 = (DyeColor) Util.getRandom($$9, $$4);
            $$13 = new Variant($$10, $$11, $$12);
        }
        setPackedVariant($$13.getPackedId());
        return $$32;
    }

    public static boolean checkTropicalFishSpawnRules(EntityType<TropicalFish> $$0, LevelAccessor $$1, EntitySpawnReason $$2, BlockPos $$3, RandomSource $$4) {
        return $$1.getFluidState($$3.below()).is(FluidTags.WATER) && $$1.getBlockState($$3.above()).is(Blocks.WATER) && ($$1.getBiome($$3).is(BiomeTags.ALLOWS_TROPICAL_FISH_SPAWNS_AT_ANY_HEIGHT) || WaterAnimal.checkSurfaceWaterAnimalSpawnRules($$0, $$1, $$2, $$3, $$4));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/fish/TropicalFish$TropicalFishGroupData.class */
    static class TropicalFishGroupData extends AbstractSchoolingFish.SchoolSpawnGroupData {
        final Variant variant;

        TropicalFishGroupData(TropicalFish $$0, Variant $$1) {
            super($$0);
            this.variant = $$1;
        }
    }
}
