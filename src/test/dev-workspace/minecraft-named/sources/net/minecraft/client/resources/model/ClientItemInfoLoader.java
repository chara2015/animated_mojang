package net.minecraft.client.resources.model;

import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.multiplayer.ClientRegistryLayer;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.PlaceholderLookupProvider;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.util.Util;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ClientItemInfoLoader.class */
public class ClientItemInfoLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter LISTER = FileToIdConverter.json("items");

    public static CompletableFuture<LoadedClientInfos> scheduleLoad(ResourceManager $$0, Executor $$1) {
        RegistryAccess.Frozen $$2 = ClientRegistryLayer.createRegistryAccess().compositeAccess();
        return CompletableFuture.supplyAsync(() -> {
            return LISTER.listMatchingResources($$0);
        }, $$1).thenCompose($$22 -> {
            List<CompletableFuture<PendingLoad>> $$3 = new ArrayList<>($$22.size());
            $$22.forEach(($$32, $$4) -> {
                $$3.add(CompletableFuture.supplyAsync(() -> {
                    Identifier $$32 = LISTER.fileToId($$32);
                    try {
                        Reader $$4 = $$4.openAsReader();
                        try {
                            PlaceholderLookupProvider $$5 = new PlaceholderLookupProvider($$2);
                            DynamicOps<JsonElement> $$6 = $$5.createSerializationContext(JsonOps.INSTANCE);
                            ClientItem $$7 = (ClientItem) ClientItem.CODEC.parse($$6, StrictJsonParser.parse($$4)).ifError($$22 -> {
                                LOGGER.error("Couldn't parse item model '{}' from pack '{}': {}", new Object[]{$$32, $$4.sourcePackId(), $$22.message()});
                            }).result().map($$12 -> {
                                if ($$5.hasRegisteredPlaceholders()) {
                                    return $$12.withRegistrySwapper($$5.createSwapper());
                                }
                                return $$12;
                            }).orElse(null);
                            PendingLoad pendingLoad = new PendingLoad($$32, $$7);
                            if ($$4 != null) {
                                $$4.close();
                            }
                            return pendingLoad;
                        } finally {
                        }
                    } catch (Exception $$8) {
                        LOGGER.error("Failed to open item model {} from pack '{}'", new Object[]{$$32, $$4.sourcePackId(), $$8});
                        return new PendingLoad($$32, null);
                    }
                }, $$1));
            });
            return Util.sequence($$3).thenApply($$02 -> {
                Map<Identifier, ClientItem> $$12 = new HashMap<>();
                Iterator it = $$02.iterator();
                while (it.hasNext()) {
                    PendingLoad $$22 = (PendingLoad) it.next();
                    if ($$22.clientItemInfo != null) {
                        $$12.put($$22.id, $$22.clientItemInfo);
                    }
                }
                return new LoadedClientInfos($$12);
            });
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ClientItemInfoLoader$PendingLoad.class */
    static final class PendingLoad extends Record {
        private final Identifier id;
        private final ClientItem clientItemInfo;

        PendingLoad(Identifier $$0, ClientItem $$1) {
            this.id = $$0;
            this.clientItemInfo = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PendingLoad.class), PendingLoad.class, "id;clientItemInfo", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$PendingLoad;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$PendingLoad;->clientItemInfo:Lnet/minecraft/client/renderer/item/ClientItem;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PendingLoad.class), PendingLoad.class, "id;clientItemInfo", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$PendingLoad;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$PendingLoad;->clientItemInfo:Lnet/minecraft/client/renderer/item/ClientItem;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PendingLoad.class, Object.class), PendingLoad.class, "id;clientItemInfo", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$PendingLoad;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$PendingLoad;->clientItemInfo:Lnet/minecraft/client/renderer/item/ClientItem;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }

        public ClientItem clientItemInfo() {
            return this.clientItemInfo;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ClientItemInfoLoader$LoadedClientInfos.class */
    public static final class LoadedClientInfos extends Record {
        private final Map<Identifier, ClientItem> contents;

        public LoadedClientInfos(Map<Identifier, ClientItem> $$0) {
            this.contents = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LoadedClientInfos.class), LoadedClientInfos.class, "contents", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$LoadedClientInfos;->contents:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LoadedClientInfos.class), LoadedClientInfos.class, "contents", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$LoadedClientInfos;->contents:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LoadedClientInfos.class, Object.class), LoadedClientInfos.class, "contents", "FIELD:Lnet/minecraft/client/resources/model/ClientItemInfoLoader$LoadedClientInfos;->contents:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<Identifier, ClientItem> contents() {
            return this.contents;
        }
    }
}
