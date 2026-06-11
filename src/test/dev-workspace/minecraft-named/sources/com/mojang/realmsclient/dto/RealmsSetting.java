package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsSetting.class */
public final class RealmsSetting extends Record implements ReflectionBasedSerialization {

    @SerializedName(JigsawBlockEntity.NAME)
    private final String name;

    @SerializedName("value")
    private final String value;

    public RealmsSetting(String $$0, String $$1) {
        this.name = $$0;
        this.value = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RealmsSetting.class), RealmsSetting.class, "name;value", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSetting;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSetting;->value:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RealmsSetting.class), RealmsSetting.class, "name;value", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSetting;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSetting;->value:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RealmsSetting.class, Object.class), RealmsSetting.class, "name;value", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSetting;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/RealmsSetting;->value:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @SerializedName(JigsawBlockEntity.NAME)
    public String name() {
        return this.name;
    }

    @SerializedName("value")
    public String value() {
        return this.value;
    }

    public static RealmsSetting hardcoreSetting(boolean $$0) {
        return new RealmsSetting("hardcore", Boolean.toString($$0));
    }

    public static boolean isHardcore(List<RealmsSetting> $$0) {
        for (RealmsSetting $$1 : $$0) {
            if ($$1.name().equals("hardcore")) {
                return Boolean.parseBoolean($$1.value());
            }
        }
        return false;
    }
}
