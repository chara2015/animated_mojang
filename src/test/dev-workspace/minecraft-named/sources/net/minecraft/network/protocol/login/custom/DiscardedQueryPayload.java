package net.minecraft.network.protocol.login.custom;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/login/custom/DiscardedQueryPayload.class */
public final class DiscardedQueryPayload extends Record implements CustomQueryPayload {
    private final Identifier id;

    public DiscardedQueryPayload(Identifier $$0) {
        this.id = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DiscardedQueryPayload.class), DiscardedQueryPayload.class, "id", "FIELD:Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DiscardedQueryPayload.class), DiscardedQueryPayload.class, "id", "FIELD:Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DiscardedQueryPayload.class, Object.class), DiscardedQueryPayload.class, "id", "FIELD:Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.network.protocol.login.custom.CustomQueryPayload
    public Identifier id() {
        return this.id;
    }

    @Override // net.minecraft.network.protocol.login.custom.CustomQueryPayload
    public void write(FriendlyByteBuf $$0) {
    }
}
