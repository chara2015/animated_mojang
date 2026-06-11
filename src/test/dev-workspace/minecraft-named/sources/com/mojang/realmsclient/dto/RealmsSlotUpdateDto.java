package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.RealmsServer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsSlotUpdateDto.class */
public final class RealmsSlotUpdateDto extends Record implements ReflectionBasedSerialization {

    @SerializedName("slotId")
    private final int slotId;

    @SerializedName("spawnProtection")
    private final int spawnProtection;

    @SerializedName("forceGameMode")
    private final boolean forceGameMode;

    @SerializedName("difficulty")
    private final int difficulty;

    @SerializedName("gameMode")
    private final int gameMode;

    @SerializedName("slotName")
    private final String slotName;

    @SerializedName("version")
    private final String version;

    @SerializedName("compatibility")
    private final RealmsServer.Compatibility compatibility;

    @SerializedName("worldTemplateId")
    private final long templateId;

    @SerializedName("worldTemplateImage")
    private final String templateImage;

    @SerializedName("hardcore")
    private final boolean hardcore;

    public RealmsSlotUpdateDto(int $$0, int $$1, boolean $$2, int $$3, int $$4, String $$5, String $$6, RealmsServer.Compatibility $$7, long $$8, String $$9, boolean $$10) {
        this.slotId = $$0;
        this.spawnProtection = $$1;
        this.forceGameMode = $$2;
        this.difficulty = $$3;
        this.gameMode = $$4;
        this.slotName = $$5;
        this.version = $$6;
        this.compatibility = $$7;
        this.templateId = $$8;
        this.templateImage = $$9;
        this.hardcore = $$10;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RealmsSlotUpdateDto.class), RealmsSlotUpdateDto.class, "slotId;spawnProtection;forceGameMode;difficulty;gameMode;slotName;version;compatibility;templateId;templateImage;hardcore", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->slotId:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->spawnProtection:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->forceGameMode:Z", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->difficulty:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->gameMode:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->slotName:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->version:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->compatibility:Lcom/mojang/realmsclient/dto/RealmsServer$Compatibility;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->templateId:J", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->templateImage:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->hardcore:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RealmsSlotUpdateDto.class), RealmsSlotUpdateDto.class, "slotId;spawnProtection;forceGameMode;difficulty;gameMode;slotName;version;compatibility;templateId;templateImage;hardcore", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->slotId:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->spawnProtection:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->forceGameMode:Z", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->difficulty:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->gameMode:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->slotName:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->version:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->compatibility:Lcom/mojang/realmsclient/dto/RealmsServer$Compatibility;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->templateId:J", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->templateImage:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->hardcore:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RealmsSlotUpdateDto.class, Object.class), RealmsSlotUpdateDto.class, "slotId;spawnProtection;forceGameMode;difficulty;gameMode;slotName;version;compatibility;templateId;templateImage;hardcore", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->slotId:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->spawnProtection:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->forceGameMode:Z", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->difficulty:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->gameMode:I", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->slotName:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->version:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->compatibility:Lcom/mojang/realmsclient/dto/RealmsServer$Compatibility;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->templateId:J", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->templateImage:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSlotUpdateDto;->hardcore:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @SerializedName("slotId")
    public int slotId() {
        return this.slotId;
    }

    @SerializedName("spawnProtection")
    public int spawnProtection() {
        return this.spawnProtection;
    }

    @SerializedName("forceGameMode")
    public boolean forceGameMode() {
        return this.forceGameMode;
    }

    @SerializedName("difficulty")
    public int difficulty() {
        return this.difficulty;
    }

    @SerializedName("gameMode")
    public int gameMode() {
        return this.gameMode;
    }

    @SerializedName("slotName")
    public String slotName() {
        return this.slotName;
    }

    @SerializedName("version")
    public String version() {
        return this.version;
    }

    @SerializedName("compatibility")
    public RealmsServer.Compatibility compatibility() {
        return this.compatibility;
    }

    @SerializedName("worldTemplateId")
    public long templateId() {
        return this.templateId;
    }

    @SerializedName("worldTemplateImage")
    public String templateImage() {
        return this.templateImage;
    }

    @SerializedName("hardcore")
    public boolean hardcore() {
        return this.hardcore;
    }

    public RealmsSlotUpdateDto(int $$0, RealmsWorldOptions $$1, boolean $$2) {
        this($$0, $$1.spawnProtection, $$1.forceGameMode, $$1.difficulty, $$1.gameMode, $$1.getSlotName($$0), $$1.version, $$1.compatibility, $$1.templateId, $$1.templateImage, $$2);
    }
}
