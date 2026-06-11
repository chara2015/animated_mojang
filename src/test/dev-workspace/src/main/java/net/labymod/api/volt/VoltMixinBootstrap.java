package net.labymod.api.volt;

import net.labymod.api.volt.injector.insert.InsertInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/VoltMixinBootstrap.class */
public class VoltMixinBootstrap {
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        InjectionInfo.register(InsertInjectionInfo.class);
    }
}
