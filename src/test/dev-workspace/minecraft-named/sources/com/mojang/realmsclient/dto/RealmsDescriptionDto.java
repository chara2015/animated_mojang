package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsDescriptionDto.class */
public final class RealmsDescriptionDto extends Record implements ReflectionBasedSerialization {

    @SerializedName(JigsawBlockEntity.NAME)
    private final String name;

    @SerializedName("description")
    private final String description;

    public RealmsDescriptionDto(String $$0, String $$1) {
        this.name = $$0;
        this.description = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RealmsDescriptionDto.class), RealmsDescriptionDto.class, "name;description", "FIELD:Lcom/mojang/realmsclient/dto/RealmsDescriptionDto;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsDescriptionDto;->description:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RealmsDescriptionDto.class), RealmsDescriptionDto.class, "name;description", "FIELD:Lcom/mojang/realmsclient/dto/RealmsDescriptionDto;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsDescriptionDto;->description:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RealmsDescriptionDto.class, Object.class), RealmsDescriptionDto.class, "name;description", "FIELD:Lcom/mojang/realmsclient/dto/RealmsDescriptionDto;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsDescriptionDto;->description:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @SerializedName(JigsawBlockEntity.NAME)
    public String name() {
        return this.name;
    }

    @SerializedName("description")
    public String description() {
        return this.description;
    }
}
