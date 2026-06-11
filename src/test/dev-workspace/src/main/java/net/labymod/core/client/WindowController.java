package net.labymod.core.client;

import java.util.function.Supplier;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.util.Pair;
import net.labymod.core.configuration.ConfigurationEventListenerRegistry;
import net.labymod.core.configuration.labymod.LabyConfigProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/WindowController.class */
public class WindowController {
    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    private static boolean maximize;

    private WindowController() {
    }

    public static Pair<Integer, Integer> calculateNewScreenSize(int initialWidth, int initialHeight, Supplier<Pair<Integer, Integer>> monitorSizeSupplier) {
        maximize = false;
        if (initialWidth != 854 || initialHeight != 480) {
            return null;
        }
        try {
            LabyConfigProvider labyConfigProvider = LabyConfigProvider.INSTANCE;
            LabyConfig labyConfig = labyConfigProvider.get();
            if (labyConfig == null) {
                ConfigurationEventListenerRegistry.register();
                labyConfig = labyConfigProvider.loadJson();
            }
            OtherConfig otherConfig = labyConfig.other();
            boolean restoreResolution = otherConfig.window().restoreWindowResolution().get().booleanValue();
            if (!restoreResolution) {
                return null;
            }
            int width = otherConfig.lastWidth().get().intValue();
            int height = otherConfig.lastHeight().get().intValue();
            Pair<Integer, Integer> monitorSizePair = monitorSizeSupplier.get();
            if (monitorSizePair != null && (width >= monitorSizePair.getFirst().intValue() || height >= monitorSizePair.getSecond().intValue())) {
                width = 1280;
                height = 720;
                maximize = true;
            }
            return Pair.of(Integer.valueOf(width), Integer.valueOf(height));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void maximize(Runnable maximizeRunnable) {
        if (maximize) {
            maximizeRunnable.run();
        }
    }
}
