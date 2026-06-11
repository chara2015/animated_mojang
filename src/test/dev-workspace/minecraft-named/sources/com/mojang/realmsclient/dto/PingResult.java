package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/PingResult.class */
public final class PingResult extends Record implements ReflectionBasedSerialization {

    @SerializedName("pingResults")
    private final List<RegionPingResult> pingResults;

    @SerializedName("worldIds")
    private final List<Long> realmIds;

    public PingResult(List<RegionPingResult> $$0, List<Long> $$1) {
        this.pingResults = $$0;
        this.realmIds = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PingResult.class), PingResult.class, "pingResults;realmIds", "FIELD:Lcom/mojang/realmsclient/dto/PingResult;->pingResults:Ljava/util/List;", "FIELD:Lcom/mojang/realmsclient/dto/PingResult;->realmIds:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PingResult.class), PingResult.class, "pingResults;realmIds", "FIELD:Lcom/mojang/realmsclient/dto/PingResult;->pingResults:Ljava/util/List;", "FIELD:Lcom/mojang/realmsclient/dto/PingResult;->realmIds:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PingResult.class, Object.class), PingResult.class, "pingResults;realmIds", "FIELD:Lcom/mojang/realmsclient/dto/PingResult;->pingResults:Ljava/util/List;", "FIELD:Lcom/mojang/realmsclient/dto/PingResult;->realmIds:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @SerializedName("pingResults")
    public List<RegionPingResult> pingResults() {
        return this.pingResults;
    }

    @SerializedName("worldIds")
    public List<Long> realmIds() {
        return this.realmIds;
    }
}
