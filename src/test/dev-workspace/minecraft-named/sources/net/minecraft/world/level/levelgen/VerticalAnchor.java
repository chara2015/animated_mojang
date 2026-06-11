package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.world.level.dimension.DimensionType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/VerticalAnchor.class */
public interface VerticalAnchor {
    public static final Codec<VerticalAnchor> CODEC = Codec.xor(Absolute.CODEC, Codec.xor(AboveBottom.CODEC, BelowTop.CODEC)).xmap(VerticalAnchor::merge, VerticalAnchor::split);
    public static final VerticalAnchor BOTTOM = aboveBottom(0);
    public static final VerticalAnchor TOP = belowTop(0);

    int resolveY(WorldGenerationContext worldGenerationContext);

    static VerticalAnchor absolute(int $$0) {
        return new Absolute($$0);
    }

    static VerticalAnchor aboveBottom(int $$0) {
        return new AboveBottom($$0);
    }

    static VerticalAnchor belowTop(int $$0) {
        return new BelowTop($$0);
    }

    static VerticalAnchor bottom() {
        return BOTTOM;
    }

    static VerticalAnchor top() {
        return TOP;
    }

    private static VerticalAnchor merge(Either<Absolute, Either<AboveBottom, BelowTop>> $$0) {
        return (VerticalAnchor) $$0.map(Function.identity(), Either::unwrap);
    }

    private static Either<Absolute, Either<AboveBottom, BelowTop>> split(VerticalAnchor $$0) {
        if ($$0 instanceof Absolute) {
            return Either.left((Absolute) $$0);
        }
        return Either.right($$0 instanceof AboveBottom ? Either.left((AboveBottom) $$0) : Either.right((BelowTop) $$0));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/VerticalAnchor$Absolute.class */
    public static final class Absolute extends Record implements VerticalAnchor {
        private final int y;
        public static final Codec<Absolute> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("absolute").xmap((v1) -> {
            return new Absolute(v1);
        }, (v0) -> {
            return v0.y();
        }).codec();

        public Absolute(int $$0) {
            this.y = $$0;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Absolute.class), Absolute.class, "y", "FIELD:Lnet/minecraft/world/level/levelgen/VerticalAnchor$Absolute;->y:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Absolute.class, Object.class), Absolute.class, "y", "FIELD:Lnet/minecraft/world/level/levelgen/VerticalAnchor$Absolute;->y:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int y() {
            return this.y;
        }

        @Override // net.minecraft.world.level.levelgen.VerticalAnchor
        public int resolveY(WorldGenerationContext $$0) {
            return this.y;
        }

        @Override // java.lang.Record
        public String toString() {
            return this.y + " absolute";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/VerticalAnchor$AboveBottom.class */
    public static final class AboveBottom extends Record implements VerticalAnchor {
        private final int offset;
        public static final Codec<AboveBottom> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("above_bottom").xmap((v1) -> {
            return new AboveBottom(v1);
        }, (v0) -> {
            return v0.offset();
        }).codec();

        public AboveBottom(int $$0) {
            this.offset = $$0;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AboveBottom.class), AboveBottom.class, "offset", "FIELD:Lnet/minecraft/world/level/levelgen/VerticalAnchor$AboveBottom;->offset:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AboveBottom.class, Object.class), AboveBottom.class, "offset", "FIELD:Lnet/minecraft/world/level/levelgen/VerticalAnchor$AboveBottom;->offset:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int offset() {
            return this.offset;
        }

        @Override // net.minecraft.world.level.levelgen.VerticalAnchor
        public int resolveY(WorldGenerationContext $$0) {
            return $$0.getMinGenY() + this.offset;
        }

        @Override // java.lang.Record
        public String toString() {
            return this.offset + " above bottom";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/VerticalAnchor$BelowTop.class */
    public static final class BelowTop extends Record implements VerticalAnchor {
        private final int offset;
        public static final Codec<BelowTop> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("below_top").xmap((v1) -> {
            return new BelowTop(v1);
        }, (v0) -> {
            return v0.offset();
        }).codec();

        public BelowTop(int $$0) {
            this.offset = $$0;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BelowTop.class), BelowTop.class, "offset", "FIELD:Lnet/minecraft/world/level/levelgen/VerticalAnchor$BelowTop;->offset:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BelowTop.class, Object.class), BelowTop.class, "offset", "FIELD:Lnet/minecraft/world/level/levelgen/VerticalAnchor$BelowTop;->offset:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int offset() {
            return this.offset;
        }

        @Override // net.minecraft.world.level.levelgen.VerticalAnchor
        public int resolveY(WorldGenerationContext $$0) {
            return (($$0.getGenDepth() - 1) + $$0.getMinGenY()) - this.offset;
        }

        @Override // java.lang.Record
        public String toString() {
            return this.offset + " below top";
        }
    }
}
