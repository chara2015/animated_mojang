package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Consumer;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/ChunkTrackingView.class */
public interface ChunkTrackingView {
    public static final ChunkTrackingView EMPTY = new ChunkTrackingView() { // from class: net.minecraft.server.level.ChunkTrackingView.1
        @Override // net.minecraft.server.level.ChunkTrackingView
        public boolean contains(int $$0, int $$1, boolean $$2) {
            return false;
        }

        @Override // net.minecraft.server.level.ChunkTrackingView
        public void forEach(Consumer<ChunkPos> $$0) {
        }
    };

    boolean contains(int i, int i2, boolean z);

    void forEach(Consumer<ChunkPos> consumer);

    static ChunkTrackingView of(ChunkPos $$0, int $$1) {
        return new Positioned($$0, $$1);
    }

    static void difference(ChunkTrackingView $$0, ChunkTrackingView $$1, Consumer<ChunkPos> $$2, Consumer<ChunkPos> $$3) {
        if ($$0.equals($$1)) {
            return;
        }
        if ($$0 instanceof Positioned) {
            Positioned $$4 = (Positioned) $$0;
            if ($$1 instanceof Positioned) {
                Positioned $$5 = (Positioned) $$1;
                if ($$4.squareIntersects($$5)) {
                    int $$6 = Math.min($$4.minX(), $$5.minX());
                    int $$7 = Math.min($$4.minZ(), $$5.minZ());
                    int $$8 = Math.max($$4.maxX(), $$5.maxX());
                    int $$9 = Math.max($$4.maxZ(), $$5.maxZ());
                    for (int $$10 = $$6; $$10 <= $$8; $$10++) {
                        for (int $$11 = $$7; $$11 <= $$9; $$11++) {
                            boolean $$12 = $$4.contains($$10, $$11);
                            boolean $$13 = $$5.contains($$10, $$11);
                            if ($$12 != $$13) {
                                if ($$13) {
                                    $$2.accept(new ChunkPos($$10, $$11));
                                } else {
                                    $$3.accept(new ChunkPos($$10, $$11));
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
        $$0.forEach($$3);
        $$1.forEach($$2);
    }

    default boolean contains(ChunkPos $$0) {
        return contains($$0.x, $$0.z);
    }

    default boolean contains(int $$0, int $$1) {
        return contains($$0, $$1, true);
    }

    default boolean isInViewDistance(int $$0, int $$1) {
        return contains($$0, $$1, false);
    }

    static boolean isInViewDistance(int $$0, int $$1, int $$2, int $$3, int $$4) {
        return isWithinDistance($$0, $$1, $$2, $$3, $$4, false);
    }

    static boolean isWithinDistance(int $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
        int $$6 = $$5 ? 2 : 1;
        long $$7 = Math.max(0, Math.abs($$3 - $$0) - $$6);
        long $$8 = Math.max(0, Math.abs($$4 - $$1) - $$6);
        long $$9 = ($$7 * $$7) + ($$8 * $$8);
        int $$10 = $$2 * $$2;
        return $$9 < ((long) $$10);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/ChunkTrackingView$Positioned.class */
    public static final class Positioned extends Record implements ChunkTrackingView {
        private final ChunkPos center;
        private final int viewDistance;

        public Positioned(ChunkPos $$0, int $$1) {
            this.center = $$0;
            this.viewDistance = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Positioned.class), Positioned.class, "center;viewDistance", "FIELD:Lnet/minecraft/server/level/ChunkTrackingView$Positioned;->center:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/server/level/ChunkTrackingView$Positioned;->viewDistance:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Positioned.class), Positioned.class, "center;viewDistance", "FIELD:Lnet/minecraft/server/level/ChunkTrackingView$Positioned;->center:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/server/level/ChunkTrackingView$Positioned;->viewDistance:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Positioned.class, Object.class), Positioned.class, "center;viewDistance", "FIELD:Lnet/minecraft/server/level/ChunkTrackingView$Positioned;->center:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/server/level/ChunkTrackingView$Positioned;->viewDistance:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ChunkPos center() {
            return this.center;
        }

        public int viewDistance() {
            return this.viewDistance;
        }

        int minX() {
            return (this.center.x - this.viewDistance) - 1;
        }

        int minZ() {
            return (this.center.z - this.viewDistance) - 1;
        }

        int maxX() {
            return this.center.x + this.viewDistance + 1;
        }

        int maxZ() {
            return this.center.z + this.viewDistance + 1;
        }

        @VisibleForTesting
        protected boolean squareIntersects(Positioned $$0) {
            return minX() <= $$0.maxX() && maxX() >= $$0.minX() && minZ() <= $$0.maxZ() && maxZ() >= $$0.minZ();
        }

        @Override // net.minecraft.server.level.ChunkTrackingView
        public boolean contains(int $$0, int $$1, boolean $$2) {
            return ChunkTrackingView.isWithinDistance(this.center.x, this.center.z, this.viewDistance, $$0, $$1, $$2);
        }

        @Override // net.minecraft.server.level.ChunkTrackingView
        public void forEach(Consumer<ChunkPos> $$0) {
            for (int $$1 = minX(); $$1 <= maxX(); $$1++) {
                for (int $$2 = minZ(); $$2 <= maxZ(); $$2++) {
                    if (contains($$1, $$2)) {
                        $$0.accept(new ChunkPos($$1, $$2));
                    }
                }
            }
        }
    }
}
