package net.minecraft.server.jsonrpc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.jsonrpc.api.MethodInfo;
import net.minecraft.server.jsonrpc.api.ParamInfo;
import net.minecraft.server.jsonrpc.api.ResultInfo;
import net.minecraft.server.jsonrpc.api.Schema;
import net.minecraft.server.jsonrpc.internalapi.MinecraftApi;
import net.minecraft.server.jsonrpc.methods.ClientInfo;
import net.minecraft.server.jsonrpc.methods.EncodeJsonRpcException;
import net.minecraft.server.jsonrpc.methods.InvalidParameterJsonRpcException;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/IncomingRpcMethod.class */
public interface IncomingRpcMethod<Params, Result> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessRpcMethodFunction.class */
    @FunctionalInterface
    public interface ParameterlessRpcMethodFunction<Result> {
        Result apply(MinecraftApi minecraftApi, ClientInfo clientInfo);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/IncomingRpcMethod$RpcMethodFunction.class */
    @FunctionalInterface
    public interface RpcMethodFunction<Params, Result> {
        Result apply(MinecraftApi minecraftApi, Params params, ClientInfo clientInfo);
    }

    MethodInfo<Params, Result> info();

    Attributes attributes();

    JsonElement apply(MinecraftApi minecraftApi, JsonElement jsonElement, ClientInfo clientInfo);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes.class */
    public static final class Attributes extends Record {
        private final boolean runOnMainThread;
        private final boolean discoverable;

        public Attributes(boolean $$0, boolean $$1) {
            this.runOnMainThread = $$0;
            this.discoverable = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Attributes.class), Attributes.class, "runOnMainThread;discoverable", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;->runOnMainThread:Z", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;->discoverable:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Attributes.class), Attributes.class, "runOnMainThread;discoverable", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;->runOnMainThread:Z", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;->discoverable:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Attributes.class, Object.class), Attributes.class, "runOnMainThread;discoverable", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;->runOnMainThread:Z", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;->discoverable:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public boolean runOnMainThread() {
            return this.runOnMainThread;
        }

        public boolean discoverable() {
            return this.discoverable;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod.class */
    public static final class ParameterlessMethod<Params, Result> extends Record implements IncomingRpcMethod<Params, Result> {
        private final MethodInfo<Params, Result> info;
        private final Attributes attributes;
        private final ParameterlessRpcMethodFunction<Result> supplier;

        public ParameterlessMethod(MethodInfo<Params, Result> $$0, Attributes $$1, ParameterlessRpcMethodFunction<Result> $$2) {
            this.info = $$0;
            this.attributes = $$1;
            this.supplier = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ParameterlessMethod.class), ParameterlessMethod.class, "info;attributes;supplier", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->attributes:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->supplier:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessRpcMethodFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ParameterlessMethod.class), ParameterlessMethod.class, "info;attributes;supplier", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->attributes:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->supplier:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessRpcMethodFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ParameterlessMethod.class, Object.class), ParameterlessMethod.class, "info;attributes;supplier", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->attributes:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessMethod;->supplier:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$ParameterlessRpcMethodFunction;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.server.jsonrpc.IncomingRpcMethod
        public MethodInfo<Params, Result> info() {
            return this.info;
        }

        @Override // net.minecraft.server.jsonrpc.IncomingRpcMethod
        public Attributes attributes() {
            return this.attributes;
        }

        public ParameterlessRpcMethodFunction<Result> supplier() {
            return this.supplier;
        }

        @Override // net.minecraft.server.jsonrpc.IncomingRpcMethod
        public JsonElement apply(MinecraftApi $$0, JsonElement $$1, ClientInfo $$2) {
            if ($$1 != null && (!$$1.isJsonArray() || !$$1.getAsJsonArray().isEmpty())) {
                throw new InvalidParameterJsonRpcException("Expected no params, or an empty array");
            }
            if (this.info.params().isPresent()) {
                throw new IllegalArgumentException("Parameterless method unexpectedly has parameter description");
            }
            Result $$3 = this.supplier.apply($$0, $$2);
            if (this.info.result().isEmpty()) {
                throw new IllegalStateException("No result codec defined");
            }
            return (JsonElement) this.info.result().get().schema().codec().encodeStart(JsonOps.INSTANCE, $$3).getOrThrow(InvalidParameterJsonRpcException::new);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/IncomingRpcMethod$Method.class */
    public static final class Method<Params, Result> extends Record implements IncomingRpcMethod<Params, Result> {
        private final MethodInfo<Params, Result> info;
        private final Attributes attributes;
        private final RpcMethodFunction<Params, Result> function;

        public Method(MethodInfo<Params, Result> $$0, Attributes $$1, RpcMethodFunction<Params, Result> $$2) {
            this.info = $$0;
            this.attributes = $$1;
            this.function = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Method.class), Method.class, "info;attributes;function", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->attributes:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->function:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$RpcMethodFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Method.class), Method.class, "info;attributes;function", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->attributes:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->function:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$RpcMethodFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Method.class, Object.class), Method.class, "info;attributes;function", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->info:Lnet/minecraft/server/jsonrpc/api/MethodInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->attributes:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Attributes;", "FIELD:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$Method;->function:Lnet/minecraft/server/jsonrpc/IncomingRpcMethod$RpcMethodFunction;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.server.jsonrpc.IncomingRpcMethod
        public MethodInfo<Params, Result> info() {
            return this.info;
        }

        @Override // net.minecraft.server.jsonrpc.IncomingRpcMethod
        public Attributes attributes() {
            return this.attributes;
        }

        public RpcMethodFunction<Params, Result> function() {
            return this.function;
        }

        @Override // net.minecraft.server.jsonrpc.IncomingRpcMethod
        public JsonElement apply(MinecraftApi minecraftApi, JsonElement jsonElement, ClientInfo clientInfo) {
            JsonElement jsonElement2;
            if (jsonElement == null || (!jsonElement.isJsonArray() && !jsonElement.isJsonObject())) {
                throw new InvalidParameterJsonRpcException("Expected params as array or named");
            }
            if (this.info.params().isEmpty()) {
                throw new IllegalArgumentException("Method defined as having parameters without describing them");
            }
            if (jsonElement.isJsonObject()) {
                String strName = this.info.params().get().name();
                JsonElement jsonElement3 = jsonElement.getAsJsonObject().get(strName);
                if (jsonElement3 == null) {
                    throw new InvalidParameterJsonRpcException(String.format(Locale.ROOT, "Params passed by-name, but expected param [%s] does not exist", strName));
                }
                jsonElement2 = jsonElement3;
            } else {
                JsonArray asJsonArray = jsonElement.getAsJsonArray();
                if (asJsonArray.isEmpty() || asJsonArray.size() > 1) {
                    throw new InvalidParameterJsonRpcException("Expected exactly one element in the params array");
                }
                jsonElement2 = asJsonArray.get(0);
            }
            Result resultApply = this.function.apply(minecraftApi, (Params) this.info.params().get().schema().codec().parse(JsonOps.INSTANCE, jsonElement2).getOrThrow(InvalidParameterJsonRpcException::new), clientInfo);
            if (this.info.result().isEmpty()) {
                throw new IllegalStateException("No result codec defined");
            }
            return (JsonElement) this.info.result().get().schema().codec().encodeStart(JsonOps.INSTANCE, resultApply).getOrThrow(EncodeJsonRpcException::new);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/IncomingRpcMethod$IncomingRpcMethodBuilder.class */
    public static class IncomingRpcMethodBuilder<Params, Result> {
        private ParamInfo<Params> paramInfo;
        private ResultInfo<Result> resultInfo;
        private ParameterlessRpcMethodFunction<Result> parameterlessFunction;
        private RpcMethodFunction<Params, Result> parameterFunction;
        private String description = "";
        private boolean discoverable = true;
        private boolean runOnMainThread = true;

        public IncomingRpcMethodBuilder(ParameterlessRpcMethodFunction<Result> $$0) {
            this.parameterlessFunction = $$0;
        }

        public IncomingRpcMethodBuilder(RpcMethodFunction<Params, Result> $$0) {
            this.parameterFunction = $$0;
        }

        public IncomingRpcMethodBuilder(Function<MinecraftApi, Result> $$0) {
            this.parameterlessFunction = ($$1, $$2) -> {
                return $$0.apply($$1);
            };
        }

        public IncomingRpcMethodBuilder<Params, Result> description(String $$0) {
            this.description = $$0;
            return this;
        }

        public IncomingRpcMethodBuilder<Params, Result> response(String $$0, Schema<Result> $$1) {
            this.resultInfo = new ResultInfo<>($$0, $$1.info());
            return this;
        }

        public IncomingRpcMethodBuilder<Params, Result> param(String $$0, Schema<Params> $$1) {
            this.paramInfo = new ParamInfo<>($$0, $$1.info());
            return this;
        }

        public IncomingRpcMethodBuilder<Params, Result> undiscoverable() {
            this.discoverable = false;
            return this;
        }

        public IncomingRpcMethodBuilder<Params, Result> notOnMainThread() {
            this.runOnMainThread = false;
            return this;
        }

        public IncomingRpcMethod<Params, Result> build() {
            if (this.resultInfo == null) {
                throw new IllegalStateException("No response defined");
            }
            Attributes $$0 = new Attributes(this.runOnMainThread, this.discoverable);
            MethodInfo<Params, Result> $$1 = new MethodInfo<>(this.description, this.paramInfo, this.resultInfo);
            if (this.parameterlessFunction != null) {
                return new ParameterlessMethod($$1, $$0, this.parameterlessFunction);
            }
            if (this.parameterFunction != null) {
                if (this.paramInfo == null) {
                    throw new IllegalStateException("No param schema defined");
                }
                return new Method($$1, $$0, this.parameterFunction);
            }
            throw new IllegalStateException("No method defined");
        }

        public IncomingRpcMethod<?, ?> register(Registry<IncomingRpcMethod<?, ?>> $$0, String $$1) {
            return register($$0, Identifier.withDefaultNamespace($$1));
        }

        private IncomingRpcMethod<?, ?> register(Registry<IncomingRpcMethod<?, ?>> $$0, Identifier $$1) {
            return (IncomingRpcMethod) Registry.register($$0, $$1, build());
        }
    }

    static <Result> IncomingRpcMethodBuilder<Void, Result> method(ParameterlessRpcMethodFunction<Result> $$0) {
        return new IncomingRpcMethodBuilder<>($$0);
    }

    static <Params, Result> IncomingRpcMethodBuilder<Params, Result> method(RpcMethodFunction<Params, Result> $$0) {
        return new IncomingRpcMethodBuilder<>($$0);
    }

    static <Result> IncomingRpcMethodBuilder<Void, Result> method(Function<MinecraftApi, Result> $$0) {
        return new IncomingRpcMethodBuilder<>($$0);
    }
}
