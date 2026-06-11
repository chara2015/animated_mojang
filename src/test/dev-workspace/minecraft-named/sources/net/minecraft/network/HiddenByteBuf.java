package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCounted;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/HiddenByteBuf.class */
public final class HiddenByteBuf extends Record implements ReferenceCounted {
    private final ByteBuf contents;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, HiddenByteBuf.class), HiddenByteBuf.class, "contents", "FIELD:Lnet/minecraft/network/HiddenByteBuf;->contents:Lio/netty/buffer/ByteBuf;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, HiddenByteBuf.class), HiddenByteBuf.class, "contents", "FIELD:Lnet/minecraft/network/HiddenByteBuf;->contents:Lio/netty/buffer/ByteBuf;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, HiddenByteBuf.class, Object.class), HiddenByteBuf.class, "contents", "FIELD:Lnet/minecraft/network/HiddenByteBuf;->contents:Lio/netty/buffer/ByteBuf;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ByteBuf contents() {
        return this.contents;
    }

    public HiddenByteBuf(ByteBuf $$0) {
        this.contents = ByteBufUtil.ensureAccessible($$0);
    }

    public static Object pack(Object $$0) {
        if ($$0 instanceof ByteBuf) {
            ByteBuf $$1 = (ByteBuf) $$0;
            return new HiddenByteBuf($$1);
        }
        return $$0;
    }

    public static Object unpack(Object $$0) {
        if ($$0 instanceof HiddenByteBuf) {
            HiddenByteBuf $$1 = (HiddenByteBuf) $$0;
            return ByteBufUtil.ensureAccessible($$1.contents);
        }
        return $$0;
    }

    public int refCnt() {
        return this.contents.refCnt();
    }

    /* JADX INFO: renamed from: retain, reason: merged with bridge method [inline-methods] */
    public HiddenByteBuf m1652retain() {
        this.contents.retain();
        return this;
    }

    /* JADX INFO: renamed from: retain, reason: merged with bridge method [inline-methods] */
    public HiddenByteBuf m1651retain(int $$0) {
        this.contents.retain($$0);
        return this;
    }

    /* JADX INFO: renamed from: touch, reason: merged with bridge method [inline-methods] */
    public HiddenByteBuf m1650touch() {
        this.contents.touch();
        return this;
    }

    /* JADX INFO: renamed from: touch, reason: merged with bridge method [inline-methods] */
    public HiddenByteBuf m1649touch(Object $$0) {
        this.contents.touch($$0);
        return this;
    }

    public boolean release() {
        return this.contents.release();
    }

    public boolean release(int $$0) {
        return this.contents.release($$0);
    }
}
