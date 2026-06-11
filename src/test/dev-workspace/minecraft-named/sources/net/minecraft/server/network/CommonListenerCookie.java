package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.server.level.ClientInformation;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/CommonListenerCookie.class */
public final class CommonListenerCookie extends Record {
    private final GameProfile gameProfile;
    private final int latency;
    private final ClientInformation clientInformation;
    private final boolean transferred;

    public CommonListenerCookie(GameProfile $$0, int $$1, ClientInformation $$2, boolean $$3) {
        this.gameProfile = $$0;
        this.latency = $$1;
        this.clientInformation = $$2;
        this.transferred = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CommonListenerCookie.class), CommonListenerCookie.class, "gameProfile;latency;clientInformation;transferred", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->gameProfile:Lcom/mojang/authlib/GameProfile;", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->latency:I", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->clientInformation:Lnet/minecraft/server/level/ClientInformation;", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->transferred:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CommonListenerCookie.class), CommonListenerCookie.class, "gameProfile;latency;clientInformation;transferred", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->gameProfile:Lcom/mojang/authlib/GameProfile;", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->latency:I", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->clientInformation:Lnet/minecraft/server/level/ClientInformation;", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->transferred:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CommonListenerCookie.class, Object.class), CommonListenerCookie.class, "gameProfile;latency;clientInformation;transferred", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->gameProfile:Lcom/mojang/authlib/GameProfile;", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->latency:I", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->clientInformation:Lnet/minecraft/server/level/ClientInformation;", "FIELD:Lnet/minecraft/server/network/CommonListenerCookie;->transferred:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public GameProfile gameProfile() {
        return this.gameProfile;
    }

    public int latency() {
        return this.latency;
    }

    public ClientInformation clientInformation() {
        return this.clientInformation;
    }

    public boolean transferred() {
        return this.transferred;
    }

    public static CommonListenerCookie createInitial(GameProfile $$0, boolean $$1) {
        return new CommonListenerCookie($$0, 0, ClientInformation.createDefault(), $$1);
    }
}
