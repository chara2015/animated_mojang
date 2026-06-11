package net.labymod.core.client.os.module;

import net.labymod.api.Laby;
import net.labymod.api.client.os.OperatingSystemAccessor;
import net.labymod.core.client.os.AbstractOperatingSystemAccessor;
import net.labymod.core.client.os.module.ModuleScanner;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleScannerUtil.class */
public final class ModuleScannerUtil {
    private ModuleScannerUtil() {
    }

    public static void scanModules(ModuleScanner.State state) {
        OperatingSystemAccessor accessor = Laby.labyAPI().operatingSystemAccessor();
        if (accessor instanceof AbstractOperatingSystemAccessor) {
            AbstractOperatingSystemAccessor osAccessor = (AbstractOperatingSystemAccessor) accessor;
            osAccessor.moduleScanner().scan(state);
        }
    }
}
