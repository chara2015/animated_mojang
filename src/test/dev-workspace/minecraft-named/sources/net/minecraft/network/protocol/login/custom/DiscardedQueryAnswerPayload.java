package net.minecraft.network.protocol.login.custom;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/custom/DiscardedQueryAnswerPayload.class */
public final class DiscardedQueryAnswerPayload extends Record implements CustomQueryAnswerPayload {
    public static final DiscardedQueryAnswerPayload INSTANCE = new DiscardedQueryAnswerPayload();

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DiscardedQueryAnswerPayload.class), DiscardedQueryAnswerPayload.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DiscardedQueryAnswerPayload.class), DiscardedQueryAnswerPayload.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DiscardedQueryAnswerPayload.class, Object.class), DiscardedQueryAnswerPayload.class, "").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload
    public void write(FriendlyByteBuf $$0) {
    }
}
