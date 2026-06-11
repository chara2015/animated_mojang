package net.minecraft.data;

import com.google.common.base.Stopwatch;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.minecraft.WorldVersion;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.server.Bootstrap;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/DataGenerator.class */
public class DataGenerator {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path rootOutputFolder;
    private final PackOutput vanillaPackOutput;
    final Set<String> allProviderIds = new HashSet();
    final Map<String, DataProvider> providersToRun = new LinkedHashMap();
    private final WorldVersion version;
    private final boolean alwaysGenerate;

    static {
        Bootstrap.bootStrap();
    }

    public DataGenerator(Path $$0, WorldVersion $$1, boolean $$2) {
        this.rootOutputFolder = $$0;
        this.vanillaPackOutput = new PackOutput(this.rootOutputFolder);
        this.version = $$1;
        this.alwaysGenerate = $$2;
    }

    public void run() throws IOException {
        HashCache $$0 = new HashCache(this.rootOutputFolder, this.allProviderIds, this.version);
        Stopwatch $$1 = Stopwatch.createStarted();
        Stopwatch $$2 = Stopwatch.createUnstarted();
        this.providersToRun.forEach(($$22, $$3) -> {
            if (!this.alwaysGenerate && !$$0.shouldRunInThisVersion($$22)) {
                LOGGER.debug("Generator {} already run for version {}", $$22, this.version.name());
                return;
            }
            LOGGER.info("Starting provider: {}", $$22);
            $$2.start();
            Objects.requireNonNull($$3);
            $$0.applyUpdate($$0.generateUpdate($$22, $$3::run).join());
            $$2.stop();
            LOGGER.info("{} finished after {} ms", $$22, Long.valueOf($$2.elapsed(TimeUnit.MILLISECONDS)));
            $$2.reset();
        });
        LOGGER.info("All providers took: {} ms", Long.valueOf($$1.elapsed(TimeUnit.MILLISECONDS)));
        $$0.purgeStaleAndWrite();
    }

    public PackGenerator getVanillaPack(boolean $$0) {
        return new PackGenerator($$0, "vanilla", this.vanillaPackOutput);
    }

    public PackGenerator getBuiltinDatapack(boolean $$0, String $$1) {
        Path $$2 = this.vanillaPackOutput.getOutputFolder(PackOutput.Target.DATA_PACK).resolve("minecraft").resolve("datapacks").resolve($$1);
        return new PackGenerator($$0, $$1, new PackOutput($$2));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/DataGenerator$PackGenerator.class */
    public class PackGenerator {
        private final boolean toRun;
        private final String providerPrefix;
        private final PackOutput output;

        PackGenerator(boolean $$1, String $$2, PackOutput $$3) {
            this.toRun = $$1;
            this.providerPrefix = $$2;
            this.output = $$3;
        }

        public <T extends DataProvider> T addProvider(DataProvider.Factory<T> factory) {
            T t = (T) factory.create(this.output);
            String str = this.providerPrefix + "/" + t.getName();
            if (!DataGenerator.this.allProviderIds.add(str)) {
                throw new IllegalStateException("Duplicate provider: " + str);
            }
            if (this.toRun) {
                DataGenerator.this.providersToRun.put(str, t);
            }
            return t;
        }
    }
}
