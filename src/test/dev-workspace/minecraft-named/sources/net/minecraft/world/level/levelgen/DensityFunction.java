package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/DensityFunction.class */
public interface DensityFunction {
    public static final Codec<DensityFunction> DIRECT_CODEC = DensityFunctions.DIRECT_CODEC;
    public static final Codec<Holder<DensityFunction>> CODEC = RegistryFileCodec.create(Registries.DENSITY_FUNCTION, DIRECT_CODEC);
    public static final Codec<DensityFunction> HOLDER_HELPER_CODEC = CODEC.xmap(DensityFunctions.HolderHolder::new, $$0 -> {
        if ($$0 instanceof DensityFunctions.HolderHolder) {
            DensityFunctions.HolderHolder $$1 = (DensityFunctions.HolderHolder) $$0;
            return $$1.function();
        }
        return new Holder.Direct($$0);
    });

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/DensityFunction$ContextProvider.class */
    public interface ContextProvider {
        FunctionContext forIndex(int i);

        void fillAllDirectly(double[] dArr, DensityFunction densityFunction);
    }

    double compute(FunctionContext functionContext);

    void fillArray(double[] dArr, ContextProvider contextProvider);

    DensityFunction mapAll(Visitor visitor);

    double minValue();

    double maxValue();

    KeyDispatchDataCodec<? extends DensityFunction> codec();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/DensityFunction$NoiseHolder.class */
    public static final class NoiseHolder extends Record {
        private final Holder<NormalNoise.NoiseParameters> noiseData;
        private final NormalNoise noise;
        public static final Codec<NoiseHolder> CODEC = NormalNoise.NoiseParameters.CODEC.xmap($$0 -> {
            return new NoiseHolder($$0, null);
        }, (v0) -> {
            return v0.noiseData();
        });

        public NoiseHolder(Holder<NormalNoise.NoiseParameters> $$0, NormalNoise $$1) {
            this.noiseData = $$0;
            this.noise = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NoiseHolder.class), NoiseHolder.class, "noiseData;noise", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;->noiseData:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;->noise:Lnet/minecraft/world/level/levelgen/synth/NormalNoise;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NoiseHolder.class), NoiseHolder.class, "noiseData;noise", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;->noiseData:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;->noise:Lnet/minecraft/world/level/levelgen/synth/NormalNoise;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NoiseHolder.class, Object.class), NoiseHolder.class, "noiseData;noise", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;->noiseData:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;->noise:Lnet/minecraft/world/level/levelgen/synth/NormalNoise;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<NormalNoise.NoiseParameters> noiseData() {
            return this.noiseData;
        }

        public NormalNoise noise() {
            return this.noise;
        }

        public NoiseHolder(Holder<NormalNoise.NoiseParameters> $$0) {
            this($$0, null);
        }

        public double getValue(double $$0, double $$1, double $$2) {
            return this.noise == null ? Density.SURFACE : this.noise.getValue($$0, $$1, $$2);
        }

        public double maxValue() {
            if (this.noise == null) {
                return 2.0d;
            }
            return this.noise.maxValue();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/DensityFunction$Visitor.class */
    public interface Visitor {
        DensityFunction apply(DensityFunction densityFunction);

        default NoiseHolder visitNoise(NoiseHolder $$0) {
            return $$0;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/DensityFunction$SimpleFunction.class */
    public interface SimpleFunction extends DensityFunction {
        @Override // net.minecraft.world.level.levelgen.DensityFunction
        default void fillArray(double[] $$0, ContextProvider $$1) {
            $$1.fillAllDirectly($$0, this);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        default DensityFunction mapAll(Visitor $$0) {
            return $$0.apply(this);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/DensityFunction$FunctionContext.class */
    public interface FunctionContext {
        int blockX();

        int blockY();

        int blockZ();

        default Blender getBlender() {
            return Blender.empty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/DensityFunction$SinglePointContext.class */
    public static final class SinglePointContext extends Record implements FunctionContext {
        private final int blockX;
        private final int blockY;
        private final int blockZ;

        public SinglePointContext(int $$0, int $$1, int $$2) {
            this.blockX = $$0;
            this.blockY = $$1;
            this.blockZ = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SinglePointContext.class), SinglePointContext.class, "blockX;blockY;blockZ", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockX:I", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockY:I", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockZ:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SinglePointContext.class), SinglePointContext.class, "blockX;blockY;blockZ", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockX:I", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockY:I", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockZ:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SinglePointContext.class, Object.class), SinglePointContext.class, "blockX;blockY;blockZ", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockX:I", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockY:I", "FIELD:Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;->blockZ:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction.FunctionContext
        public int blockX() {
            return this.blockX;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction.FunctionContext
        public int blockY() {
            return this.blockY;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction.FunctionContext
        public int blockZ() {
            return this.blockZ;
        }
    }

    default DensityFunction clamp(double $$0, double $$1) {
        return new DensityFunctions.Clamp(this, $$0, $$1);
    }

    default DensityFunction abs() {
        return DensityFunctions.map(this, DensityFunctions.Mapped.Type.ABS);
    }

    default DensityFunction square() {
        return DensityFunctions.map(this, DensityFunctions.Mapped.Type.SQUARE);
    }

    default DensityFunction cube() {
        return DensityFunctions.map(this, DensityFunctions.Mapped.Type.CUBE);
    }

    default DensityFunction halfNegative() {
        return DensityFunctions.map(this, DensityFunctions.Mapped.Type.HALF_NEGATIVE);
    }

    default DensityFunction quarterNegative() {
        return DensityFunctions.map(this, DensityFunctions.Mapped.Type.QUARTER_NEGATIVE);
    }

    default DensityFunction invert() {
        return DensityFunctions.map(this, DensityFunctions.Mapped.Type.INVERT);
    }

    default DensityFunction squeeze() {
        return DensityFunctions.map(this, DensityFunctions.Mapped.Type.SQUEEZE);
    }
}
