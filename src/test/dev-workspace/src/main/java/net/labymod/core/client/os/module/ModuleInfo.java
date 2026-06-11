package net.labymod.core.client.os.module;

import com.sun.jna.platform.win32.WinDef;
import java.util.List;
import net.labymod.core.client.os.windows.util.Kernel32Util;
import net.labymod.core.client.os.windows.util.PsapiUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleInfo.class */
public class ModuleInfo {
    private final List<String> names;

    private ModuleInfo(List<String> names) {
        this.names = names;
    }

    public static ModuleInfo of(String name) {
        return new ModuleInfo(List.of(name));
    }

    public static ModuleInfo of(String... names) {
        return new ModuleInfo(List.of((Object[]) names));
    }

    public boolean contains(String name) {
        for (String s : this.names) {
            if (s.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public String getFileName() {
        WinDef.HMODULE moduleHandle = Kernel32Util.getModuleHandle(this.names);
        if (moduleHandle == null) {
            return null;
        }
        return PsapiUtil.getModuleFileName(Kernel32Util.getCurrentProcess(), moduleHandle);
    }
}
