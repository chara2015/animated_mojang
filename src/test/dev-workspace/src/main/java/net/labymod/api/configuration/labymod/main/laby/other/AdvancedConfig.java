package net.labymod.api.configuration.labymod.main.laby.other;

import java.util.Map;
import net.labymod.api.configuration.loader.ConfigAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/other/AdvancedConfig.class */
public interface AdvancedConfig extends ConfigAccessor {
    boolean debugger();

    boolean refreshHotkey();

    Map<String, Boolean> debugWindows();
}
