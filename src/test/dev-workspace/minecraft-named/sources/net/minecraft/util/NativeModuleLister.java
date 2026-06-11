package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.Version;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.CrashReportCategory;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/NativeModuleLister.class */
public class NativeModuleLister {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int LANG_MASK = 65535;
    private static final int DEFAULT_LANG = 1033;
    private static final int CODEPAGE_MASK = -65536;
    private static final int DEFAULT_CODEPAGE = 78643200;

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
    public static List<NativeModuleInfo> listModules() throws Win32Exception {
        if (!Platform.isWindows()) {
            return ImmutableList.of();
        }
        int $$0 = Kernel32.INSTANCE.GetCurrentProcessId();
        ImmutableList.Builder<NativeModuleInfo> $$1 = ImmutableList.builder();
        List<Tlhelp32.MODULEENTRY32W> $$2 = Kernel32Util.getModules($$0);
        for (Tlhelp32.MODULEENTRY32W $$3 : $$2) {
            String $$4 = $$3.szModule();
            Optional<NativeModuleVersion> $$5 = tryGetVersion($$3.szExePath());
            $$1.add(new NativeModuleInfo($$4, $$5));
        }
        return $$1.build();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
    private static Optional<NativeModuleVersion> tryGetVersion(String $$0) throws Win32Exception {
        try {
            IntByReference $$1 = new IntByReference();
            int $$2 = Version.INSTANCE.GetFileVersionInfoSize($$0, $$1);
            if ($$2 == 0) {
                int $$3 = Native.getLastError();
                if ($$3 == 1813 || $$3 == 1812) {
                    return Optional.empty();
                }
                throw new Win32Exception($$3);
            }
            Memory memory = new Memory($$2);
            if (!Version.INSTANCE.GetFileVersionInfo($$0, 0, $$2, memory)) {
                throw new Win32Exception(Native.getLastError());
            }
            IntByReference $$5 = new IntByReference();
            Pointer $$6 = queryVersionValue(memory, "\\VarFileInfo\\Translation", $$5);
            int[] $$7 = $$6.getIntArray(0L, $$5.getValue() / 4);
            OptionalInt $$8 = findLangAndCodepage($$7);
            if ($$8.isEmpty()) {
                return Optional.empty();
            }
            int $$9 = $$8.getAsInt();
            int $$10 = $$9 & LANG_MASK;
            int $$11 = ($$9 & (-65536)) >> 16;
            String $$12 = queryVersionString(memory, langTableKey("FileDescription", $$10, $$11), $$5);
            String $$13 = queryVersionString(memory, langTableKey("CompanyName", $$10, $$11), $$5);
            String $$14 = queryVersionString(memory, langTableKey("FileVersion", $$10, $$11), $$5);
            return Optional.of(new NativeModuleVersion($$12, $$14, $$13));
        } catch (Exception $$15) {
            LOGGER.info("Failed to find module info for {}", $$0, $$15);
            return Optional.empty();
        }
    }

    private static String langTableKey(String $$0, int $$1, int $$2) {
        return String.format(Locale.ROOT, "\\StringFileInfo\\%04x%04x\\%s", Integer.valueOf($$1), Integer.valueOf($$2), $$0);
    }

    private static OptionalInt findLangAndCodepage(int[] $$0) {
        OptionalInt $$1 = OptionalInt.empty();
        for (int $$2 : $$0) {
            if (($$2 & (-65536)) == DEFAULT_CODEPAGE && ($$2 & LANG_MASK) == 1033) {
                return OptionalInt.of($$2);
            }
            $$1 = OptionalInt.of($$2);
        }
        return $$1;
    }

    private static Pointer queryVersionValue(Pointer $$0, String $$1, IntByReference $$2) {
        PointerByReference $$3 = new PointerByReference();
        if (!Version.INSTANCE.VerQueryValue($$0, $$1, $$3, $$2)) {
            throw new UnsupportedOperationException("Can't get version value " + $$1);
        }
        return $$3.getValue();
    }

    private static String queryVersionString(Pointer $$0, String $$1, IntByReference $$2) {
        try {
            Pointer $$3 = queryVersionValue($$0, $$1, $$2);
            byte[] $$4 = $$3.getByteArray(0L, ($$2.getValue() - 1) * 2);
            return new String($$4, StandardCharsets.UTF_16LE);
        } catch (Exception e) {
            return "";
        }
    }

    public static void addCrashSection(CrashReportCategory $$0) {
        $$0.setDetail("Modules", () -> {
            return (String) listModules().stream().sorted(Comparator.comparing($$02 -> {
                return $$02.name;
            })).map($$03 -> {
                return "\n\t\t" + String.valueOf($$03);
            }).collect(Collectors.joining());
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/NativeModuleLister$NativeModuleVersion.class */
    public static class NativeModuleVersion {
        public final String description;
        public final String version;
        public final String company;

        public NativeModuleVersion(String $$0, String $$1, String $$2) {
            this.description = $$0;
            this.version = $$1;
            this.company = $$2;
        }

        public String toString() {
            return this.description + ":" + this.version + ":" + this.company;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/NativeModuleLister$NativeModuleInfo.class */
    public static class NativeModuleInfo {
        public final String name;
        public final Optional<NativeModuleVersion> version;

        public NativeModuleInfo(String $$0, Optional<NativeModuleVersion> $$1) {
            this.name = $$0;
            this.version = $$1;
        }

        public String toString() {
            return (String) this.version.map($$0 -> {
                return this.name + ":" + String.valueOf($$0);
            }).orElse(this.name);
        }
    }
}
