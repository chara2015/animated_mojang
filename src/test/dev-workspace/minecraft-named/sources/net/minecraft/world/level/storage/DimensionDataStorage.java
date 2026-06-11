package net.minecraft.world.level.storage;

import com.google.common.collect.Iterables;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.FastBufferedInputStream;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/DimensionDataStorage.class */
public class DimensionDataStorage implements AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final DataFixer fixerUpper;
    private final HolderLookup.Provider registries;
    private final Path dataFolder;
    private final Map<SavedDataType<?>, Optional<SavedData>> cache = new HashMap();
    private CompletableFuture<?> pendingWriteFuture = CompletableFuture.completedFuture(null);

    public DimensionDataStorage(Path $$0, DataFixer $$1, HolderLookup.Provider $$2) {
        this.fixerUpper = $$1;
        this.dataFolder = $$0;
        this.registries = $$2;
    }

    private Path getDataFile(String $$0) {
        return this.dataFolder.resolve($$0 + ".dat");
    }

    public <T extends SavedData> T computeIfAbsent(SavedDataType<T> savedDataType) {
        T t = (T) get(savedDataType);
        if (t != null) {
            return t;
        }
        T t2 = savedDataType.constructor().get();
        set(savedDataType, t2);
        return t2;
    }

    public <T extends SavedData> T get(SavedDataType<T> $$0) {
        Optional<SavedData> $$1 = this.cache.get($$0);
        if ($$1 == null) {
            $$1 = Optional.ofNullable(readSavedData($$0));
            this.cache.put($$0, $$1);
        }
        return (T) $$1.orElse(null);
    }

    private <T extends SavedData> T readSavedData(SavedDataType<T> $$0) {
        try {
            Path $$1 = getDataFile($$0.id());
            if (Files.exists($$1, new LinkOption[0])) {
                CompoundTag $$2 = readTagFromDisk($$0.id(), $$0.dataFixType(), SharedConstants.getCurrentVersion().dataVersion().version());
                RegistryOps<Tag> $$3 = this.registries.createSerializationContext(NbtOps.INSTANCE);
                return (T) $$0.codec().parse($$3, $$2.get("data")).resultOrPartial($$12 -> {
                    LOGGER.error("Failed to parse saved data for '{}': {}", $$0, $$12);
                }).orElse(null);
            }
            return null;
        } catch (Exception $$4) {
            LOGGER.error("Error loading saved data: {}", $$0, $$4);
            return null;
        }
    }

    public <T extends SavedData> void set(SavedDataType<T> $$0, T $$1) {
        this.cache.put($$0, Optional.of($$1));
        $$1.setDirty();
    }

    public CompoundTag readTagFromDisk(String $$0, DataFixTypes $$1, int $$2) throws IOException {
        CompoundTag $$8;
        InputStream $$3 = Files.newInputStream(getDataFile($$0), new OpenOption[0]);
        try {
            PushbackInputStream $$4 = new PushbackInputStream(new FastBufferedInputStream($$3), 2);
            try {
                if (isGzip($$4)) {
                    $$8 = NbtIo.readCompressed($$4, NbtAccounter.unlimitedHeap());
                } else {
                    DataInputStream $$6 = new DataInputStream($$4);
                    try {
                        $$8 = NbtIo.read($$6);
                        $$6.close();
                    } catch (Throwable th) {
                        try {
                            $$6.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                int $$9 = NbtUtils.getDataVersion($$8, 1343);
                CompoundTag compoundTagUpdate = $$1.update(this.fixerUpper, $$8, $$9, $$2);
                $$4.close();
                if ($$3 != null) {
                    $$3.close();
                }
                return compoundTagUpdate;
            } finally {
            }
        } catch (Throwable th3) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    private boolean isGzip(PushbackInputStream $$0) throws IOException {
        byte[] $$1 = new byte[2];
        boolean $$2 = false;
        int $$3 = $$0.read($$1, 0, 2);
        if ($$3 == 2) {
            int $$4 = (($$1[1] & 255) << 8) | ($$1[0] & 255);
            if ($$4 == 35615) {
                $$2 = true;
            }
        }
        if ($$3 != 0) {
            $$0.unread($$1, 0, $$3);
        }
        return $$2;
    }

    public CompletableFuture<?> scheduleSave() {
        Map<SavedDataType<?>, CompoundTag> $$0 = collectDirtyTagsToSave();
        if ($$0.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        int $$1 = Util.maxAllowedExecutorThreads();
        int $$2 = $$0.size();
        if ($$2 > $$1) {
            this.pendingWriteFuture = this.pendingWriteFuture.thenCompose($$3 -> {
                List<CompletableFuture<?>> $$4 = new ArrayList<>($$1);
                int $$5 = Mth.positiveCeilDiv($$2, $$1);
                for (List<Map.Entry<SavedDataType<?>, CompoundTag>> $$6 : Iterables.partition($$0.entrySet(), $$5)) {
                    $$4.add(CompletableFuture.runAsync(() -> {
                        Iterator it = $$6.iterator();
                        while (it.hasNext()) {
                            Map.Entry<SavedDataType<?>, CompoundTag> $$12 = (Map.Entry) it.next();
                            tryWrite($$12.getKey(), $$12.getValue());
                        }
                    }, Util.ioPool()));
                }
                return CompletableFuture.allOf((CompletableFuture[]) $$4.toArray($$02 -> {
                    return new CompletableFuture[$$02];
                }));
            });
        } else {
            this.pendingWriteFuture = this.pendingWriteFuture.thenCompose($$12 -> {
                return CompletableFuture.allOf((CompletableFuture[]) $$0.entrySet().stream().map($$02 -> {
                    return CompletableFuture.runAsync(() -> {
                        tryWrite((SavedDataType) $$02.getKey(), (CompoundTag) $$02.getValue());
                    }, Util.ioPool());
                }).toArray($$03 -> {
                    return new CompletableFuture[$$03];
                }));
            });
        }
        return this.pendingWriteFuture;
    }

    private Map<SavedDataType<?>, CompoundTag> collectDirtyTagsToSave() {
        Object2ObjectArrayMap object2ObjectArrayMap = new Object2ObjectArrayMap();
        RegistryOps<Tag> $$1 = this.registries.createSerializationContext(NbtOps.INSTANCE);
        this.cache.forEach(($$2, $$3) -> {
            $$3.filter((v0) -> {
                return v0.isDirty();
            }).ifPresent($$3 -> {
                object2ObjectArrayMap.put($$2, encodeUnchecked($$2, $$3, $$1));
                $$3.setDirty(false);
            });
        });
        return object2ObjectArrayMap;
    }

    private <T extends SavedData> CompoundTag encodeUnchecked(SavedDataType<T> $$0, SavedData $$1, RegistryOps<Tag> $$2) {
        Codec<T> $$3 = $$0.codec();
        CompoundTag $$4 = new CompoundTag();
        $$4.put("data", (Tag) $$3.encodeStart($$2, $$1).getOrThrow());
        NbtUtils.addCurrentDataVersion($$4);
        return $$4;
    }

    private void tryWrite(SavedDataType<?> $$0, CompoundTag $$1) {
        Path $$2 = getDataFile($$0.id());
        try {
            NbtIo.writeCompressed($$1, $$2);
        } catch (IOException $$3) {
            LOGGER.error("Could not save data to {}", $$2.getFileName(), $$3);
        }
    }

    public void saveAndJoin() {
        scheduleSave().join();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        saveAndJoin();
    }
}
