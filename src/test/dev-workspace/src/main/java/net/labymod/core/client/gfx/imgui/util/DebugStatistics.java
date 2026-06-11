package net.labymod.core.client.gfx.imgui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/util/DebugStatistics.class */
public final class DebugStatistics {
    private static final Map<UUID, Runnable> BUFFER_BUILDERS = new HashMap();

    public static void registerBufferBuilder(UUID uuid, Runnable renderTask) {
        BUFFER_BUILDERS.put(uuid, renderTask);
    }

    public static void unregisterBufferBuilder(UUID uuid) {
        BUFFER_BUILDERS.remove(uuid);
    }

    public static Map<UUID, Runnable> getBufferBuilders() {
        return BUFFER_BUILDERS;
    }
}
