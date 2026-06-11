package net.minecraft.data.structures;

import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/structures/SnbtToNbt.class */
public class SnbtToNbt implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final PackOutput output;
    private final Iterable<Path> inputFolders;
    private final List<Filter> filters = Lists.newArrayList();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/structures/SnbtToNbt$Filter.class */
    @FunctionalInterface
    public interface Filter {
        CompoundTag apply(String str, CompoundTag compoundTag);
    }

    public SnbtToNbt(PackOutput $$0, Iterable<Path> $$1) {
        this.output = $$0;
        this.inputFolders = $$1;
    }

    public SnbtToNbt addFilter(Filter $$0) {
        this.filters.add($$0);
        return this;
    }

    private CompoundTag applyFilters(String $$0, CompoundTag $$1) {
        CompoundTag $$2 = $$1;
        for (Filter $$3 : this.filters) {
            $$2 = $$3.apply($$0, $$2);
        }
        return $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/structures/SnbtToNbt$TaskResult.class */
    static final class TaskResult extends Record {
        private final String name;
        private final byte[] payload;
        private final HashCode hash;

        TaskResult(String $$0, byte[] $$1, HashCode $$2) {
            this.name = $$0;
            this.payload = $$1;
            this.hash = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TaskResult.class), TaskResult.class, "name;payload;hash", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->payload:[B", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->hash:Lcom/google/common/hash/HashCode;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TaskResult.class), TaskResult.class, "name;payload;hash", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->payload:[B", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->hash:Lcom/google/common/hash/HashCode;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TaskResult.class, Object.class), TaskResult.class, "name;payload;hash", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->payload:[B", "FIELD:Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;->hash:Lcom/google/common/hash/HashCode;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public byte[] payload() {
            return this.payload;
        }

        public HashCode hash() {
            return this.hash;
        }
    }

    @Override // net.minecraft.data.DataProvider
    public CompletableFuture<?> run(CachedOutput $$0) {
        Path $$1 = this.output.getOutputFolder();
        List<CompletableFuture<?>> $$2 = Lists.newArrayList();
        for (Path $$3 : this.inputFolders) {
            $$2.add(CompletableFuture.supplyAsync(() -> {
                try {
                    Stream<Path> $$32 = Files.walk($$3, new FileVisitOption[0]);
                    try {
                        CompletableFuture<Void> completableFutureAllOf = CompletableFuture.allOf((CompletableFuture[]) $$32.filter($$02 -> {
                            return $$02.toString().endsWith(".snbt");
                        }).map($$33 -> {
                            return CompletableFuture.runAsync(() -> {
                                TaskResult $$4 = readStructure($$33, getName($$3, $$33));
                                storeStructureIfChanged($$0, $$4, $$1);
                            }, Util.backgroundExecutor().forName("SnbtToNbt"));
                        }).toArray($$03 -> {
                            return new CompletableFuture[$$03];
                        }));
                        if ($$32 != null) {
                            $$32.close();
                        }
                        return completableFutureAllOf;
                    } finally {
                    }
                } catch (Exception $$4) {
                    throw new RuntimeException("Failed to read structure input directory, aborting", $$4);
                }
            }, Util.backgroundExecutor().forName("SnbtToNbt")).thenCompose($$02 -> {
                return $$02;
            }));
        }
        return Util.sequenceFailFast($$2);
    }

    @Override // net.minecraft.data.DataProvider
    public final String getName() {
        return "SNBT -> NBT";
    }

    private String getName(Path $$0, Path $$1) {
        String $$2 = $$0.relativize($$1).toString().replaceAll("\\\\", "/");
        return $$2.substring(0, $$2.length() - ".snbt".length());
    }

    private TaskResult readStructure(Path $$0, String $$1) {
        try {
            BufferedReader $$2 = Files.newBufferedReader($$0);
            try {
                String $$3 = IOUtils.toString($$2);
                CompoundTag $$4 = applyFilters($$1, NbtUtils.snbtToStructure($$3));
                ByteArrayOutputStream $$5 = new ByteArrayOutputStream();
                HashingOutputStream $$6 = new HashingOutputStream(Hashing.sha1(), $$5);
                NbtIo.writeCompressed($$4, (OutputStream) $$6);
                byte[] $$7 = $$5.toByteArray();
                HashCode $$8 = $$6.hash();
                TaskResult taskResult = new TaskResult($$1, $$7, $$8);
                if ($$2 != null) {
                    $$2.close();
                }
                return taskResult;
            } finally {
            }
        } catch (Throwable $$9) {
            throw new StructureConversionException($$0, $$9);
        }
    }

    private void storeStructureIfChanged(CachedOutput $$0, TaskResult $$1, Path $$2) {
        Path $$3 = $$2.resolve($$1.name + ".nbt");
        try {
            $$0.writeIfNeeded($$3, $$1.payload, $$1.hash);
        } catch (IOException $$4) {
            LOGGER.error("Couldn't write structure {} at {}", new Object[]{$$1.name, $$3, $$4});
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/structures/SnbtToNbt$StructureConversionException.class */
    static class StructureConversionException extends RuntimeException {
        public StructureConversionException(Path $$0, Throwable $$1) {
            super($$0.toAbsolutePath().toString(), $$1);
        }
    }
}
