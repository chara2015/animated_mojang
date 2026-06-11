package net.minecraft.server.jsonrpc.methods;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/ClientInfo.class */
public final class ClientInfo extends Record {
    private final Integer connectionId;

    public ClientInfo(Integer $$0) {
        this.connectionId = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientInfo.class), ClientInfo.class, "connectionId", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ClientInfo;->connectionId:Ljava/lang/Integer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientInfo.class), ClientInfo.class, "connectionId", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ClientInfo;->connectionId:Ljava/lang/Integer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientInfo.class, Object.class), ClientInfo.class, "connectionId", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ClientInfo;->connectionId:Ljava/lang/Integer;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Integer connectionId() {
        return this.connectionId;
    }

    public static ClientInfo of(Integer $$0) {
        return new ClientInfo($$0);
    }
}
