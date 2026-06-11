package net.labymod.api;

import java.util.function.Consumer;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.event.Event;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.reference.ReferenceStorageFinder;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Laby.class */
public final class Laby {
    private static boolean initialized;
    private static ReferenceStorage referenceStorage;

    @ApiStatus.Internal
    public static void setInitialized() {
        initialized = true;
    }

    public static LabyAPI labyAPI() {
        return references().labyAPI();
    }

    public static <T extends Event> void fireEventNextTick(T event) {
        references().eventBus().fireNextTick(event, null);
    }

    public static <T extends Event> void fireEventNextTick(T event, Consumer<T> consumer) {
        references().eventBus().fireNextTick(event, consumer);
    }

    public static <T extends Event> T fireEvent(T event) {
        references().eventBus().fire(event);
        return event;
    }

    @Deprecated(since = "4.4.0", forRemoval = true)
    public static GFXBridge gfx() {
        return references().gfxBridge();
    }

    public static ReferenceStorage references() {
        if (referenceStorage == null) {
            loadReferenceStorage();
        }
        if (!initialized) {
            initialized = true;
            referenceStorage.labyAPI();
        }
        return referenceStorage;
    }

    private static void loadReferenceStorage() {
        referenceStorage = (ReferenceStorage) ReferenceStorageFinder.find(PlatformEnvironment.getPlatformClassloader().getPlatformClassloader());
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
