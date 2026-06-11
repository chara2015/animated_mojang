package net.minecraft.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.WorldVersion;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/HashCache.class */
public class HashCache {
    static final Logger LOGGER = LogUtils.getLogger();
    private static final String HEADER_MARKER = "// ";
    private final Path rootDir;
    private final Path cacheDir;
    private final String versionId;
    private final Map<String, ProviderCache> caches;
    private final Set<String> cachesToWrite = new HashSet();
    final Set<Path> cachePaths = new HashSet();
    private final int initialCount;
    private int writes;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/HashCache$UpdateFunction.class */
    @FunctionalInterface
    public interface UpdateFunction {
        CompletableFuture<?> update(CachedOutput cachedOutput);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/HashCache$ProviderCache.class */
    static final class ProviderCache extends Record {
        private final String version;
        private final ImmutableMap<Path, HashCode> data;

        ProviderCache(String $$0, ImmutableMap<Path, HashCode> $$1) {
            this.version = $$0;
            this.data = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ProviderCache.class), ProviderCache.class, "version;data", "FIELD:Lnet/minecraft/data/HashCache$ProviderCache;->version:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$ProviderCache;->data:Lcom/google/common/collect/ImmutableMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ProviderCache.class), ProviderCache.class, "version;data", "FIELD:Lnet/minecraft/data/HashCache$ProviderCache;->version:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$ProviderCache;->data:Lcom/google/common/collect/ImmutableMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ProviderCache.class, Object.class), ProviderCache.class, "version;data", "FIELD:Lnet/minecraft/data/HashCache$ProviderCache;->version:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$ProviderCache;->data:Lcom/google/common/collect/ImmutableMap;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String version() {
            return this.version;
        }

        public ImmutableMap<Path, HashCode> data() {
            return this.data;
        }

        public HashCode get(Path $$0) {
            return (HashCode) this.data.get($$0);
        }

        public int count() {
            return this.data.size();
        }

