package net.minecraft.client.renderer.block.model;

import com.mojang.math.Quadrant;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/Variant.class */
public final class Variant extends Record implements BlockModelPart.Unbaked {
    private final Identifier modelLocation;
    private final SimpleModelState modelState;
    public static final MapCodec<Variant> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Identifier.CODEC.fieldOf("model").forGetter((v0) -> {
            return v0.modelLocation();
        }), SimpleModelState.MAP_CODEC.forGetter((v0) -> {
            return v0.modelState();
        })).apply($$0, Variant::new);
    });
    public static final Codec<Variant> CODEC = MAP_CODEC.codec();

    public Variant(Identifier $$0, SimpleModelState $$1) {
        this.modelLocation = $$0;
        this.modelState = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Variant.class), Variant.class, "modelLocation;modelState", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant;->modelLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant;->modelState:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Variant.class), Variant.class, "modelLocation;modelState", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant;->modelLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant;->modelState:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Variant.class, Object.class), Variant.class, "modelLocation;modelState", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant;->modelLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant;->modelState:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier modelLocation() {
        return this.modelLocation;
    }

    public SimpleModelState modelState() {
        return this.modelState;
    }

    public Variant(Identifier $$0) {
        this($$0, SimpleModelState.DEFAULT);
    }

    public Variant withXRot(Quadrant $$0) {
        return withState(this.modelState.withX($$0));
    }

    public Variant withYRot(Quadrant $$0) {
        return withState(this.modelState.withY($$0));
    }

    public Variant withZRot(Quadrant $$0) {
        return withState(this.modelState.withZ($$0));
    }

    public Variant withUvLock(boolean $$0) {
        return withState(this.modelState.withUvLock($$0));
    }

    public Variant withModel(Identifier $$0) {
        return new Variant($$0, this.modelState);
    }

    public Variant withState(SimpleModelState $$0) {
        return new Variant(this.modelLocation, $$0);
    }

    public Variant with(VariantMutator $$0) {
        return (Variant) $$0.apply(this);
    }

    @Override // net.minecraft.client.renderer.block.model.BlockModelPart.Unbaked
    public BlockModelPart bake(ModelBaker $$0) {
        return SimpleModelWrapper.bake($$0, this.modelLocation, this.modelState.asModelState());
    }

    @Override // net.minecraft.client.resources.model.ResolvableModel
    public void resolveDependencies(ResolvableModel.Resolver $$0) {
        $$0.markDependency(this.modelLocation);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/Variant$SimpleModelState.class */
    public static final class SimpleModelState extends Record {
        private final Quadrant x;
        private final Quadrant y;
        private final Quadrant z;
        private final boolean uvLock;
        public static final MapCodec<SimpleModelState> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Quadrant.CODEC.optionalFieldOf("x", Quadrant.R0).forGetter((v0) -> {
                return v0.x();
            }), Quadrant.CODEC.optionalFieldOf("y", Quadrant.R0).forGetter((v0) -> {
                return v0.y();
            }), Quadrant.CODEC.optionalFieldOf("z", Quadrant.R0).forGetter((v0) -> {
                return v0.z();
            }), Codec.BOOL.optionalFieldOf("uvlock", false).forGetter((v0) -> {
                return v0.uvLock();
            })).apply($$0, (v1, v2, v3, v4) -> {
                return new SimpleModelState(v1, v2, v3, v4);
            });
        });
        public static final SimpleModelState DEFAULT = new SimpleModelState(Quadrant.R0, Quadrant.R0, Quadrant.R0, false);

        public SimpleModelState(Quadrant $$0, Quadrant $$1, Quadrant $$2, boolean $$3) {
            this.x = $$0;
            this.y = $$1;
            this.z = $$2;
            this.uvLock = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SimpleModelState.class), SimpleModelState.class, "x;y;z;uvLock", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->x:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->y:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->z:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->uvLock:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SimpleModelState.class), SimpleModelState.class, "x;y;z;uvLock", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->x:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->y:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->z:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->uvLock:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SimpleModelState.class, Object.class), SimpleModelState.class, "x;y;z;uvLock", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->x:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->y:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->z:Lcom/mojang/math/Quadrant;", "FIELD:Lnet/minecraft/client/renderer/block/model/Variant$SimpleModelState;->uvLock:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Quadrant x() {
            return this.x;
        }

        public Quadrant y() {
            return this.y;
        }

        public Quadrant z() {
            return this.z;
        }

        public boolean uvLock() {
            return this.uvLock;
        }

        public ModelState asModelState() {
            BlockModelRotation $$0 = BlockModelRotation.get(Quadrant.fromXYZAngles(this.x, this.y, this.z));
            return this.uvLock ? $$0.withUvLock() : $$0;
        }

        public SimpleModelState withX(Quadrant $$0) {
            return new SimpleModelState($$0, this.y, this.z, this.uvLock);
        }

        public SimpleModelState withY(Quadrant $$0) {
            return new SimpleModelState(this.x, $$0, this.z, this.uvLock);
        }

        public SimpleModelState withZ(Quadrant $$0) {
            return new SimpleModelState(this.x, this.y, $$0, this.uvLock);
        }

        public SimpleModelState withUvLock(boolean $$0) {
            return new SimpleModelState(this.x, this.y, this.z, $$0);
        }
    }
}
