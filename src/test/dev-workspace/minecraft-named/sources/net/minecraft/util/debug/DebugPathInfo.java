package net.minecraft.util.debug;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.pathfinder.Path;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugPathInfo.class */
public final class DebugPathInfo extends Record {
    private final Path path;
    private final float maxNodeDistance;
    public static final StreamCodec<FriendlyByteBuf, DebugPathInfo> STREAM_CODEC = StreamCodec.composite(Path.STREAM_CODEC, (v0) -> {
        return v0.path();
    }, ByteBufCodecs.FLOAT, (v0) -> {
        return v0.maxNodeDistance();
    }, (v1, v2) -> {
        return new DebugPathInfo(v1, v2);
    });

    public DebugPathInfo(Path $$0, float $$1) {
        this.path = $$0;
        this.maxNodeDistance = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DebugPathInfo.class), DebugPathInfo.class, "path;maxNodeDistance", "FIELD:Lnet/minecraft/util/debug/DebugPathInfo;->path:Lnet/minecraft/world/level/pathfinder/Path;", "FIELD:Lnet/minecraft/util/debug/DebugPathInfo;->maxNodeDistance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DebugPathInfo.class), DebugPathInfo.class, "path;maxNodeDistance", "FIELD:Lnet/minecraft/util/debug/DebugPathInfo;->path:Lnet/minecraft/world/level/pathfinder/Path;", "FIELD:Lnet/minecraft/util/debug/DebugPathInfo;->maxNodeDistance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DebugPathInfo.class, Object.class), DebugPathInfo.class, "path;maxNodeDistance", "FIELD:Lnet/minecraft/util/debug/DebugPathInfo;->path:Lnet/minecraft/world/level/pathfinder/Path;", "FIELD:Lnet/minecraft/util/debug/DebugPathInfo;->maxNodeDistance:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Path path() {
        return this.path;
    }

    public float maxNodeDistance() {
        return this.maxNodeDistance;
    }
}
