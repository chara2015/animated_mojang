package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsWorldResetDto.class */
public final class RealmsWorldResetDto extends Record implements ReflectionBasedSerialization {

    @SerializedName("seed")
    private final String seed;

    @SerializedName("worldTemplateId")
    private final long worldTemplateId;

    @SerializedName("levelType")
    private final int levelType;

    @SerializedName("generateStructures")
    private final boolean generateStructures;

    @SerializedName("experiments")
    private final Set<String> experiments;

    public RealmsWorldResetDto(String $$0, long $$1, int $$2, boolean $$3, Set<String> $$4) {
        this.seed = $$0;
        this.worldTemplateId = $$1;
        this.levelType = $$2;
        this.generateStructures = $$3;
        this.experiments = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RealmsWorldResetDto.class), RealmsWorldResetDto.class, "seed;worldTemplateId;levelType;generateStructures;experiments", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->seed:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->worldTemplateId:J", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->levelType:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->generateStructures:Z", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->experiments:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RealmsWorldResetDto.class), RealmsWorldResetDto.class, "seed;worldTemplateId;levelType;generateStructures;experiments", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->seed:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->worldTemplateId:J", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->levelType:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->generateStructures:Z", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->experiments:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RealmsWorldResetDto.class, Object.class), RealmsWorldResetDto.class, "seed;worldTemplateId;levelType;generateStructures;experiments", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->seed:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->worldTemplateId:J", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->levelType:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->generateStructures:Z", "FIELD:Lcom/mojang/realmsclient/dto/RealmsWorldResetDto;->experiments:Ljava/util/Set;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @SerializedName("seed")
    public String seed() {
        return this.seed;
    }

    @SerializedName("worldTemplateId")
    public long worldTemplateId() {
        return this.worldTemplateId;
    }

    @SerializedName("levelType")
    public int levelType() {
        return this.levelType;
    }

    @SerializedName("generateStructures")
    public boolean generateStructures() {
        return this.generateStructures;
    }

    @SerializedName("experiments")
    public Set<String> experiments() {
        return this.experiments;
    }
}
