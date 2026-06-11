package net.labymod.core.client.os.windows.util;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/util/Kernel32Util.class */
public class Kernel32Util {
    private static final Kernel32 KERNEL32 = Kernel32.INSTANCE;

    private Kernel32Util() {
    }

    public static WinDef.HMODULE getModuleHandle(List<String> names) {
        for (String name : names) {
            WinDef.HMODULE moduleHandle = KERNEL32.GetModuleHandle(name);
            if (moduleHandle != null) {
                return moduleHandle;
            }
        }
        return null;
    }

    public static WinNT.HANDLE getCurrentProcess() {
        return KERNEL32.GetCurrentProcess();
    }

    public static int getCurrentProcessId() {
        return KERNEL32.GetCurrentProcessId();
    }

    public static List<String> getModuleNames(int pid) {
        List<String> names = new ArrayList<>();
        for (Tlhelp32.MODULEENTRY32W module : getWin32Modules(pid)) {
            names.add(module.szModule());
        }
        return Collections.unmodifiableList(names);
    }

    public static List<LoadedModule> getModules(int pid) {
        return getModules(pid, m -> {
            return true;
        });
    }

    public static List<LoadedModule> getModules(int pid, Predicate<LoadedModule> filter) {
        List<LoadedModule> modules = new ArrayList<>();
        for (Tlhelp32.MODULEENTRY32W module : getWin32Modules(pid)) {
            LoadedModule m = new LoadedModule(module.szModule(), module.szExePath());
            if (filter.test(m)) {
                modules.add(m);
            }
        }
        return modules;
    }

    public static List<Tlhelp32.MODULEENTRY32W> getWin32Modules(int pid) {
        return com.sun.jna.platform.win32.Kernel32Util.getModules(pid);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/util/Kernel32Util$LoadedModule.class */
    public static final class LoadedModule extends Record {
        private final String name;
        private final String path;

        public LoadedModule(String name, String path) {
            this.name = name;
            this.path = path;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LoadedModule.class), LoadedModule.class, "name;path", "FIELD:Lnet/labymod/core/client/os/windows/util/Kernel32Util$LoadedModule;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/os/windows/util/Kernel32Util$LoadedModule;->path:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LoadedModule.class), LoadedModule.class, "name;path", "FIELD:Lnet/labymod/core/client/os/windows/util/Kernel32Util$LoadedModule;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/os/windows/util/Kernel32Util$LoadedModule;->path:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LoadedModule.class, Object.class), LoadedModule.class, "name;path", "FIELD:Lnet/labymod/core/client/os/windows/util/Kernel32Util$LoadedModule;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/os/windows/util/Kernel32Util$LoadedModule;->path:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public String path() {
            return this.path;
        }
    }
}
