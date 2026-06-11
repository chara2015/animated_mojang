package net.minecraft.util.debug;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugBreezeInfo.class */
public final class DebugBreezeInfo extends Record {
    private final Optional<Integer> attackTarget;
    private final Optional<BlockPos> jumpTarget;
    public static final StreamCodec<ByteBuf, DebugBreezeInfo> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT.apply(ByteBufCodecs::optional), (v0) -> {
        return v0.attackTarget();
    }, BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional), (v0) -> {
        return v0.jumpTarget();
    }, DebugBreezeInfo::new);

    public DebugBreezeInfo(Optional<Integer> $$0, Optional<BlockPos> $$1) {
        this.attackTarget = $$0;
        this.jumpTarget = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DebugBreezeInfo.class), DebugBreezeInfo.class, "attackTarget;jumpTarget", "FIELD:Lnet/minecraft/util/debug/DebugBreezeInfo;->attackTarget:Ljava/util/Optional;", "FIELD:Lnet/minecraft/util/debug/DebugBreezeInfo;->jumpTarget:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DebugBreezeInfo.class), DebugBreezeInfo.class, "attackTarget;jumpTarget", "FIELD:Lnet/minecraft/util/debug/DebugBreezeInfo;->attackTarget:Ljava/util/Optional;", "FIELD:Lnet/minecraft/util/debug/DebugBreezeInfo;->jumpTarget:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DebugBreezeInfo.class, Object.class), DebugBreezeInfo.class, "attackTarget;jumpTarget", "FIELD:Lnet/minecraft/util/debug/DebugBreezeInfo;->attackTarget:Ljava/util/Optional;", "FIELD:Lnet/minecraft/util/debug/DebugBreezeInfo;->jumpTarget:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<Integer> attackTarget() {
        return this.attackTarget;
    }

    public Optional<BlockPos> jumpTarget() {
        return this.jumpTarget;
    }
}
