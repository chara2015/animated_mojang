package net.labymod.core.main.lagdetector;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/lagdetector/LagDetector.class */
public final class LagDetector {
    private static final Logging LOGGER = Logging.create((Class<?>) LagDetector.class, () -> {
        return Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment();
    });
    private static final List<LagDetectionModule> MODULES = new ArrayList();
    private static LagDetector instance;
    private static boolean initialized;

    private LagDetector() {
    }

    private static void addModule(LagDetectionModule module) {
        LOGGER.info("Register \"{}\" lag detection module", module.getName());
        MODULES.add(module);
    }

    private static void initialize() {
        if (initialized) {
            return;
        }
        instance = new LagDetector();
        Laby.references().eventBus().registerListener(instance);
        initialized = true;
        addModule(new GarbageCollectorLagDetectionModule());
    }

    public static void onUpdate() {
        initialize();
        for (LagDetectionModule module : MODULES) {
            module.onUpdate();
        }
    }

    @Subscribe
    public void onKey(KeyEvent event) {
        for (LagDetectionModule module : MODULES) {
            module.onKey(event.key(), event.state());
        }
    }
}
