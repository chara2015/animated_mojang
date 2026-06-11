package net.minecraft.server.jsonrpc.methods;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.jsonrpc.IncomingRpcMethod;
import net.minecraft.server.jsonrpc.JsonRPCUtils;
import net.minecraft.server.jsonrpc.OutgoingRpcMethod;
import net.minecraft.server.jsonrpc.api.MethodInfo;
import net.minecraft.server.jsonrpc.api.Schema;
import net.minecraft.server.jsonrpc.api.SchemaComponent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/DiscoveryService.class */
public class DiscoveryService {
    public static DiscoverResponse discover(List<SchemaComponent<?>> $$0) {
        List<MethodInfo.Named<?, ?>> $$1 = new ArrayList<>(BuiltInRegistries.INCOMING_RPC_METHOD.size() + BuiltInRegistries.OUTGOING_RPC_METHOD.size());
        BuiltInRegistries.INCOMING_RPC_METHOD.listElements().forEach($$12 -> {
            if (((IncomingRpcMethod) $$12.value()).attributes().discoverable()) {
                $$1.add(((IncomingRpcMethod) $$12.value()).info().named($$12.key().identifier()));
            }
        });
        BuiltInRegistries.OUTGOING_RPC_METHOD.listElements().forEach($$13 -> {
            if (((OutgoingRpcMethod) $$13.value()).attributes().discoverable()) {
                $$1.add(((OutgoingRpcMethod) $$13.value()).info().named($$13.key().identifier()));
            }
        });
        Map<String, Schema<?>> $$2 = new HashMap<>();
        for (SchemaComponent<?> $$3 : $$0) {
            $$2.put($$3.name(), $$3.schema().info());
        }
        DiscoverInfo $$4 = new DiscoverInfo("Minecraft Server JSON-RPC", SharedConstants.RPC_MANAGEMENT_SERVER_API_VERSION);
        return new DiscoverResponse(JsonRPCUtils.OPEN_RPC_VERSION, $$4, $$1, new DiscoverComponents($$2));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse.class */
    public static final class DiscoverResponse extends Record {
        private final String jsonRpcProtocolVersion;
        private final DiscoverInfo discoverInfo;
        private final List<MethodInfo.Named<?, ?>> methods;
        private final DiscoverComponents components;
        public static final MapCodec<DiscoverResponse> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.STRING.fieldOf("openrpc").forGetter((v0) -> {
                return v0.jsonRpcProtocolVersion();
            }), DiscoverInfo.CODEC.codec().fieldOf("info").forGetter((v0) -> {
                return v0.discoverInfo();
            }), Codec.list(MethodInfo.Named.CODEC).fieldOf("methods").forGetter((v0) -> {
                return v0.methods();
            }), DiscoverComponents.CODEC.codec().fieldOf("components").forGetter((v0) -> {
                return v0.components();
            })).apply($$0, DiscoverResponse::new);
        });

        public DiscoverResponse(String $$0, DiscoverInfo $$1, List<MethodInfo.Named<?, ?>> $$2, DiscoverComponents $$3) {
            this.jsonRpcProtocolVersion = $$0;
            this.discoverInfo = $$1;
            this.methods = $$2;
            this.components = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DiscoverResponse.class), DiscoverResponse.class, "jsonRpcProtocolVersion;discoverInfo;methods;components", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->jsonRpcProtocolVersion:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->discoverInfo:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->methods:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->components:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverComponents;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DiscoverResponse.class), DiscoverResponse.class, "jsonRpcProtocolVersion;discoverInfo;methods;components", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->jsonRpcProtocolVersion:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->discoverInfo:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->methods:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->components:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverComponents;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DiscoverResponse.class, Object.class), DiscoverResponse.class, "jsonRpcProtocolVersion;discoverInfo;methods;components", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->jsonRpcProtocolVersion:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->discoverInfo:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->methods:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverResponse;->components:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverComponents;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String jsonRpcProtocolVersion() {
            return this.jsonRpcProtocolVersion;
        }

        public DiscoverInfo discoverInfo() {
            return this.discoverInfo;
        }

        public List<MethodInfo.Named<?, ?>> methods() {
            return this.methods;
        }

        public DiscoverComponents components() {
            return this.components;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverComponents.class */
    public static final class DiscoverComponents extends Record {
        private final Map<String, Schema<?>> schemas;
        public static final MapCodec<DiscoverComponents> CODEC = typedSchema();

        public DiscoverComponents(Map<String, Schema<?>> $$0) {
            this.schemas = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DiscoverComponents.class), DiscoverComponents.class, "schemas", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverComponents;->schemas:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DiscoverComponents.class), DiscoverComponents.class, "schemas", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverComponents;->schemas:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DiscoverComponents.class, Object.class), DiscoverComponents.class, "schemas", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverComponents;->schemas:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<String, Schema<?>> schemas() {
            return this.schemas;
        }

        private static MapCodec<DiscoverComponents> typedSchema() {
            return RecordCodecBuilder.mapCodec($$0 -> {
                return $$0.group(Codec.unboundedMap(Codec.STRING, Schema.CODEC).fieldOf("schemas").forGetter((v0) -> {
                    return v0.schemas();
                })).apply($$0, DiscoverComponents::new);
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo.class */
    public static final class DiscoverInfo extends Record {
        private final String title;
        private final String version;
        public static final MapCodec<DiscoverInfo> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.STRING.fieldOf("title").forGetter((v0) -> {
                return v0.title();
            }), Codec.STRING.fieldOf("version").forGetter((v0) -> {
                return v0.version();
            })).apply($$0, DiscoverInfo::new);
        });

        public DiscoverInfo(String $$0, String $$1) {
            this.title = $$0;
            this.version = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DiscoverInfo.class), DiscoverInfo.class, "title;version", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;->title:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;->version:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DiscoverInfo.class), DiscoverInfo.class, "title;version", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;->title:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;->version:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DiscoverInfo.class, Object.class), DiscoverInfo.class, "title;version", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;->title:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/DiscoveryService$DiscoverInfo;->version:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String title() {
            return this.title;
        }

        public String version() {
            return this.version;
        }
    }
}
