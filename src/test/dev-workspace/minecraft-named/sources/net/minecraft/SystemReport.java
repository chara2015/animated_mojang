package net.minecraft;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.lang.management.ManagementFactory;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/SystemReport.class */
public class SystemReport {
    public static final long BYTES_PER_MEBIBYTE = 1048576;
    private static final long ONE_GIGA = 1000000000;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String OPERATING_SYSTEM = System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    private static final String JAVA_VERSION = System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    private static final String JAVA_VM_VERSION = System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    private final Map<String, String> entries = Maps.newLinkedHashMap();

    public SystemReport() {
        setDetail("Minecraft Version", SharedConstants.getCurrentVersion().name());
        setDetail("Minecraft Version ID", SharedConstants.getCurrentVersion().id());
        setDetail("Operating System", OPERATING_SYSTEM);
        setDetail("Java Version", JAVA_VERSION);
        setDetail("Java VM Version", JAVA_VM_VERSION);
        setDetail("Memory", () -> {
            Runtime $$0 = Runtime.getRuntime();
            long $$1 = $$0.maxMemory();
            long $$2 = $$0.totalMemory();
            long $$3 = $$0.freeMemory();
            long j = $$1 / BYTES_PER_MEBIBYTE;
            long j2 = $$2 / BYTES_PER_MEBIBYTE;
            long $$6 = $$3 / BYTES_PER_MEBIBYTE;
            return $$3 + " bytes (" + $$3 + " MiB) / " + $$6 + " bytes (" + $$3 + " MiB) up to " + $$2 + " bytes (" + $$3 + " MiB)";
        });
        setDetail("CPUs", () -> {
            return String.valueOf(Runtime.getRuntime().availableProcessors());
        });
        ignoreErrors("hardware", () -> {
            putHardware(new SystemInfo());
        });
        setDetail("JVM Flags", () -> {
            return printJvmFlags($$0 -> {
                return $$0.startsWith("-X");
            });
        });
        setDetail("Debug Flags", () -> {
            return printJvmFlags($$0 -> {
                return $$0.startsWith("-DMC_DEBUG_");
            });
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String printJvmFlags(Predicate<String> $$0) {
        List<String> $$1 = ManagementFactory.getRuntimeMXBean().getInputArguments();
        List<String> $$2 = $$1.stream().filter($$0).toList();
        return String.format(Locale.ROOT, "%d total; %s", Integer.valueOf($$2.size()), String.join(" ", $$2));
    }

    public void setDetail(String $$0, String $$1) {
        this.entries.put($$0, $$1);
    }

    public void setDetail(String $$0, Supplier<String> $$1) {
        try {
            setDetail($$0, $$1.get());
        } catch (Exception $$2) {
            LOGGER.warn("Failed to get system info for {}", $$0, $$2);
            setDetail($$0, "ERR");
        }
    }

    private void putHardware(SystemInfo $$0) {
        HardwareAbstractionLayer $$1 = $$0.getHardware();
        ignoreErrors("processor", () -> {
            putProcessor($$1.getProcessor());
        });
        ignoreErrors("graphics", () -> {
            putGraphics($$1.getGraphicsCards());
        });
        ignoreErrors("memory", () -> {
            putMemory($$1.getMemory());
        });
        ignoreErrors("storage", this::putStorage);
    }

    private void ignoreErrors(String $$0, Runnable $$1) {
        try {
            $$1.run();
        } catch (Throwable $$2) {
            LOGGER.warn("Failed retrieving info for group {}", $$0, $$2);
        }
    }

    public static float sizeInMiB(long $$0) {
        return $$0 / 1048576.0f;
    }

    private void putPhysicalMemory(List<PhysicalMemory> $$0) {
        int $$1 = 0;
        for (PhysicalMemory $$2 : $$0) {
            int i = $$1;
            $$1++;
            String $$3 = String.format(Locale.ROOT, "Memory slot #%d ", Integer.valueOf(i));
            setDetail($$3 + "capacity (MiB)", () -> {
                return String.format(Locale.ROOT, "%.2f", Float.valueOf(sizeInMiB($$2.getCapacity())));
            });
            setDetail($$3 + "clockSpeed (GHz)", () -> {
                return String.format(Locale.ROOT, "%.2f", Float.valueOf($$2.getClockSpeed() / 1.0E9f));
            });
            Objects.requireNonNull($$2);
            setDetail($$3 + "type", $$2::getMemoryType);
        }
    }

    private void putVirtualMemory(VirtualMemory $$0) {
        setDetail("Virtual memory max (MiB)", () -> {
            return String.format(Locale.ROOT, "%.2f", Float.valueOf(sizeInMiB($$0.getVirtualMax())));
        });
        setDetail("Virtual memory used (MiB)", () -> {
            return String.format(Locale.ROOT, "%.2f", Float.valueOf(sizeInMiB($$0.getVirtualInUse())));
        });
        setDetail("Swap memory total (MiB)", () -> {
            return String.format(Locale.ROOT, "%.2f", Float.valueOf(sizeInMiB($$0.getSwapTotal())));
        });
        setDetail("Swap memory used (MiB)", () -> {
            return String.format(Locale.ROOT, "%.2f", Float.valueOf(sizeInMiB($$0.getSwapUsed())));
        });
    }

    private void putMemory(GlobalMemory $$0) {
        ignoreErrors("physical memory", () -> {
            putPhysicalMemory($$0.getPhysicalMemory());
        });
        ignoreErrors("virtual memory", () -> {
            putVirtualMemory($$0.getVirtualMemory());
        });
    }

    private void putGraphics(List<GraphicsCard> $$0) {
        int $$1 = 0;
        for (GraphicsCard $$2 : $$0) {
            int i = $$1;
            $$1++;
            String $$3 = String.format(Locale.ROOT, "Graphics card #%d ", Integer.valueOf(i));
            Objects.requireNonNull($$2);
            setDetail($$3 + "name", $$2::getName);
            Objects.requireNonNull($$2);
            setDetail($$3 + "vendor", $$2::getVendor);
            setDetail($$3 + "VRAM (MiB)", () -> {
                return String.format(Locale.ROOT, "%.2f", Float.valueOf(sizeInMiB($$2.getVRam())));
            });
            Objects.requireNonNull($$2);
            setDetail($$3 + "deviceId", $$2::getDeviceId);
            Objects.requireNonNull($$2);
            setDetail($$3 + "versionInfo", $$2::getVersionInfo);
        }
    }

    private void putProcessor(CentralProcessor $$0) {
        CentralProcessor.ProcessorIdentifier $$1 = $$0.getProcessorIdentifier();
        Objects.requireNonNull($$1);
        setDetail("Processor Vendor", $$1::getVendor);
        Objects.requireNonNull($$1);
        setDetail("Processor Name", $$1::getName);
        Objects.requireNonNull($$1);
        setDetail("Identifier", $$1::getIdentifier);
        Objects.requireNonNull($$1);
        setDetail("Microarchitecture", $$1::getMicroarchitecture);
        setDetail("Frequency (GHz)", () -> {
            return String.format(Locale.ROOT, "%.2f", Float.valueOf($$1.getVendorFreq() / 1.0E9f));
        });
        setDetail("Number of physical packages", () -> {
            return String.valueOf($$0.getPhysicalPackageCount());
        });
        setDetail("Number of physical CPUs", () -> {
            return String.valueOf($$0.getPhysicalProcessorCount());
        });
        setDetail("Number of logical CPUs", () -> {
            return String.valueOf($$0.getLogicalProcessorCount());
        });
    }

    private void putStorage() {
        putSpaceForProperty("jna.tmpdir");
        putSpaceForProperty("org.lwjgl.system.SharedLibraryExtractPath");
        putSpaceForProperty("io.netty.native.workdir");
        putSpaceForProperty("java.io.tmpdir");
        putSpaceForPath("workdir", () -> {
            return "";
        });
    }

    private void putSpaceForProperty(String $$0) {
        putSpaceForPath($$0, () -> {
            return System.getProperty($$0);
        });
    }

    private void putSpaceForPath(String $$0, Supplier<String> $$1) {
        String $$2 = "Space in storage for " + $$0 + " (MiB)";
        try {
            String $$3 = $$1.get();
            if ($$3 == null) {
                setDetail($$2, "<path not set>");
            } else {
                FileStore $$4 = Files.getFileStore(Path.of($$3, new String[0]));
                setDetail($$2, String.format(Locale.ROOT, "available: %.2f, total: %.2f", Float.valueOf(sizeInMiB($$4.getUsableSpace())), Float.valueOf(sizeInMiB($$4.getTotalSpace()))));
            }
        } catch (InvalidPathException $$5) {
            LOGGER.warn("{} is not a path", $$0, $$5);
            setDetail($$2, "<invalid path>");
        } catch (Exception $$6) {
            LOGGER.warn("Failed retrieving storage space for {}", $$0, $$6);
            setDetail($$2, "ERR");
        }
    }

    public void appendToCrashReportString(StringBuilder $$0) {
        $$0.append("-- ").append("System Details").append(" --\n");
        $$0.append("Details:");
        this.entries.forEach(($$1, $$2) -> {
            $$0.append("\n\t");
            $$0.append($$1);
            $$0.append(": ");
            $$0.append($$2);
        });
    }

    public String toLineSeparatedString() {
        return (String) this.entries.entrySet().stream().map($$0 -> {
            return ((String) $$0.getKey()) + ": " + ((String) $$0.getValue());
        }).collect(Collectors.joining(System.lineSeparator()));
    }
}
