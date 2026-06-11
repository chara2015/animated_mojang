package net.minecraft.server.jsonrpc;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.jsonrpc.api.MethodInfo;
import net.minecraft.server.jsonrpc.api.ParamInfo;
import net.minecraft.server.jsonrpc.api.ResultInfo;
import net.minecraft.server.jsonrpc.api.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod.class */
public interface OutgoingRpcMethod<Params, Result> {
    public static final String NOTIFICATION_PREFIX = "notification/";

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod$Factory.class */
    @FunctionalInterface
    public interface Factory<Params, Result> {
        OutgoingRpcMethod<Params, Result> create(MethodInfo<Params, Result> methodInfo, Attributes attributes);
    }

    MethodInfo<Params, Result> info();

    Attributes attributes();

    default JsonElement encodeParams(Params $$0) {
        return null;
    }

    default Result decodeResult(JsonElement $$0) {
        return null;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes.class */
    public static final class Attributes extends Record {
        private final boolean discoverable;

        public Attributes(boolean $$0) {
            this.discoverable = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Attributes.class), Attributes.class, "discoverable", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;->discoverable:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Attributes.class), Attributes.class, "discoverable", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;->discoverable:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Attributes.class, Object.class), Attributes.class, "discoverable", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;->discoverable:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public boolean discoverable() {
            return this.discoverable;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod$ParmeterlessNotification.class */
    public static final class ParmeterlessNotification extends Record implements OutgoingRpcMethod<Void, Void> {
        private final MethodInfo<Void, Void> info;
        private final Attributes attributes;

        public ParmeterlessNotification(MethodInfo<Void, Void> $$0, Attributes $$1) {
            this.info = $$0;
            this.attributes = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ParmeterlessNotification.class), ParmeterlessNotification.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParmeterlessNotification;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParmeterlessNotification;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ParmeterlessNotification.class), ParmeterlessNotification.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParmeterlessNotification;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParmeterlessNotification;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ParmeterlessNotification.class, Object.class), ParmeterlessNotification.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParmeterlessNotification;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParmeterlessNotification;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public MethodInfo<Void, Void> info() {
            return this.info;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public Attributes attributes() {
            return this.attributes;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod$Notification.class */
    public static final class Notification<Params> extends Record implements OutgoingRpcMethod<Params, Void> {
        private final MethodInfo<Params, Void> info;
        private final Attributes attributes;

        public Notification(MethodInfo<Params, Void> $$0, Attributes $$1) {
            this.info = $$0;
            this.attributes = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Notification.class), Notification.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Notification;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Notification;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Notification.class), Notification.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Notification;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Notification;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Notification.class, Object.class), Notification.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Notification;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Notification;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public MethodInfo<Params, Void> info() {
            return this.info;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public Attributes attributes() {
            return this.attributes;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public JsonElement encodeParams(Params $$0) {
            if (this.info.params().isEmpty()) {
                throw new IllegalStateException("Method defined as having no parameters");
            }
            return (JsonElement) this.info.params().get().schema().codec().encodeStart(JsonOps.INSTANCE, $$0).getOrThrow();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod$ParameterlessMethod.class */
    public static final class ParameterlessMethod<Result> extends Record implements OutgoingRpcMethod<Void, Result> {
        private final MethodInfo<Void, Result> info;
        private final Attributes attributes;

        public ParameterlessMethod(MethodInfo<Void, Result> $$0, Attributes $$1) {
            this.info = $$0;
            this.attributes = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ParameterlessMethod.class), ParameterlessMethod.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParameterlessMethod;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParameterlessMethod;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ParameterlessMethod.class), ParameterlessMethod.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParameterlessMethod;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParameterlessMethod;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ParameterlessMethod.class, Object.class), ParameterlessMethod.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParameterlessMethod;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$ParameterlessMethod;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public MethodInfo<Void, Result> info() {
            return this.info;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public Attributes attributes() {
            return this.attributes;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public Result decodeResult(JsonElement jsonElement) {
            if (this.info.result().isEmpty()) {
                throw new IllegalStateException("Method defined as having no result");
            }
            return (Result) this.info.result().get().schema().codec().parse(JsonOps.INSTANCE, jsonElement).getOrThrow();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod$Method.class */
    public static final class Method<Params, Result> extends Record implements OutgoingRpcMethod<Params, Result> {
        private final MethodInfo<Params, Result> info;
        private final Attributes attributes;

        public Method(MethodInfo<Params, Result> $$0, Attributes $$1) {
            this.info = $$0;
            this.attributes = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Method.class), Method.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Method;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Method;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Method.class), Method.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Method;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Method;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Method.class, Object.class), Method.class, "info;attributes", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Method;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Method;->attributes:Lnet/minecraft/server/jsonrpc/OutgoingRpcMethod$Attributes;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public MethodInfo<Params, Result> info() {
            return this.info;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public Attributes attributes() {
            return this.attributes;
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public JsonElement encodeParams(Params $$0) {
            if (this.info.params().isEmpty()) {
                throw new IllegalStateException("Method defined as having no parameters");
            }
            return (JsonElement) this.info.params().get().schema().codec().encodeStart(JsonOps.INSTANCE, $$0).getOrThrow();
        }

        @Override // net.minecraft.server.jsonrpc.OutgoingRpcMethod
        public Result decodeResult(JsonElement jsonElement) {
            if (this.info.result().isEmpty()) {
                throw new IllegalStateException("Method defined as having no result");
            }
            return (Result) this.info.result().get().schema().codec().parse(JsonOps.INSTANCE, jsonElement).getOrThrow();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/OutgoingRpcMethod$OutgoingRpcMethodBuilder.class */
    public static class OutgoingRpcMethodBuilder<Params, Result> {
        public static final Attributes DEFAULT_ATTRIBUTES = new Attributes(true);
        private final Factory<Params, Result> method;
        private String description = "";
        private ParamInfo<Params> paramInfo;
        private ResultInfo<Result> resultInfo;

        public OutgoingRpcMethodBuilder(Factory<Params, Result> $$0) {
            this.method = $$0;
        }

        public OutgoingRpcMethodBuilder<Params, Result> description(String $$0) {
            this.description = $$0;
            return this;
        }

        public OutgoingRpcMethodBuilder<Params, Result> response(String $$0, Schema<Result> $$1) {
            this.resultInfo = new ResultInfo<>($$0, $$1);
            return this;
        }

        public OutgoingRpcMethodBuilder<Params, Result> param(String $$0, Schema<Params> $$1) {
            this.paramInfo = new ParamInfo<>($$0, $$1);
            return this;
        }

        private OutgoingRpcMethod<Params, Result> build() {
            MethodInfo<Params, Result> $$0 = new MethodInfo<>(this.description, this.paramInfo, this.resultInfo);
            return this.method.create($$0, DEFAULT_ATTRIBUTES);
        }

        public Holder.Reference<OutgoingRpcMethod<Params, Result>> register(String $$0) {
            return register(Identifier.withDefaultNamespace("notification/" + $$0));
        }

        private Holder.Reference<OutgoingRpcMethod<Params, Result>> register(Identifier $$0) {
            return Registry.registerForHolder(BuiltInRegistries.OUTGOING_RPC_METHOD, $$0, build());
        }
    }

    static OutgoingRpcMethodBuilder<Void, Void> notification() {
        return new OutgoingRpcMethodBuilder<>(ParmeterlessNotification::new);
    }

    static <Params> OutgoingRpcMethodBuilder<Params, Void> notificationWithParams() {
        return new OutgoingRpcMethodBuilder<>(Notification::new);
    }

    static <Result> OutgoingRpcMethodBuilder<Void, Result> request() {
        return new OutgoingRpcMethodBuilder<>(ParameterlessMethod::new);
    }

    static <Params, Result> OutgoingRpcMethodBuilder<Params, Result> requestWithParams() {
        return new OutgoingRpcMethodBuilder<>(Method::new);
    }
}
