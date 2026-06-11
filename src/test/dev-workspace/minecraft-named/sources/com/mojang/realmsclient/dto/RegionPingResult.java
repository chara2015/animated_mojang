package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RegionPingResult.class */
public final class RegionPingResult extends Record implements ReflectionBasedSerialization {

    @SerializedName("regionName")
    private final String regionName;

    @SerializedName("ping")
    private final int ping;

    public RegionPingResult(String $$0, int $$1) {
        this.regionName = $$0;
        this.ping = $$1;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RegionPingResult.class), RegionPingResult.class, "regionName;ping", "FIELD:Lcom/mojang/realmsclient/dto/RegionPingResult;->regionName:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RegionPingResult;->ping:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RegionPingResult.class, Object.class), RegionPingResult.class, "regionName;ping", "FIELD:Lcom/mojang/realmsclient/dto/RegionPingResult;->regionName:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RegionPingResult;->ping:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @SerializedName("regionName")
    public String regionName() {
        return this.regionName;
    }

    @SerializedName("ping")
    public int ping() {
        return this.ping;
    }

    @Override // java.lang.Record
    public String toString() {
        return String.format(Locale.ROOT, "%s --> %.2f ms", this.regionName, Float.valueOf(this.ping));
    }
}