        public static ProviderCache load(Path $$0, Path $$1) throws IOException {
            BufferedReader $$2 = Files.newBufferedReader($$1, StandardCharsets.UTF_8);
            try {
                String $$3 = $$2.readLine();
                if (!$$3.startsWith(HashCache.HEADER_MARKER)) {
                    throw new IllegalStateException("Missing cache file header");
                }
                String[] $$4 = $$3.substring(HashCache.HEADER_MARKER.length()).split("\t", 2);
                String $$5 = $$4[0];
                ImmutableMap.Builder<Path, HashCode> $$6 = ImmutableMap.builder();
                $$2.lines().forEach($$22 -> {
                    int $$32 = $$22.indexOf(32);
                    $$6.put($$0.resolve($$22.substring($$32 + 1)), HashCode.fromString($$22.substring(0, $$32)));
                });
                ProviderCache providerCache = new ProviderCache($$5, $$6.build());
                if ($$2 != null) {
                    $$2.close();
                }
                return providerCache;
            } catch (Throwable th) {
                if ($$2 != null) {
                    try {
                        $$2.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void save(Path $$0, Path $$1, String $$2) {
            try {
                BufferedWriter $$3 = Files.newBufferedWriter($$1, StandardCharsets.UTF_8, new OpenOption[0]);
                try {
                    $$3.write(HashCache.HEADER_MARKER);
                    $$3.write(this.version);
                    $$3.write(9);
                    $$3.write($$2);
                    $$3.newLine();
                    UnmodifiableIterator it = this.data.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Path, HashCode> $$4 = (Map.Entry) it.next();
                        $$3.write($$4.getValue().toString());
                        $$3.write(32);
                        $$3.write($$0.relativize($$4.getKey()).toString());
                        $$3.newLine();
                    }
                    if ($$3 != null) {
                        $$3.close();
                    }
                } finally {
                }
            } catch (IOException $$5) {
                HashCache.LOGGER.warn("Unable write cachefile {}: {}", $$1, $$5);
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/HashCache$ProviderCacheBuilder.class */
    static final class ProviderCacheBuilder extends Record {
        private final String version;
        private final ConcurrentMap<Path, HashCode> data;

        private ProviderCacheBuilder(String $$0, ConcurrentMap<Path, HashCode> $$1) {
            this.version = $$0;
            this.data = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ProviderCacheBuilder.class), ProviderCacheBuilder.class, "version;data", "FIELD:Lnet/minecraft/data/HashCache$ProviderCacheBuilder;->version:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$ProviderCacheBuilder;->data:Ljava/util/concurrent/ConcurrentMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ProviderCacheBuilder.class), ProviderCacheBuilder.class, "version;data", "FIELD:Lnet/minecraft/data/HashCache$ProviderCacheBuilder;->version:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$ProviderCacheBuilder;->data:Ljava/util/concurrent/ConcurrentMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ProviderCacheBuilder.class, Object.class), ProviderCacheBuilder.class, "version;data", "FIELD:Lnet/minecraft/data/HashCache$ProviderCacheBuilder;->version:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$ProviderCacheBuilder;->data:Ljava/util/concurrent/ConcurrentMap;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String version() {
            return this.version;
        }

        public ConcurrentMap<Path, HashCode> data() {
            return this.data;
        }

        ProviderCacheBuilder(String $$0) {
            this($$0, new ConcurrentHashMap());
        }

        public void put(Path $$0, HashCode $$1) {
            this.data.put($$0, $$1);
        }

        public ProviderCache build() {
            return new ProviderCache(this.version, ImmutableMap.copyOf(this.data));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/HashCache$CacheUpdater.class */
    static class CacheUpdater implements CachedOutput {
        private final String provider;
        private final ProviderCache oldCache;
        private final ProviderCacheBuilder newCache;
        private final AtomicInteger writes = new AtomicInteger();
        private volatile boolean closed;

        CacheUpdater(String $$0, String $$1, ProviderCache $$2) {
            this.provider = $$0;
            this.oldCache = $$2;
            this.newCache = new ProviderCacheBuilder($$1);
        }

        private boolean shouldWrite(Path $$0, HashCode $$1) {
            return (Objects.equals(this.oldCache.get($$0), $$1) && Files.exists($$0, new LinkOption[0])) ? false : true;
        }

        @Override // net.minecraft.data.CachedOutput
        public void writeIfNeeded(Path $$0, byte[] $$1, HashCode $$2) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("Cannot write to cache as it has already been closed");
            }
            if (shouldWrite($$0, $$2)) {
                this.writes.incrementAndGet();
                Files.createDirectories($$0.getParent(), new FileAttribute[0]);
                Files.write($$0, $$1, new OpenOption[0]);
            }
            this.newCache.put($$0, $$2);
        }

        public UpdateResult close() {
            this.closed = true;
            return new UpdateResult(this.provider, this.newCache.build(), this.writes.get());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/HashCache$UpdateResult.class */
    public static final class UpdateResult extends Record {
        private final String providerId;
        private final ProviderCache cache;
        private final int writes;

        public UpdateResult(String $$0, ProviderCache $$1, int $$2) {
            this.providerId = $$0;
            this.cache = $$1;
            this.writes = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UpdateResult.class), UpdateResult.class, "providerId;cache;writes", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->providerId:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->cache:Lnet/minecraft/data/HashCache$ProviderCache;", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->writes:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UpdateResult.class), UpdateResult.class, "providerId;cache;writes", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->providerId:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->cache:Lnet/minecraft/data/HashCache$ProviderCache;", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->writes:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UpdateResult.class, Object.class), UpdateResult.class, "providerId;cache;writes", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->providerId:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->cache:Lnet/minecraft/data/HashCache$ProviderCache;", "FIELD:Lnet/minecraft/data/HashCache$UpdateResult;->writes:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String providerId() {
            return this.providerId;
        }

        public ProviderCache cache() {
            return this.cache;
        }

        public int writes() {
            return this.writes;
        }
    }

    private Path getProviderCachePath(String $$0) {
        return this.cacheDir.resolve(Hashing.sha1().hashString($$0, StandardCharsets.UTF_8).toString());
    }

    public HashCache(Path $$0, Collection<String> $$1, WorldVersion $$2) throws IOException {
        this.versionId = $$2.id();
        this.rootDir = $$0;
        this.cacheDir = $$0.resolve(".cache");
        Files.createDirectories(this.cacheDir, new FileAttribute[0]);
        Map<String, ProviderCache> $$3 = new HashMap<>();
        int $$4 = 0;
        for (String $$5 : $$1) {
            Path $$6 = getProviderCachePath($$5);
            this.cachePaths.add($$6);
            ProviderCache $$7 = readCache($$0, $$6);
            $$3.put($$5, $$7);
            $$4 += $$7.count();
        }
        this.caches = $$3;
        this.initialCount = $$4;
    }

    private static ProviderCache readCache(Path $$0, Path $$1) {
        if (Files.isReadable($$1)) {
            try {
                return ProviderCache.load($$0, $$1);
            } catch (Exception $$2) {
                LOGGER.warn("Failed to parse cache {}, discarding", $$1, $$2);
            }
        }
        return new ProviderCache("unknown", ImmutableMap.of());
    }

    public boolean shouldRunInThisVersion(String $$0) {
        ProviderCache $$1 = this.caches.get($$0);
        return $$1 == null || !$$1.version.equals(this.versionId);
    }

    public CompletableFuture<UpdateResult> generateUpdate(String $$0, UpdateFunction $$1) {
        ProviderCache $$2 = this.caches.get($$0);
        if ($$2 == null) {
            throw new IllegalStateException("Provider not registered: " + $$0);
        }
        CacheUpdater $$3 = new CacheUpdater($$0, this.versionId, $$2);
        return $$1.update($$3).thenApply($$12 -> {
            return $$3.close();
        });
    }

    public void applyUpdate(UpdateResult $$0) {
        this.caches.put($$0.providerId(), $$0.cache());
        this.cachesToWrite.add($$0.providerId());
        this.writes += $$0.writes();
    }

    public void purgeStaleAndWrite() throws IOException {
        final Set<Path> $$0 = new HashSet<>();
        this.caches.forEach(($$1, $$2) -> {
            if (this.cachesToWrite.contains($$1)) {
                Path $$3 = getProviderCachePath($$1);
                $$2.save(this.rootDir, $$3, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(ZonedDateTime.now()) + "\t" + $$1);
            }
            $$0.addAll($$2.data().keySet());
        });
        $$0.add(this.rootDir.resolve("version.json"));
        final MutableInt $$12 = new MutableInt();
        final MutableInt $$22 = new MutableInt();
        Files.walkFileTree(this.rootDir, new SimpleFileVisitor<Path>() { // from class: net.minecraft.data.HashCache.1
            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            public FileVisitResult visitFile(Path $$02, BasicFileAttributes $$13) {
                if (HashCache.this.cachePaths.contains($$02)) {
                    return FileVisitResult.CONTINUE;
                }
                $$12.increment();
                if ($$0.contains($$02)) {
                    return FileVisitResult.CONTINUE;
                }
                try {
                    Files.delete($$02);
                } catch (IOException $$23) {
                    HashCache.LOGGER.warn("Failed to delete file {}", $$02, $$23);
                }
                $$22.increment();
                return FileVisitResult.CONTINUE;
            }
        });
        LOGGER.info("Caching: total files: {}, old count: {}, new count: {}, removed stale: {}, written: {}", new Object[]{$$12, Integer.valueOf(this.initialCount), Integer.valueOf($$0.size()), $$22, Integer.valueOf(this.writes)});
    }
}
