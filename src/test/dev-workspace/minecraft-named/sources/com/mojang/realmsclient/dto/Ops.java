package com.mojang.realmsclient.dto;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.util.LenientJsonParser;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/Ops.class */
public final class Ops extends Record {
    private final Set<String> ops;
    private static final Logger LOGGER = LogUtils.getLogger();

    public Ops(Set<String> $$0) {
        this.ops = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Ops.class), Ops.class, "ops", "FIELD:Lcom/mojang/realmsclient/dto/Ops;->ops:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Ops.class), Ops.class, "ops", "FIELD:Lcom/mojang/realmsclient/dto/Ops;->ops:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Ops.class, Object.class), Ops.class, "ops", "FIELD:Lcom/mojang/realmsclient/dto/Ops;->ops:Ljava/util/Set;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Set<String> ops() {
        return this.ops;
    }

    public static Ops parse(String $$0) {
        Set<String> $$1 = new HashSet<>();
        try {
            JsonObject $$2 = LenientJsonParser.parse($$0).getAsJsonObject();
            JsonElement $$3 = $$2.get("ops");
            if ($$3.isJsonArray()) {
                for (JsonElement $$4 : $$3.getAsJsonArray()) {
                    $$1.add($$4.getAsString());
                }
            }
        } catch (Exception $$5) {
            LOGGER.error("Could not parse Ops", $$5);
        }
        return new Ops($$1);
    }
}
