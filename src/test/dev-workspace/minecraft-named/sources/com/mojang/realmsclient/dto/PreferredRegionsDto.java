package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/PreferredRegionsDto.class */
public final class PreferredRegionsDto extends Record implements ReflectionBasedSerialization {

    @SerializedName("regionDataList")
    private final List<RegionDataDto> regionData;

    public PreferredRegionsDto(List<RegionDataDto> $$0) {
        this.regionData = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PreferredRegionsDto.class), PreferredRegionsDto.class, "regionData", "FIELD:Lcom/mojang/realmsclient/dto/PreferredRegionsDto;->regionData:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PreferredRegionsDto.class), PreferredRegionsDto.class, "regionData", "FIELD:Lcom/mojang/realmsclient/dto/PreferredRegionsDto;->regionData:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PreferredRegionsDto.class, Object.class), PreferredRegionsDto.class, "regionData", "FIELD:Lcom/mojang/realmsclient/dto/PreferredRegionsDto;->regionData:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @SerializedName("regionDataList")
    public List<RegionDataDto> regionData() {
        return this.regionData;
    }

    public static PreferredRegionsDto empty() {
        return new PreferredRegionsDto(List.of());
    }
}
