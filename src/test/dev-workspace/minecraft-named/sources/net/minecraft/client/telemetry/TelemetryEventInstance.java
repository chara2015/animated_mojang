package net.minecraft.client.telemetry;

import com.mojang.authlib.minecraft.TelemetryEvent;
import com.mojang.authlib.minecraft.TelemetrySession;
import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/TelemetryEventInstance.class */
public final class TelemetryEventInstance extends Record {
    private final TelemetryEventType type;
    private final TelemetryPropertyMap properties;
    public static final Codec<TelemetryEventInstance> CODEC = TelemetryEventType.CODEC.dispatchStable((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TelemetryEventInstance.class), TelemetryEventInstance.class, "type;properties", "FIELD:Lnet/minecraft/client/telemetry/TelemetryEventInstance;->type:Lnet/minecraft/client/telemetry/TelemetryEventType;", "FIELD:Lnet/minecraft/client/telemetry/TelemetryEventInstance;->properties:Lnet/minecraft/client/telemetry/TelemetryPropertyMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TelemetryEventInstance.class), TelemetryEventInstance.class, "type;properties", "FIELD:Lnet/minecraft/client/telemetry/TelemetryEventInstance;->type:Lnet/minecraft/client/telemetry/TelemetryEventType;", "FIELD:Lnet/minecraft/client/telemetry/TelemetryEventInstance;->properties:Lnet/minecraft/client/telemetry/TelemetryPropertyMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TelemetryEventInstance.class, Object.class), TelemetryEventInstance.class, "type;properties", "FIELD:Lnet/minecraft/client/telemetry/TelemetryEventInstance;->type:Lnet/minecraft/client/telemetry/TelemetryEventType;", "FIELD:Lnet/minecraft/client/telemetry/TelemetryEventInstance;->properties:Lnet/minecraft/client/telemetry/TelemetryPropertyMap;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public TelemetryEventType type() {
        return this.type;
    }

    public TelemetryPropertyMap properties() {
        return this.properties;
    }

    public TelemetryEventInstance(TelemetryEventType $$0, TelemetryPropertyMap $$1) {
        $$1.propertySet().forEach($$12 -> {
            if (!$$0.contains($$12)) {
                throw new IllegalArgumentException("Property '" + $$12.id() + "' not expected for event: '" + $$0.id() + "'");
            }
        });
        this.type = $$0;
        this.properties = $$1;
    }

    public TelemetryEvent export(TelemetrySession $$0) {
        return this.type.export($$0, this.properties);
    }
}
