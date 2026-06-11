package net.minecraft.server.packs.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/FallbackResourceManager.class */
public class FallbackResourceManager implements ResourceManager {
    static final Logger LOGGER = LogUtils.getLogger();
    protected final List<PackEntry> fallbacks = Lists.newArrayList();
    private final PackType type;
    private final String namespace;

    public FallbackResourceManager(PackType $$0, String $$1) {
        this.type = $$0;
        this.namespace = $$1;
    }

    public void push(PackResources $$0) {
        pushInternal($$0.packId(), $$0, null);
    }

    public void push(PackResources $$0, Predicate<Identifier> $$1) {
        pushInternal($$0.packId(), $$0, $$1);
    }

    public void pushFilterOnly(String $$0, Predicate<Identifier> $$1) {
        pushInternal($$0, null, $$1);
    }

    private void pushInternal(String $$0, PackResources $$1, Predicate<Identifier> $$2) {
        this.fallbacks.add(new PackEntry($$0, $$1, $$2));
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Set<String> getNamespaces() {
        return ImmutableSet.of(this.namespace);
    }

    @Override // net.minecraft.server.packs.resources.ResourceProvider
    public Optional<Resource> getResource(Identifier $$0) {
        IoSupplier<InputStream> $$4;
        for (int $$1 = this.fallbacks.size() - 1; $$1 >= 0; $$1--) {
            PackEntry $$2 = this.fallbacks.get($$1);
            PackResources $$3 = $$2.resources;
            if ($$3 != null && ($$4 = $$3.getResource(this.type, $$0)) != null) {
                IoSupplier<ResourceMetadata> $$5 = createStackMetadataFinder($$0, $$1);
                return Optional.of(createResource($$3, $$0, $$4, $$5));
            }
            if ($$2.isFiltered($$0)) {
                LOGGER.warn("Resource {} not found, but was filtered by pack {}", $$0, $$2.name);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private static Resource createResource(PackResources $$0, Identifier $$1, IoSupplier<InputStream> $$2, IoSupplier<ResourceMetadata> $$3) {
        return new Resource($$0, wrapForDebug($$1, $$0, $$2), $$3);
    }

    private static IoSupplier<InputStream> wrapForDebug(Identifier $$0, PackResources $$1, IoSupplier<InputStream> $$2) {
        if (LOGGER.isDebugEnabled()) {
            return () -> {
                return new LeakedResourceWarningInputStream((InputStream) $$2.get(), $$0, $$1.packId());
            };
        }
        return $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/FallbackResourceManager$LeakedResourceWarningInputStream.class */
    static class LeakedResourceWarningInputStream extends FilterInputStream {
        private final Supplier<String> message;
        private boolean closed;

        public LeakedResourceWarningInputStream(InputStream $$0, Identifier $$1, String $$2) {
            super($$0);
            Exception $$3 = new Exception("Stacktrace");
            this.message = () -> {
                StringWriter $$32 = new StringWriter();
                $$3.printStackTrace(new PrintWriter($$32));
                return "Leaked resource: '" + String.valueOf($$1) + "' loaded from pack: '" + $$2 + "'\n" + String.valueOf($$32);
            };
        }

        @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            super.close();
            this.closed = true;
        }

        protected void finalize() throws Throwable {
            if (!this.closed) {
                FallbackResourceManager.LOGGER.warn("{}", this.message.get());
            }
            super.finalize();
        }
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public List<Resource> getResourceStack(Identifier $$0) {
        IoSupplier<InputStream> $$8;
        IoSupplier<ResourceMetadata> $$10;
        Identifier $$1 = getMetadataLocation($$0);
        List<Resource> $$2 = new ArrayList<>();
        boolean $$3 = false;
        String $$4 = null;
        int $$5 = this.fallbacks.size() - 1;
        while (true) {
            if ($$5 < 0) {
                break;
            }
            PackEntry $$6 = this.fallbacks.get($$5);
            PackResources $$7 = $$6.resources;
            if ($$7 != null && ($$8 = $$7.getResource(this.type, $$0)) != null) {
                if ($$3) {
                    $$10 = ResourceMetadata.EMPTY_SUPPLIER;
                } else {
                    $$10 = () -> {
                        IoSupplier<InputStream> $$22 = $$7.getResource(this.type, $$1);
                        return $$22 != null ? parseMetadata($$22) : ResourceMetadata.EMPTY;
                    };
                }
                $$2.add(new Resource($$7, $$8, $$10));
            }
            if ($$6.isFiltered($$0)) {
                $$4 = $$6.name;
                break;
            }
            if ($$6.isFiltered($$1)) {
                $$3 = true;
            }
            $$5--;
        }
        if ($$2.isEmpty() && $$4 != null) {
            LOGGER.warn("Resource {} not found, but was filtered by pack {}", $$0, $$4);
        }
        return Lists.reverse($$2);
    }

    private static boolean isMetadata(Identifier $$0) {
        return $$0.getPath().endsWith(PackResources.METADATA_EXTENSION);
    }

    private static Identifier getIdentifierFromMetadata(Identifier $$0) {
        String $$1 = $$0.getPath().substring(0, $$0.getPath().length() - PackResources.METADATA_EXTENSION.length());
        return $$0.withPath($$1);
    }

    static Identifier getMetadataLocation(Identifier $$0) {
        return $$0.withPath($$0.getPath() + ".mcmeta");
    }

    /* JADX INFO: renamed from: net.minecraft.server.packs.resources.FallbackResourceManager$1ResourceWithSourceAndIndex, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex.class */
    static final class C1ResourceWithSourceAndIndex extends Record {
        private final PackResources packResources;
        private final IoSupplier<InputStream> resource;
        private final int packIndex;

        C1ResourceWithSourceAndIndex(PackResources $$0, IoSupplier<InputStream> $$1, int $$2) {
            this.packResources = $$0;
            this.resource = $$1;
            this.packIndex = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, C1ResourceWithSourceAndIndex.class), C1ResourceWithSourceAndIndex.class, "packResources;resource;packIndex", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->packResources:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->resource:Lnet/minecraft/server/packs/resources/IoSupplier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->packIndex:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, C1ResourceWithSourceAndIndex.class), C1ResourceWithSourceAndIndex.class, "packResources;resource;packIndex", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->packResources:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->resource:Lnet/minecraft/server/packs/resources/IoSupplier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->packIndex:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, C1ResourceWithSourceAndIndex.class, Object.class), C1ResourceWithSourceAndIndex.class, "packResources;resource;packIndex", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->packResources:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->resource:Lnet/minecraft/server/packs/resources/IoSupplier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;->packIndex:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PackResources packResources() {
            return this.packResources;
        }

        public IoSupplier<InputStream> resource() {
            return this.resource;
        }

        public int packIndex() {
            return this.packIndex;
        }
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Map<Identifier, Resource> listResources(String $$0, Predicate<Identifier> $$1) {
        Map<Identifier, C1ResourceWithSourceAndIndex> $$2 = new HashMap<>();
        Map<Identifier, C1ResourceWithSourceAndIndex> $$3 = new HashMap<>();
        int $$4 = this.fallbacks.size();
        for (int $$5 = 0; $$5 < $$4; $$5++) {
            PackEntry $$6 = this.fallbacks.get($$5);
            $$6.filterAll($$2.keySet());
            $$6.filterAll($$3.keySet());
            PackResources $$7 = $$6.resources;
            if ($$7 != null) {
                int $$8 = $$5;
                $$7.listResources(this.type, this.namespace, $$0, ($$52, $$62) -> {
                    if (isMetadata($$52)) {
                        if ($$1.test(getIdentifierFromMetadata($$52))) {
                            $$3.put($$52, new C1ResourceWithSourceAndIndex($$7, $$62, $$8));
                        }
                    } else if ($$1.test($$52)) {
                        $$2.put($$52, new C1ResourceWithSourceAndIndex($$7, $$62, $$8));
                    }
                });
            }
        }
        Map<Identifier, Resource> $$9 = Maps.newTreeMap();
        $$2.forEach(($$22, $$32) -> {
            IoSupplier<ResourceMetadata> $$72;
            Identifier $$42 = getMetadataLocation($$22);
            C1ResourceWithSourceAndIndex $$53 = (C1ResourceWithSourceAndIndex) $$3.get($$42);
            if ($$53 != null && $$53.packIndex >= $$32.packIndex) {
                $$72 = convertToMetadata($$53.resource);
            } else {
                $$72 = ResourceMetadata.EMPTY_SUPPLIER;
            }
            $$9.put($$22, createResource($$32.packResources, $$22, $$32.resource, $$72));
        });
        return $$9;
    }

    private IoSupplier<ResourceMetadata> createStackMetadataFinder(Identifier $$0, int $$1) {
        return () -> {
            IoSupplier<InputStream> $$6;
            Identifier $$2 = getMetadataLocation($$0);
            for (int $$3 = this.fallbacks.size() - 1; $$3 >= $$1; $$3--) {
                PackEntry $$4 = this.fallbacks.get($$3);
                PackResources $$5 = $$4.resources;
                if ($$5 != null && ($$6 = $$5.getResource(this.type, $$2)) != null) {
                    return parseMetadata($$6);
                }
                if ($$4.isFiltered($$2)) {
                    break;
                }
            }
            return ResourceMetadata.EMPTY;
        };
    }

    private static IoSupplier<ResourceMetadata> convertToMetadata(IoSupplier<InputStream> $$0) {
        return () -> {
            return parseMetadata($$0);
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ResourceMetadata parseMetadata(IoSupplier<InputStream> $$0) throws IOException {
        InputStream $$1 = $$0.get();
        try {
            ResourceMetadata resourceMetadataFromJsonStream = ResourceMetadata.fromJsonStream($$1);
            if ($$1 != null) {
                $$1.close();
            }
            return resourceMetadataFromJsonStream;
        } catch (Throwable th) {
            if ($$1 != null) {
                try {
                    $$1.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/FallbackResourceManager$EntryStack.class */
    static final class EntryStack extends Record {
        private final Identifier fileLocation;
        private final Identifier metadataLocation;
        private final List<ResourceWithSource> fileSources;
        private final Map<PackResources, IoSupplier<InputStream>> metaSources;

        private EntryStack(Identifier $$0, Identifier $$1, List<ResourceWithSource> $$2, Map<PackResources, IoSupplier<InputStream>> $$3) {
            this.fileLocation = $$0;
            this.metadataLocation = $$1;
            this.fileSources = $$2;
            this.metaSources = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EntryStack.class), EntryStack.class, "fileLocation;metadataLocation;fileSources;metaSources", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->fileLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->metadataLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->fileSources:Ljava/util/List;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->metaSources:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EntryStack.class), EntryStack.class, "fileLocation;metadataLocation;fileSources;metaSources", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->fileLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->metadataLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->fileSources:Ljava/util/List;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->metaSources:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EntryStack.class, Object.class), EntryStack.class, "fileLocation;metadataLocation;fileSources;metaSources", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->fileLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->metadataLocation:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->fileSources:Ljava/util/List;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;->metaSources:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier fileLocation() {
            return this.fileLocation;
        }

        public Identifier metadataLocation() {
            return this.metadataLocation;
        }

        public List<ResourceWithSource> fileSources() {
            return this.fileSources;
        }

        public Map<PackResources, IoSupplier<InputStream>> metaSources() {
            return this.metaSources;
        }

        EntryStack(Identifier $$0) {
            this($$0, FallbackResourceManager.getMetadataLocation($$0), new ArrayList(), new Object2ObjectArrayMap());
        }
    }

    private static void applyPackFiltersToExistingResources(PackEntry $$0, Map<Identifier, EntryStack> $$1) {
        for (EntryStack $$2 : $$1.values()) {
            if ($$0.isFiltered($$2.fileLocation)) {
                $$2.fileSources.clear();
            } else if ($$0.isFiltered($$2.metadataLocation())) {
                $$2.metaSources.clear();
            }
        }
    }

    private void listPackResources(PackEntry $$0, String $$1, Predicate<Identifier> $$2, Map<Identifier, EntryStack> $$3) {
        PackResources $$4 = $$0.resources;
        if ($$4 == null) {
            return;
        }
        $$4.listResources(this.type, this.namespace, $$1, ($$32, $$42) -> {
            if (isMetadata($$32)) {
                Identifier $$5 = getIdentifierFromMetadata($$32);
                if (!$$2.test($$5)) {
                    return;
                }
                ((EntryStack) $$3.computeIfAbsent($$5, EntryStack::new)).metaSources.put($$4, $$42);
                return;
            }
            if (!$$2.test($$32)) {
                return;
            }
            ((EntryStack) $$3.computeIfAbsent($$32, EntryStack::new)).fileSources.add(new ResourceWithSource($$4, $$42));
        });
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Map<Identifier, List<Resource>> listResourceStacks(String $$0, Predicate<Identifier> $$1) {
        Map<Identifier, EntryStack> $$2 = Maps.newHashMap();
        for (PackEntry $$3 : this.fallbacks) {
            applyPackFiltersToExistingResources($$3, $$2);
            listPackResources($$3, $$0, $$1, $$2);
        }
        TreeMap<Identifier, List<Resource>> $$4 = Maps.newTreeMap();
        for (EntryStack $$5 : $$2.values()) {
            if (!$$5.fileSources.isEmpty()) {
                List<Resource> $$6 = new ArrayList<>();
                for (ResourceWithSource $$7 : $$5.fileSources) {
                    PackResources $$8 = $$7.source;
                    IoSupplier<InputStream> $$9 = $$5.metaSources.get($$8);
                    IoSupplier<ResourceMetadata> $$10 = $$9 != null ? convertToMetadata($$9) : ResourceMetadata.EMPTY_SUPPLIER;
                    $$6.add(createResource($$8, $$5.fileLocation, $$7.resource, $$10));
                }
                $$4.put($$5.fileLocation, $$6);
            }
        }
        return $$4;
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Stream<PackResources> listPacks() {
        return this.fallbacks.stream().map($$0 -> {
            return $$0.resources;
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/FallbackResourceManager$PackEntry.class */
    static final class PackEntry extends Record {
        private final String name;
        private final PackResources resources;
        private final Predicate<Identifier> filter;

        PackEntry(String $$0, PackResources $$1, Predicate<Identifier> $$2) {
            this.name = $$0;
            this.resources = $$1;
            this.filter = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PackEntry.class), PackEntry.class, "name;resources;filter", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->resources:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->filter:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PackEntry.class), PackEntry.class, "name;resources;filter", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->resources:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->filter:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PackEntry.class, Object.class), PackEntry.class, "name;resources;filter", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->resources:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;->filter:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public PackResources resources() {
            return this.resources;
        }

        public Predicate<Identifier> filter() {
            return this.filter;
        }

        public void filterAll(Collection<Identifier> $$0) {
            if (this.filter != null) {
                $$0.removeIf(this.filter);
            }
        }

        public boolean isFiltered(Identifier $$0) {
            return this.filter != null && this.filter.test($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource.class */
    static final class ResourceWithSource extends Record {
        private final PackResources source;
        private final IoSupplier<InputStream> resource;

        ResourceWithSource(PackResources $$0, IoSupplier<InputStream> $$1) {
            this.source = $$0;
            this.resource = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ResourceWithSource.class), ResourceWithSource.class, "source;resource", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;->source:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;->resource:Lnet/minecraft/server/packs/resources/IoSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ResourceWithSource.class), ResourceWithSource.class, "source;resource", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;->source:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;->resource:Lnet/minecraft/server/packs/resources/IoSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ResourceWithSource.class, Object.class), ResourceWithSource.class, "source;resource", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;->source:Lnet/minecraft/server/packs/PackResources;", "FIELD:Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;->resource:Lnet/minecraft/server/packs/resources/IoSupplier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PackResources source() {
            return this.source;
        }

        public IoSupplier<InputStream> resource() {
            return this.resource;
        }
    }
}
