package net.minecraft.util.debug;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugStructureInfo.class */
public final class DebugStructureInfo extends Record {
    private final BoundingBox boundingBox;
    private final List<Piece> pieces;
    public static final StreamCodec<ByteBuf, DebugStructureInfo> STREAM_CODEC = StreamCodec.composite(BoundingBox.STREAM_CODEC, (v0) -> {
        return v0.boundingBox();
    }, Piece.STREAM_CODEC.apply(ByteBufCodecs.list()), (v0) -> {
        return v0.pieces();
    }, DebugStructureInfo::new);

    public DebugStructureInfo(BoundingBox $$0, List<Piece> $$1) {
        this.boundingBox = $$0;
        this.pieces = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DebugStructureInfo.class), DebugStructureInfo.class, "boundingBox;pieces", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo;->pieces:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DebugStructureInfo.class), DebugStructureInfo.class, "boundingBox;pieces", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo;->pieces:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DebugStructureInfo.class, Object.class), DebugStructureInfo.class, "boundingBox;pieces", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo;->pieces:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BoundingBox boundingBox() {
        return this.boundingBox;
    }

    public List<Piece> pieces() {
        return this.pieces;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugStructureInfo$Piece.class */
    public static final class Piece extends Record {
        private final BoundingBox boundingBox;
        private final boolean isStart;
        public static final StreamCodec<ByteBuf, Piece> STREAM_CODEC = StreamCodec.composite(BoundingBox.STREAM_CODEC, (v0) -> {
            return v0.boundingBox();
        }, ByteBufCodecs.BOOL, (v0) -> {
            return v0.isStart();
        }, (v1, v2) -> {
            return new Piece(v1, v2);
        });

        public Piece(BoundingBox $$0, boolean $$1) {
            this.boundingBox = $$0;
            this.isStart = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Piece.class), Piece.class, "boundingBox;isStart", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo$Piece;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo$Piece;->isStart:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Piece.class), Piece.class, "boundingBox;isStart", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo$Piece;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo$Piece;->isStart:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Piece.class, Object.class), Piece.class, "boundingBox;isStart", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo$Piece;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", "FIELD:Lnet/minecraft/util/debug/DebugStructureInfo$Piece;->isStart:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public BoundingBox boundingBox() {
            return this.boundingBox;
        }

        public boolean isStart() {
            return this.isStart;
        }
    }
}
