package net.minecraft.world.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.util.ARGB;
import net.minecraft.util.Brightness;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display.class */
public abstract class Display extends Entity {
    public static final int NO_BRIGHTNESS_OVERRIDE = -1;
    private static final int INITIAL_TRANSFORMATION_INTERPOLATION_DURATION = 0;
    private static final int INITIAL_TRANSFORMATION_START_INTERPOLATION = 0;
    private static final int INITIAL_POS_ROT_INTERPOLATION_DURATION = 0;
    private static final float INITIAL_SHADOW_RADIUS = 0.0f;
    private static final float INITIAL_SHADOW_STRENGTH = 1.0f;
    private static final float INITIAL_VIEW_RANGE = 1.0f;
    private static final float INITIAL_WIDTH = 0.0f;
    private static final float INITIAL_HEIGHT = 0.0f;
    private static final int NO_GLOW_COLOR_OVERRIDE = -1;
    public static final String TAG_POS_ROT_INTERPOLATION_DURATION = "teleport_duration";
    public static final String TAG_TRANSFORMATION_INTERPOLATION_DURATION = "interpolation_duration";
    public static final String TAG_TRANSFORMATION_START_INTERPOLATION = "start_interpolation";
    public static final String TAG_TRANSFORMATION = "transformation";
    public static final String TAG_BILLBOARD = "billboard";
    public static final String TAG_BRIGHTNESS = "brightness";
    public static final String TAG_VIEW_RANGE = "view_range";
    public static final String TAG_SHADOW_RADIUS = "shadow_radius";
    public static final String TAG_SHADOW_STRENGTH = "shadow_strength";
    public static final String TAG_WIDTH = "width";
    public static final String TAG_HEIGHT = "height";
    public static final String TAG_GLOW_COLOR_OVERRIDE = "glow_color_override";
    private long interpolationStartClientTick;
    private int interpolationDuration;
    private float lastProgress;
    private AABB cullingBoundingBox;
    private boolean noCulling;
    protected boolean updateRenderState;
    private boolean updateStartTick;
    private boolean updateInterpolationDuration;
    private RenderState renderState;
    private final InterpolationHandler interpolation;
    static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Integer> DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_POS_ROT_INTERPOLATION_DURATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Vector3fc> DATA_TRANSLATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3fc> DATA_SCALE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Quaternionfc> DATA_LEFT_ROTATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.QUATERNION);
    private static final EntityDataAccessor<Quaternionfc> DATA_RIGHT_ROTATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.QUATERNION);
    private static final EntityDataAccessor<Byte> DATA_BILLBOARD_RENDER_CONSTRAINTS_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> DATA_BRIGHTNESS_OVERRIDE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_VIEW_RANGE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_SHADOW_RADIUS_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_SHADOW_STRENGTH_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_WIDTH_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_HEIGHT_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_GLOW_COLOR_OVERRIDE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
    private static final IntSet RENDER_STATE_IDS = IntSet.of(new int[]{DATA_TRANSLATION_ID.id(), DATA_SCALE_ID.id(), DATA_LEFT_ROTATION_ID.id(), DATA_RIGHT_ROTATION_ID.id(), DATA_BILLBOARD_RENDER_CONSTRAINTS_ID.id(), DATA_BRIGHTNESS_OVERRIDE_ID.id(), DATA_SHADOW_RADIUS_ID.id(), DATA_SHADOW_STRENGTH_ID.id()});

    protected abstract void updateRenderSubState(boolean z, float f);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$BillboardConstraints.class */
    public enum BillboardConstraints implements StringRepresentable {
        FIXED((byte) 0, "fixed"),
        VERTICAL((byte) 1, "vertical"),
        HORIZONTAL((byte) 2, "horizontal"),
        CENTER((byte) 3, "center");

        public static final Codec<BillboardConstraints> CODEC = StringRepresentable.fromEnum(BillboardConstraints::values);
        public static final IntFunction<BillboardConstraints> BY_ID = ByIdMap.continuous((v0) -> {
            return v0.getId();
        }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        private final byte id;
        private final String name;

        BillboardConstraints(byte $$0, String $$1) {
            this.name = $$1;
            this.id = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }

        byte getId() {
            return this.id;
        }
    }

    public Display(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
        this.interpolationStartClientTick = -2147483648L;
        this.noCulling = true;
        this.interpolation = new InterpolationHandler(this, 0);
        this.noPhysics = true;
        this.cullingBoundingBox = getBoundingBox();
    }

    @Override // net.minecraft.world.entity.Entity, net.minecraft.network.syncher.SyncedDataHolder
    public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
        super.onSyncedDataUpdated($$0);
        if (DATA_HEIGHT_ID.equals($$0) || DATA_WIDTH_ID.equals($$0)) {
            updateCulling();
        }
        if (DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID.equals($$0)) {
            this.updateStartTick = true;
        }
        if (DATA_POS_ROT_INTERPOLATION_DURATION_ID.equals($$0)) {
            this.interpolation.setInterpolationLength(getPosRotInterpolationDuration());
        }
        if (DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID.equals($$0)) {
            this.updateInterpolationDuration = true;
        }
        if (RENDER_STATE_IDS.contains($$0.id())) {
            this.updateRenderState = true;
        }
    }

    @Override // net.minecraft.world.entity.Entity
    public final boolean hurtServer(ServerLevel $$0, DamageSource $$1, float $$2) {
        return false;
    }

    private static Transformation createTransformation(SynchedEntityData $$0) {
        Vector3fc $$1 = (Vector3fc) $$0.get(DATA_TRANSLATION_ID);
        Quaternionfc $$2 = (Quaternionfc) $$0.get(DATA_LEFT_ROTATION_ID);
        Vector3fc $$3 = (Vector3fc) $$0.get(DATA_SCALE_ID);
        Quaternionfc $$4 = (Quaternionfc) $$0.get(DATA_RIGHT_ROTATION_ID);
        return new Transformation($$1, $$2, $$3, $$4);
    }

    @Override // net.minecraft.world.entity.Entity
    public void tick() {
        Entity $$0 = getVehicle();
        if ($$0 != null && $$0.isRemoved()) {
            stopRiding();
        }
        if (level().isClientSide()) {
            if (this.updateStartTick) {
                this.updateStartTick = false;
                int $$1 = getTransformationInterpolationDelay();
                this.interpolationStartClientTick = this.tickCount + $$1;
            }
            if (this.updateInterpolationDuration) {
                this.updateInterpolationDuration = false;
                this.interpolationDuration = getTransformationInterpolationDuration();
            }
            if (this.updateRenderState) {
                this.updateRenderState = false;
                boolean $$2 = this.interpolationDuration != 0;
                if ($$2 && this.renderState != null) {
                    this.renderState = createInterpolatedRenderState(this.renderState, this.lastProgress);
                } else {
                    this.renderState = createFreshRenderState();
                }
                updateRenderSubState($$2, this.lastProgress);
            }
            this.interpolation.interpolate();
        }
    }

    @Override // net.minecraft.world.entity.Entity
    public InterpolationHandler getInterpolation() {
        return this.interpolation;
    }

    @Override // net.minecraft.world.entity.Entity
    protected void defineSynchedData(SynchedEntityData.Builder $$0) {
        $$0.define(DATA_POS_ROT_INTERPOLATION_DURATION_ID, 0);
        $$0.define(DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID, 0);
        $$0.define(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID, 0);
        $$0.define(DATA_TRANSLATION_ID, new Vector3f());
        $$0.define(DATA_SCALE_ID, new Vector3f(1.0f, 1.0f, 1.0f));
        $$0.define(DATA_RIGHT_ROTATION_ID, new Quaternionf());
        $$0.define(DATA_LEFT_ROTATION_ID, new Quaternionf());
        $$0.define(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID, Byte.valueOf(BillboardConstraints.FIXED.getId()));
        $$0.define(DATA_BRIGHTNESS_OVERRIDE_ID, -1);
        $$0.define(DATA_VIEW_RANGE_ID, Float.valueOf(1.0f));
        $$0.define(DATA_SHADOW_RADIUS_ID, Float.valueOf(0.0f));
        $$0.define(DATA_SHADOW_STRENGTH_ID, Float.valueOf(1.0f));
        $$0.define(DATA_WIDTH_ID, Float.valueOf(0.0f));
        $$0.define(DATA_HEIGHT_ID, Float.valueOf(0.0f));
        $$0.define(DATA_GLOW_COLOR_OVERRIDE_ID, -1);
    }

    @Override // net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        setTransformation((Transformation) $$0.read(TAG_TRANSFORMATION, Transformation.EXTENDED_CODEC).orElse(Transformation.identity()));
        setTransformationInterpolationDuration($$0.getIntOr(TAG_TRANSFORMATION_INTERPOLATION_DURATION, 0));
        setTransformationInterpolationDelay($$0.getIntOr(TAG_TRANSFORMATION_START_INTERPOLATION, 0));
        int $$1 = $$0.getIntOr(TAG_POS_ROT_INTERPOLATION_DURATION, 0);
        setPosRotInterpolationDuration(Mth.clamp($$1, 0, 59));
        setBillboardConstraints((BillboardConstraints) $$0.read(TAG_BILLBOARD, BillboardConstraints.CODEC).orElse(BillboardConstraints.FIXED));
        setViewRange($$0.getFloatOr(TAG_VIEW_RANGE, 1.0f));
        setShadowRadius($$0.getFloatOr(TAG_SHADOW_RADIUS, 0.0f));
        setShadowStrength($$0.getFloatOr(TAG_SHADOW_STRENGTH, 1.0f));
        setWidth($$0.getFloatOr(TAG_WIDTH, 0.0f));
        setHeight($$0.getFloatOr(TAG_HEIGHT, 0.0f));
        setGlowColorOverride($$0.getIntOr(TAG_GLOW_COLOR_OVERRIDE, -1));
        setBrightnessOverride((Brightness) $$0.read(TAG_BRIGHTNESS, Brightness.CODEC).orElse(null));
    }

    private void setTransformation(Transformation $$0) {
        this.entityData.set(DATA_TRANSLATION_ID, $$0.getTranslation());
        this.entityData.set(DATA_LEFT_ROTATION_ID, $$0.getLeftRotation());
        this.entityData.set(DATA_SCALE_ID, $$0.getScale());
        this.entityData.set(DATA_RIGHT_ROTATION_ID, $$0.getRightRotation());
    }

    @Override // net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        $$0.store(TAG_TRANSFORMATION, Transformation.EXTENDED_CODEC, createTransformation(this.entityData));
        $$0.store(TAG_BILLBOARD, BillboardConstraints.CODEC, getBillboardConstraints());
        $$0.putInt(TAG_TRANSFORMATION_INTERPOLATION_DURATION, getTransformationInterpolationDuration());
        $$0.putInt(TAG_POS_ROT_INTERPOLATION_DURATION, getPosRotInterpolationDuration());
        $$0.putFloat(TAG_VIEW_RANGE, getViewRange());
        $$0.putFloat(TAG_SHADOW_RADIUS, getShadowRadius());
        $$0.putFloat(TAG_SHADOW_STRENGTH, getShadowStrength());
        $$0.putFloat(TAG_WIDTH, getWidth());
        $$0.putFloat(TAG_HEIGHT, getHeight());
        $$0.putInt(TAG_GLOW_COLOR_OVERRIDE, getGlowColorOverride());
        $$0.storeNullable(TAG_BRIGHTNESS, Brightness.CODEC, getBrightnessOverride());
    }

    public AABB getBoundingBoxForCulling() {
        return this.cullingBoundingBox;
    }

    public boolean affectedByCulling() {
        return !this.noCulling;
    }

    @Override // net.minecraft.world.entity.Entity
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override // net.minecraft.world.entity.Entity
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    public RenderState renderState() {
        return this.renderState;
    }

    private void setTransformationInterpolationDuration(int $$0) {
        this.entityData.set(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID, Integer.valueOf($$0));
    }

    private int getTransformationInterpolationDuration() {
        return ((Integer) this.entityData.get(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID)).intValue();
    }

    private void setTransformationInterpolationDelay(int $$0) {
        this.entityData.set(DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID, Integer.valueOf($$0), true);
    }

    private int getTransformationInterpolationDelay() {
        return ((Integer) this.entityData.get(DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID)).intValue();
    }

    private void setPosRotInterpolationDuration(int $$0) {
        this.entityData.set(DATA_POS_ROT_INTERPOLATION_DURATION_ID, Integer.valueOf($$0));
    }

    private int getPosRotInterpolationDuration() {
        return ((Integer) this.entityData.get(DATA_POS_ROT_INTERPOLATION_DURATION_ID)).intValue();
    }

    private void setBillboardConstraints(BillboardConstraints $$0) {
        this.entityData.set(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID, Byte.valueOf($$0.getId()));
    }

    private BillboardConstraints getBillboardConstraints() {
        return BillboardConstraints.BY_ID.apply(((Byte) this.entityData.get(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID)).byteValue());
    }

    private void setBrightnessOverride(Brightness $$0) {
        this.entityData.set(DATA_BRIGHTNESS_OVERRIDE_ID, Integer.valueOf($$0 != null ? $$0.pack() : -1));
    }

    private Brightness getBrightnessOverride() {
        int $$0 = ((Integer) this.entityData.get(DATA_BRIGHTNESS_OVERRIDE_ID)).intValue();
        if ($$0 != -1) {
            return Brightness.unpack($$0);
        }
        return null;
    }

    private int getPackedBrightnessOverride() {
        return ((Integer) this.entityData.get(DATA_BRIGHTNESS_OVERRIDE_ID)).intValue();
    }

    private void setViewRange(float $$0) {
        this.entityData.set(DATA_VIEW_RANGE_ID, Float.valueOf($$0));
    }

    private float getViewRange() {
        return ((Float) this.entityData.get(DATA_VIEW_RANGE_ID)).floatValue();
    }

    private void setShadowRadius(float $$0) {
        this.entityData.set(DATA_SHADOW_RADIUS_ID, Float.valueOf($$0));
    }

    private float getShadowRadius() {
        return ((Float) this.entityData.get(DATA_SHADOW_RADIUS_ID)).floatValue();
    }

    private void setShadowStrength(float $$0) {
        this.entityData.set(DATA_SHADOW_STRENGTH_ID, Float.valueOf($$0));
    }

    private float getShadowStrength() {
        return ((Float) this.entityData.get(DATA_SHADOW_STRENGTH_ID)).floatValue();
    }

    private void setWidth(float $$0) {
        this.entityData.set(DATA_WIDTH_ID, Float.valueOf($$0));
    }

    private float getWidth() {
        return ((Float) this.entityData.get(DATA_WIDTH_ID)).floatValue();
    }

    private void setHeight(float $$0) {
        this.entityData.set(DATA_HEIGHT_ID, Float.valueOf($$0));
    }

    private int getGlowColorOverride() {
        return ((Integer) this.entityData.get(DATA_GLOW_COLOR_OVERRIDE_ID)).intValue();
    }

    private void setGlowColorOverride(int $$0) {
        this.entityData.set(DATA_GLOW_COLOR_OVERRIDE_ID, Integer.valueOf($$0));
    }

    public float calculateInterpolationProgress(float $$0) {
        int $$1 = this.interpolationDuration;
        if ($$1 <= 0) {
            return 1.0f;
        }
        float $$2 = ((long) this.tickCount) - this.interpolationStartClientTick;
        float $$3 = $$2 + $$0;
        float $$4 = Mth.clamp(Mth.inverseLerp($$3, 0.0f, $$1), 0.0f, 1.0f);
        this.lastProgress = $$4;
        return $$4;
    }

    private float getHeight() {
        return ((Float) this.entityData.get(DATA_HEIGHT_ID)).floatValue();
    }

    @Override // net.minecraft.world.entity.Entity
    public void setPos(double $$0, double $$1, double $$2) {
        super.setPos($$0, $$1, $$2);
        updateCulling();
    }

    private void updateCulling() {
        float $$0 = getWidth();
        float $$1 = getHeight();
        this.noCulling = $$0 == 0.0f || $$1 == 0.0f;
        float $$2 = $$0 / 2.0f;
        double $$3 = getX();
        double $$4 = getY();
        double $$5 = getZ();
        this.cullingBoundingBox = new AABB($$3 - ((double) $$2), $$4, $$5 - ((double) $$2), $$3 + ((double) $$2), $$4 + ((double) $$1), $$5 + ((double) $$2));
    }

    @Override // net.minecraft.world.entity.Entity
    public boolean shouldRenderAtSqrDistance(double $$0) {
        return $$0 < Mth.square((((double) getViewRange()) * 64.0d) * getViewScale());
    }

    @Override // net.minecraft.world.entity.Entity
    public int getTeamColor() {
        int $$0 = getGlowColorOverride();
        return $$0 != -1 ? $$0 : super.getTeamColor();
    }

    private RenderState createFreshRenderState() {
        return new RenderState(GenericInterpolator.constant(createTransformation(this.entityData)), getBillboardConstraints(), getPackedBrightnessOverride(), FloatInterpolator.constant(getShadowRadius()), FloatInterpolator.constant(getShadowStrength()), getGlowColorOverride());
    }

    private RenderState createInterpolatedRenderState(RenderState $$0, float $$1) {
        Transformation $$2 = $$0.transformation.get($$1);
        float $$3 = $$0.shadowRadius.get($$1);
        float $$4 = $$0.shadowStrength.get($$1);
        return new RenderState(new TransformationInterpolator($$2, createTransformation(this.entityData)), getBillboardConstraints(), getPackedBrightnessOverride(), new LinearFloatInterpolator($$3, getShadowRadius()), new LinearFloatInterpolator($$4, getShadowStrength()), getGlowColorOverride());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$RenderState.class */
    public static final class RenderState extends Record {
        private final GenericInterpolator<Transformation> transformation;
        private final BillboardConstraints billboardConstraints;
        private final int brightnessOverride;
        private final FloatInterpolator shadowRadius;
        private final FloatInterpolator shadowStrength;
        private final int glowColorOverride;

        public RenderState(GenericInterpolator<Transformation> $$0, BillboardConstraints $$1, int $$2, FloatInterpolator $$3, FloatInterpolator $$4, int $$5) {
            this.transformation = $$0;
            this.billboardConstraints = $$1;
            this.brightnessOverride = $$2;
            this.shadowRadius = $$3;
            this.shadowStrength = $$4;
            this.glowColorOverride = $$5;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RenderState.class), RenderState.class, "transformation;billboardConstraints;brightnessOverride;shadowRadius;shadowStrength;glowColorOverride", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->transformation:Lnet/minecraft/world/entity/Display$GenericInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->billboardConstraints:Lnet/minecraft/world/entity/Display$BillboardConstraints;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->brightnessOverride:I", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->shadowRadius:Lnet/minecraft/world/entity/Display$FloatInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->shadowStrength:Lnet/minecraft/world/entity/Display$FloatInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->glowColorOverride:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RenderState.class), RenderState.class, "transformation;billboardConstraints;brightnessOverride;shadowRadius;shadowStrength;glowColorOverride", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->transformation:Lnet/minecraft/world/entity/Display$GenericInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->billboardConstraints:Lnet/minecraft/world/entity/Display$BillboardConstraints;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->brightnessOverride:I", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->shadowRadius:Lnet/minecraft/world/entity/Display$FloatInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->shadowStrength:Lnet/minecraft/world/entity/Display$FloatInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->glowColorOverride:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RenderState.class, Object.class), RenderState.class, "transformation;billboardConstraints;brightnessOverride;shadowRadius;shadowStrength;glowColorOverride", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->transformation:Lnet/minecraft/world/entity/Display$GenericInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->billboardConstraints:Lnet/minecraft/world/entity/Display$BillboardConstraints;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->brightnessOverride:I", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->shadowRadius:Lnet/minecraft/world/entity/Display$FloatInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->shadowStrength:Lnet/minecraft/world/entity/Display$FloatInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$RenderState;->glowColorOverride:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public GenericInterpolator<Transformation> transformation() {
            return this.transformation;
        }

        public BillboardConstraints billboardConstraints() {
            return this.billboardConstraints;
        }

        public int brightnessOverride() {
            return this.brightnessOverride;
        }

        public FloatInterpolator shadowRadius() {
            return this.shadowRadius;
        }

        public FloatInterpolator shadowStrength() {
            return this.shadowStrength;
        }

        public int glowColorOverride() {
            return this.glowColorOverride;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$ItemDisplay.class */
    public static class ItemDisplay extends Display {
        private static final String TAG_ITEM = "item";
        private static final String TAG_ITEM_DISPLAY = "item_display";
        private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK_ID = SynchedEntityData.defineId(ItemDisplay.class, EntityDataSerializers.ITEM_STACK);
        private static final EntityDataAccessor<Byte> DATA_ITEM_DISPLAY_ID = SynchedEntityData.defineId(ItemDisplay.class, EntityDataSerializers.BYTE);
        private final SlotAccess slot;
        private ItemRenderState itemRenderState;

        public ItemDisplay(EntityType<?> $$0, Level $$1) {
            super($$0, $$1);
            this.slot = SlotAccess.of(this::getItemStack, this::setItemStack);
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void defineSynchedData(SynchedEntityData.Builder $$0) {
            super.defineSynchedData($$0);
            $$0.define(DATA_ITEM_STACK_ID, ItemStack.EMPTY);
            $$0.define(DATA_ITEM_DISPLAY_ID, Byte.valueOf(ItemDisplayContext.NONE.getId()));
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity, net.minecraft.network.syncher.SyncedDataHolder
        public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
            super.onSyncedDataUpdated($$0);
            if (DATA_ITEM_STACK_ID.equals($$0) || DATA_ITEM_DISPLAY_ID.equals($$0)) {
                this.updateRenderState = true;
            }
        }

        private ItemStack getItemStack() {
            return (ItemStack) this.entityData.get(DATA_ITEM_STACK_ID);
        }

        private void setItemStack(ItemStack $$0) {
            this.entityData.set(DATA_ITEM_STACK_ID, $$0);
        }

        private void setItemTransform(ItemDisplayContext $$0) {
            this.entityData.set(DATA_ITEM_DISPLAY_ID, Byte.valueOf($$0.getId()));
        }

        private ItemDisplayContext getItemTransform() {
            return ItemDisplayContext.BY_ID.apply(((Byte) this.entityData.get(DATA_ITEM_DISPLAY_ID)).byteValue());
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void readAdditionalSaveData(ValueInput $$0) {
            super.readAdditionalSaveData($$0);
            setItemStack((ItemStack) $$0.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY));
            setItemTransform((ItemDisplayContext) $$0.read(TAG_ITEM_DISPLAY, ItemDisplayContext.CODEC).orElse(ItemDisplayContext.NONE));
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void addAdditionalSaveData(ValueOutput $$0) {
            super.addAdditionalSaveData($$0);
            ItemStack $$1 = getItemStack();
            if (!$$1.isEmpty()) {
                $$0.store("item", ItemStack.CODEC, $$1);
            }
            $$0.store(TAG_ITEM_DISPLAY, ItemDisplayContext.CODEC, getItemTransform());
        }

        @Override // net.minecraft.world.entity.Entity, net.minecraft.world.entity.SlotProvider
        public SlotAccess getSlot(int $$0) {
            if ($$0 == 0) {
                return this.slot;
            }
            return null;
        }

        public ItemRenderState itemRenderState() {
            return this.itemRenderState;
        }

        @Override // net.minecraft.world.entity.Display
        protected void updateRenderSubState(boolean $$0, float $$1) {
            ItemStack $$2 = getItemStack();
            $$2.setEntityRepresentation(this);
            this.itemRenderState = new ItemRenderState($$2, getItemTransform());
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$ItemDisplay$ItemRenderState.class */
        public static final class ItemRenderState extends Record {
            private final ItemStack itemStack;
            private final ItemDisplayContext itemTransform;

            public ItemRenderState(ItemStack $$0, ItemDisplayContext $$1) {
                this.itemStack = $$0;
                this.itemTransform = $$1;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ItemRenderState.class), ItemRenderState.class, "itemStack;itemTransform", "FIELD:Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;->itemStack:Lnet/minecraft/world/item/ItemStack;", "FIELD:Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;->itemTransform:Lnet/minecraft/world/item/ItemDisplayContext;").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ItemRenderState.class), ItemRenderState.class, "itemStack;itemTransform", "FIELD:Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;->itemStack:Lnet/minecraft/world/item/ItemStack;", "FIELD:Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;->itemTransform:Lnet/minecraft/world/item/ItemDisplayContext;").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object $$0) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ItemRenderState.class, Object.class), ItemRenderState.class, "itemStack;itemTransform", "FIELD:Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;->itemStack:Lnet/minecraft/world/item/ItemStack;", "FIELD:Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;->itemTransform:Lnet/minecraft/world/item/ItemDisplayContext;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
            }

            public ItemStack itemStack() {
                return this.itemStack;
            }

            public ItemDisplayContext itemTransform() {
                return this.itemTransform;
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$BlockDisplay.class */
    public static class BlockDisplay extends Display {
        public static final String TAG_BLOCK_STATE = "block_state";
        private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(BlockDisplay.class, EntityDataSerializers.BLOCK_STATE);
        private BlockRenderState blockRenderState;

        public BlockDisplay(EntityType<?> $$0, Level $$1) {
            super($$0, $$1);
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void defineSynchedData(SynchedEntityData.Builder $$0) {
            super.defineSynchedData($$0);
            $$0.define(DATA_BLOCK_STATE_ID, Blocks.AIR.defaultBlockState());
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity, net.minecraft.network.syncher.SyncedDataHolder
        public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
            super.onSyncedDataUpdated($$0);
            if ($$0.equals(DATA_BLOCK_STATE_ID)) {
                this.updateRenderState = true;
            }
        }

        private BlockState getBlockState() {
            return (BlockState) this.entityData.get(DATA_BLOCK_STATE_ID);
        }

        private void setBlockState(BlockState $$0) {
            this.entityData.set(DATA_BLOCK_STATE_ID, $$0);
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void readAdditionalSaveData(ValueInput $$0) {
            super.readAdditionalSaveData($$0);
            setBlockState((BlockState) $$0.read(TAG_BLOCK_STATE, BlockState.CODEC).orElse(Blocks.AIR.defaultBlockState()));
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void addAdditionalSaveData(ValueOutput $$0) {
            super.addAdditionalSaveData($$0);
            $$0.store(TAG_BLOCK_STATE, BlockState.CODEC, getBlockState());
        }

        public BlockRenderState blockRenderState() {
            return this.blockRenderState;
        }

        @Override // net.minecraft.world.entity.Display
        protected void updateRenderSubState(boolean $$0, float $$1) {
            this.blockRenderState = new BlockRenderState(getBlockState());
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$BlockDisplay$BlockRenderState.class */
        public static final class BlockRenderState extends Record {
            private final BlockState blockState;

            public BlockRenderState(BlockState $$0) {
                this.blockState = $$0;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockRenderState.class), BlockRenderState.class, "blockState", "FIELD:Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;->blockState:Lnet/minecraft/world/level/block/state/BlockState;").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockRenderState.class), BlockRenderState.class, "blockState", "FIELD:Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;->blockState:Lnet/minecraft/world/level/block/state/BlockState;").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object $$0) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockRenderState.class, Object.class), BlockRenderState.class, "blockState", "FIELD:Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;->blockState:Lnet/minecraft/world/level/block/state/BlockState;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
            }

            public BlockState blockState() {
                return this.blockState;
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$TextDisplay.class */
    public static class TextDisplay extends Display {
        public static final String TAG_TEXT = "text";
        private static final String TAG_LINE_WIDTH = "line_width";
        private static final String TAG_TEXT_OPACITY = "text_opacity";
        private static final String TAG_BACKGROUND_COLOR = "background";
        private static final String TAG_SHADOW = "shadow";
        private static final String TAG_SEE_THROUGH = "see_through";
        private static final String TAG_USE_DEFAULT_BACKGROUND = "default_background";
        private static final String TAG_ALIGNMENT = "alignment";
        public static final byte FLAG_SHADOW = 1;
        public static final byte FLAG_SEE_THROUGH = 2;
        public static final byte FLAG_USE_DEFAULT_BACKGROUND = 4;
        public static final byte FLAG_ALIGN_LEFT = 8;
        public static final byte FLAG_ALIGN_RIGHT = 16;
        private static final byte INITIAL_TEXT_OPACITY = -1;
        public static final int INITIAL_BACKGROUND = 1073741824;
        private static final int INITIAL_LINE_WIDTH = 200;
        private static final EntityDataAccessor<Component> DATA_TEXT_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.COMPONENT);
        private static final EntityDataAccessor<Integer> DATA_LINE_WIDTH_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.INT);
        private static final EntityDataAccessor<Integer> DATA_BACKGROUND_COLOR_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.INT);
        private static final EntityDataAccessor<Byte> DATA_TEXT_OPACITY_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.BYTE);
        private static final EntityDataAccessor<Byte> DATA_STYLE_FLAGS_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.BYTE);
        private static final IntSet TEXT_RENDER_STATE_IDS = IntSet.of(new int[]{DATA_TEXT_ID.id(), DATA_LINE_WIDTH_ID.id(), DATA_BACKGROUND_COLOR_ID.id(), DATA_TEXT_OPACITY_ID.id(), DATA_STYLE_FLAGS_ID.id()});
        private CachedInfo clientDisplayCache;
        private TextRenderState textRenderState;

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$TextDisplay$LineSplitter.class */
        @FunctionalInterface
        public interface LineSplitter {
            CachedInfo split(Component component, int i);
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$TextDisplay$Align.class */
        public enum Align implements StringRepresentable {
            CENTER("center"),
            LEFT("left"),
            RIGHT("right");

            public static final Codec<Align> CODEC = StringRepresentable.fromEnum(Align::values);
            private final String name;

            Align(String $$0) {
                this.name = $$0;
            }

            @Override // net.minecraft.util.StringRepresentable
            public String getSerializedName() {
                return this.name;
            }
        }

        public TextDisplay(EntityType<?> $$0, Level $$1) {
            super($$0, $$1);
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void defineSynchedData(SynchedEntityData.Builder $$0) {
            super.defineSynchedData($$0);
            $$0.define(DATA_TEXT_ID, Component.empty());
            $$0.define(DATA_LINE_WIDTH_ID, 200);
            $$0.define(DATA_BACKGROUND_COLOR_ID, Integer.valueOf(INITIAL_BACKGROUND));
            $$0.define(DATA_TEXT_OPACITY_ID, (byte) -1);
            $$0.define(DATA_STYLE_FLAGS_ID, (byte) 0);
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity, net.minecraft.network.syncher.SyncedDataHolder
        public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
            super.onSyncedDataUpdated($$0);
            if (TEXT_RENDER_STATE_IDS.contains($$0.id())) {
                this.updateRenderState = true;
            }
        }

        private Component getText() {
            return (Component) this.entityData.get(DATA_TEXT_ID);
        }

        private void setText(Component $$0) {
            this.entityData.set(DATA_TEXT_ID, $$0);
        }

        private int getLineWidth() {
            return ((Integer) this.entityData.get(DATA_LINE_WIDTH_ID)).intValue();
        }

        private void setLineWidth(int $$0) {
            this.entityData.set(DATA_LINE_WIDTH_ID, Integer.valueOf($$0));
        }

        private byte getTextOpacity() {
            return ((Byte) this.entityData.get(DATA_TEXT_OPACITY_ID)).byteValue();
        }

        private void setTextOpacity(byte $$0) {
            this.entityData.set(DATA_TEXT_OPACITY_ID, Byte.valueOf($$0));
        }

        private int getBackgroundColor() {
            return ((Integer) this.entityData.get(DATA_BACKGROUND_COLOR_ID)).intValue();
        }

        private void setBackgroundColor(int $$0) {
            this.entityData.set(DATA_BACKGROUND_COLOR_ID, Integer.valueOf($$0));
        }

        private byte getFlags() {
            return ((Byte) this.entityData.get(DATA_STYLE_FLAGS_ID)).byteValue();
        }

        private void setFlags(byte $$0) {
            this.entityData.set(DATA_STYLE_FLAGS_ID, Byte.valueOf($$0));
        }

        private static byte loadFlag(byte $$0, ValueInput $$1, String $$2, byte $$3) {
            if ($$1.getBooleanOr($$2, false)) {
                return (byte) ($$0 | $$3);
            }
            return $$0;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void readAdditionalSaveData(ValueInput $$0) throws MatchException, CommandSyntaxException {
            byte b;
            super.readAdditionalSaveData($$0);
            setLineWidth($$0.getIntOr(TAG_LINE_WIDTH, 200));
            setTextOpacity($$0.getByteOr(TAG_TEXT_OPACITY, (byte) -1));
            setBackgroundColor($$0.getIntOr(TAG_BACKGROUND_COLOR, INITIAL_BACKGROUND));
            byte $$1 = loadFlag(loadFlag(loadFlag((byte) 0, $$0, TAG_SHADOW, (byte) 1), $$0, TAG_SEE_THROUGH, (byte) 2), $$0, TAG_USE_DEFAULT_BACKGROUND, (byte) 4);
            Optional<Align> $$2 = $$0.read(TAG_ALIGNMENT, Align.CODEC);
            if ($$2.isPresent()) {
                switch ($$2.get()) {
                    case CENTER:
                        b = $$1;
                        break;
                    case LEFT:
                        b = (byte) ($$1 | 8);
                        break;
                    case RIGHT:
                        b = (byte) ($$1 | 16);
                        break;
                    default:
                        throw new MatchException((String) null, (Throwable) null);
                }
                $$1 = b;
            }
            setFlags($$1);
            Optional<Component> $$3 = $$0.read(TAG_TEXT, ComponentSerialization.CODEC);
            if ($$3.isPresent()) {
                try {
                    Level level = level();
                    if (level instanceof ServerLevel) {
                        ServerLevel $$4 = (ServerLevel) level;
                        CommandSourceStack $$5 = createCommandSourceStackForNameResolution($$4).withPermission(LevelBasedPermissionSet.GAMEMASTER);
                        Component $$6 = ComponentUtils.updateForEntity($$5, $$3.get(), this, 0);
                        setText($$6);
                    } else {
                        setText(Component.empty());
                    }
                } catch (Exception $$7) {
                    Display.LOGGER.warn("Failed to parse display entity text {}", $$3, $$7);
                }
            }
        }

        private static void storeFlag(byte $$0, ValueOutput $$1, String $$2, byte $$3) {
            $$1.putBoolean($$2, ($$0 & $$3) != 0);
        }

        @Override // net.minecraft.world.entity.Display, net.minecraft.world.entity.Entity
        protected void addAdditionalSaveData(ValueOutput $$0) {
            super.addAdditionalSaveData($$0);
            $$0.store(TAG_TEXT, ComponentSerialization.CODEC, getText());
            $$0.putInt(TAG_LINE_WIDTH, getLineWidth());
            $$0.putInt(TAG_BACKGROUND_COLOR, getBackgroundColor());
            $$0.putByte(TAG_TEXT_OPACITY, getTextOpacity());
            byte $$1 = getFlags();
            storeFlag($$1, $$0, TAG_SHADOW, (byte) 1);
            storeFlag($$1, $$0, TAG_SEE_THROUGH, (byte) 2);
            storeFlag($$1, $$0, TAG_USE_DEFAULT_BACKGROUND, (byte) 4);
            $$0.store(TAG_ALIGNMENT, Align.CODEC, getAlign($$1));
        }

        @Override // net.minecraft.world.entity.Display
        protected void updateRenderSubState(boolean $$0, float $$1) {
            if ($$0 && this.textRenderState != null) {
                this.textRenderState = createInterpolatedTextRenderState(this.textRenderState, $$1);
            } else {
                this.textRenderState = createFreshTextRenderState();
            }
            this.clientDisplayCache = null;
        }

        public TextRenderState textRenderState() {
            return this.textRenderState;
        }

        private TextRenderState createFreshTextRenderState() {
            return new TextRenderState(getText(), getLineWidth(), IntInterpolator.constant(getTextOpacity()), IntInterpolator.constant(getBackgroundColor()), getFlags());
        }

        private TextRenderState createInterpolatedTextRenderState(TextRenderState $$0, float $$1) {
            int $$2 = $$0.backgroundColor.get($$1);
            int $$3 = $$0.textOpacity.get($$1);
            return new TextRenderState(getText(), getLineWidth(), new LinearIntInterpolator($$3, getTextOpacity()), new ColorInterpolator($$2, getBackgroundColor()), getFlags());
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$TextDisplay$CachedLine.class */
        public static final class CachedLine extends Record {
            private final FormattedCharSequence contents;
            private final int width;

            public CachedLine(FormattedCharSequence $$0, int $$1) {
                this.contents = $$0;
                this.width = $$1;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CachedLine.class), CachedLine.class, "contents;width", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;->contents:Lnet/minecraft/util/FormattedCharSequence;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;->width:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CachedLine.class), CachedLine.class, "contents;width", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;->contents:Lnet/minecraft/util/FormattedCharSequence;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;->width:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object $$0) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CachedLine.class, Object.class), CachedLine.class, "contents;width", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;->contents:Lnet/minecraft/util/FormattedCharSequence;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;->width:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
            }

            public FormattedCharSequence contents() {
                return this.contents;
            }

            public int width() {
                return this.width;
            }
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$TextDisplay$CachedInfo.class */
        public static final class CachedInfo extends Record {
            private final List<CachedLine> lines;
            private final int width;

            public CachedInfo(List<CachedLine> $$0, int $$1) {
                this.lines = $$0;
                this.width = $$1;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CachedInfo.class), CachedInfo.class, "lines;width", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;->lines:Ljava/util/List;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;->width:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CachedInfo.class), CachedInfo.class, "lines;width", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;->lines:Ljava/util/List;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;->width:I").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object $$0) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CachedInfo.class, Object.class), CachedInfo.class, "lines;width", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;->lines:Ljava/util/List;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;->width:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
            }

            public List<CachedLine> lines() {
                return this.lines;
            }

            public int width() {
                return this.width;
            }
        }

        public CachedInfo cacheDisplay(LineSplitter $$0) {
            if (this.clientDisplayCache == null) {
                if (this.textRenderState != null) {
                    this.clientDisplayCache = $$0.split(this.textRenderState.text(), this.textRenderState.lineWidth());
                } else {
                    this.clientDisplayCache = new CachedInfo(List.of(), 0);
                }
            }
            return this.clientDisplayCache;
        }

        public static Align getAlign(byte $$0) {
            if (($$0 & 8) != 0) {
                return Align.LEFT;
            }
            if (($$0 & 16) != 0) {
                return Align.RIGHT;
            }
            return Align.CENTER;
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$TextDisplay$TextRenderState.class */
        public static final class TextRenderState extends Record {
            private final Component text;
            private final int lineWidth;
            private final IntInterpolator textOpacity;
            private final IntInterpolator backgroundColor;
            private final byte flags;

            public TextRenderState(Component $$0, int $$1, IntInterpolator $$2, IntInterpolator $$3, byte $$4) {
                this.text = $$0;
                this.lineWidth = $$1;
                this.textOpacity = $$2;
                this.backgroundColor = $$3;
                this.flags = $$4;
            }

            @Override // java.lang.Record
            public final String toString() {
                return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextRenderState.class), TextRenderState.class, "text;lineWidth;textOpacity;backgroundColor;flags", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->lineWidth:I", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->textOpacity:Lnet/minecraft/world/entity/Display$IntInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->backgroundColor:Lnet/minecraft/world/entity/Display$IntInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->flags:B").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextRenderState.class), TextRenderState.class, "text;lineWidth;textOpacity;backgroundColor;flags", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->lineWidth:I", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->textOpacity:Lnet/minecraft/world/entity/Display$IntInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->backgroundColor:Lnet/minecraft/world/entity/Display$IntInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->flags:B").dynamicInvoker().invoke(this) /* invoke-custom */;
            }

            @Override // java.lang.Record
            public final boolean equals(Object $$0) {
                return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextRenderState.class, Object.class), TextRenderState.class, "text;lineWidth;textOpacity;backgroundColor;flags", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->lineWidth:I", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->textOpacity:Lnet/minecraft/world/entity/Display$IntInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->backgroundColor:Lnet/minecraft/world/entity/Display$IntInterpolator;", "FIELD:Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;->flags:B").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
            }

            public Component text() {
                return this.text;
            }

            public int lineWidth() {
                return this.lineWidth;
            }

            public IntInterpolator textOpacity() {
                return this.textOpacity;
            }

            public IntInterpolator backgroundColor() {
                return this.backgroundColor;
            }

            public byte flags() {
                return this.flags;
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$GenericInterpolator.class */
    @FunctionalInterface
    public interface GenericInterpolator<T> {
        T get(float f);

        static <T> GenericInterpolator<T> constant(T $$0) {
            return $$1 -> {
                return $$0;
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$TransformationInterpolator.class */
    static final class TransformationInterpolator extends Record implements GenericInterpolator<Transformation> {
        private final Transformation previous;
        private final Transformation current;

        TransformationInterpolator(Transformation $$0, Transformation $$1) {
            this.previous = $$0;
            this.current = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TransformationInterpolator.class), TransformationInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$TransformationInterpolator;->previous:Lcom/mojang/math/Transformation;", "FIELD:Lnet/minecraft/world/entity/Display$TransformationInterpolator;->current:Lcom/mojang/math/Transformation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TransformationInterpolator.class), TransformationInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$TransformationInterpolator;->previous:Lcom/mojang/math/Transformation;", "FIELD:Lnet/minecraft/world/entity/Display$TransformationInterpolator;->current:Lcom/mojang/math/Transformation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TransformationInterpolator.class, Object.class), TransformationInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$TransformationInterpolator;->previous:Lcom/mojang/math/Transformation;", "FIELD:Lnet/minecraft/world/entity/Display$TransformationInterpolator;->current:Lcom/mojang/math/Transformation;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Transformation previous() {
            return this.previous;
        }

        public Transformation current() {
            return this.current;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.minecraft.world.entity.Display.GenericInterpolator
        public Transformation get(float $$0) {
            if ($$0 >= 1.0d) {
                return this.current;
            }
            return this.previous.slerp(this.current, $$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$IntInterpolator.class */
    @FunctionalInterface
    public interface IntInterpolator {
        int get(float f);

        static IntInterpolator constant(int $$0) {
            return $$1 -> {
                return $$0;
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$LinearIntInterpolator.class */
    static final class LinearIntInterpolator extends Record implements IntInterpolator {
        private final int previous;
        private final int current;

        LinearIntInterpolator(int $$0, int $$1) {
            this.previous = $$0;
            this.current = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LinearIntInterpolator.class), LinearIntInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$LinearIntInterpolator;->previous:I", "FIELD:Lnet/minecraft/world/entity/Display$LinearIntInterpolator;->current:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LinearIntInterpolator.class), LinearIntInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$LinearIntInterpolator;->previous:I", "FIELD:Lnet/minecraft/world/entity/Display$LinearIntInterpolator;->current:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LinearIntInterpolator.class, Object.class), LinearIntInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$LinearIntInterpolator;->previous:I", "FIELD:Lnet/minecraft/world/entity/Display$LinearIntInterpolator;->current:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int previous() {
            return this.previous;
        }

        public int current() {
            return this.current;
        }

        @Override // net.minecraft.world.entity.Display.IntInterpolator
        public int get(float $$0) {
            return Mth.lerpInt($$0, this.previous, this.current);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$ColorInterpolator.class */
    static final class ColorInterpolator extends Record implements IntInterpolator {
        private final int previous;
        private final int current;

        ColorInterpolator(int $$0, int $$1) {
            this.previous = $$0;
            this.current = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ColorInterpolator.class), ColorInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$ColorInterpolator;->previous:I", "FIELD:Lnet/minecraft/world/entity/Display$ColorInterpolator;->current:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ColorInterpolator.class), ColorInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$ColorInterpolator;->previous:I", "FIELD:Lnet/minecraft/world/entity/Display$ColorInterpolator;->current:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ColorInterpolator.class, Object.class), ColorInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$ColorInterpolator;->previous:I", "FIELD:Lnet/minecraft/world/entity/Display$ColorInterpolator;->current:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int previous() {
            return this.previous;
        }

        public int current() {
            return this.current;
        }

        @Override // net.minecraft.world.entity.Display.IntInterpolator
        public int get(float $$0) {
            return ARGB.srgbLerp($$0, this.previous, this.current);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$FloatInterpolator.class */
    @FunctionalInterface
    public interface FloatInterpolator {
        float get(float f);

        static FloatInterpolator constant(float $$0) {
            return $$1 -> {
                return $$0;
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Display$LinearFloatInterpolator.class */
    static final class LinearFloatInterpolator extends Record implements FloatInterpolator {
        private final float previous;
        private final float current;

        LinearFloatInterpolator(float $$0, float $$1) {
            this.previous = $$0;
            this.current = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LinearFloatInterpolator.class), LinearFloatInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;->previous:F", "FIELD:Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;->current:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LinearFloatInterpolator.class), LinearFloatInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;->previous:F", "FIELD:Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;->current:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LinearFloatInterpolator.class, Object.class), LinearFloatInterpolator.class, "previous;current", "FIELD:Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;->previous:F", "FIELD:Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;->current:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float previous() {
            return this.previous;
        }

        public float current() {
            return this.current;
        }

        @Override // net.minecraft.world.entity.Display.FloatInterpolator
        public float get(float $$0) {
            return Mth.lerp($$0, this.previous, this.current);
        }
    }
}
