package net.minecraft.world.level.block.entity;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BoundingBoxRenderable.class */
public interface BoundingBoxRenderable {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BoundingBoxRenderable$Mode.class */
    public enum Mode {
        NONE,
        BOX,
        BOX_AND_INVISIBLE_BLOCKS
    }

    Mode renderMode();

    RenderableBox getRenderableBox();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BoundingBoxRenderable$RenderableBox.class */
    public static final class RenderableBox extends Record {
        private final BlockPos localPos;
        private final Vec3i size;

        public RenderableBox(BlockPos $$0, Vec3i $$1) {
            this.localPos = $$0;
            this.size = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RenderableBox.class), RenderableBox.class, "localPos;size", "FIELD:Lnet/minecraft/world/level/block/entity/BoundingBoxRenderable$RenderableBox;->localPos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/entity/BoundingBoxRenderable$RenderableBox;->size:Lnet/minecraft/core/Vec3i;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RenderableBox.class), RenderableBox.class, "localPos;size", "FIELD:Lnet/minecraft/world/level/block/entity/BoundingBoxRenderable$RenderableBox;->localPos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/entity/BoundingBoxRenderable$RenderableBox;->size:Lnet/minecraft/core/Vec3i;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RenderableBox.class, Object.class), RenderableBox.class, "localPos;size", "FIELD:Lnet/minecraft/world/level/block/entity/BoundingBoxRenderable$RenderableBox;->localPos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/entity/BoundingBoxRenderable$RenderableBox;->size:Lnet/minecraft/core/Vec3i;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public BlockPos localPos() {
            return this.localPos;
        }

        public Vec3i size() {
            return this.size;
        }

        public static RenderableBox fromCorners(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
            int $$6 = Math.min($$0, $$3);
            int $$7 = Math.min($$1, $$4);
            int $$8 = Math.min($$2, $$5);
            return new RenderableBox(new BlockPos($$6, $$7, $$8), new Vec3i(Math.max($$0, $$3) - $$6, Math.max($$1, $$4) - $$7, Math.max($$2, $$5) - $$8));
        }
    }
}
