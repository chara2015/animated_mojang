package net.labymod.api.debug;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.user.GameUser;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/debug/DebugRegistry.class */
@ApiStatus.Internal
public final class DebugRegistry {
    private static final Map<String, DebugFeature> STATES = new HashMap();
    public static final DebugFeature DEBUG_WINDOWS = register("Debug Windows", Key.D, () -> {
        return Laby.labyAPI().labyModLoader().isDevelopmentEnvironment() || Laby.labyAPI().config().other().advanced().debugger();
    });
    public static final DebugFeature SURFACE_WIREFRAME = register("Surface Wireframe", Key.G, DebugRegistry::isStaffOrCosmeticCreator);
    public static final DebugFeature PETS_AI = register("Pets AI", Key.U, DebugRegistry::isStaffOrCosmeticCreator);
    public static final DebugFeature PAYLOAD = register("Custom Payload", Key.O, DebugRegistry::isDevelopmentEnvironment);
    public static final DebugFeature WORLD_OBJECT_CULL_VOLUMES = register("World Object Cull Volumes", Key.V, DebugRegistry::isDevelopmentEnvironment);
    public static final DebugFeature FRUSTUM_DEBUG = register("Frustum Debug", Key.F, DebugRegistry::isDevelopmentEnvironment);

    private DebugRegistry() {
    }

    public static void toggleStates(@NotNull Key key) {
        for (DebugFeature state : STATES.values()) {
            state.toggleState(key);
        }
    }

    @NotNull
    public static Collection<DebugFeature> getDebugFeatures() {
        return Collections.unmodifiableCollection(STATES.values());
    }

    @NotNull
    public static DebugFeature register(@NotNull String name, @NotNull Key key) {
        DebugFeature newDebugFeature = new DebugFeature(name, key);
        STATES.put(name, newDebugFeature);
        return newDebugFeature;
    }

    @NotNull
    public static DebugFeature register(@NotNull String name, @NotNull Key key, @NotNull BooleanSupplier permissionCheck) {
        DebugFeature newDebugFeature = new DebugFeature(name, key, permissionCheck);
        STATES.put(name, newDebugFeature);
        return newDebugFeature;
    }

    private static boolean isStaffOrCosmeticCreator() {
        GameUser gameUser = Laby.references().gameUserService().clientGameUser();
        if (gameUser == null) {
            return false;
        }
        return gameUser.visibleGroup().isStaffOrCosmeticCreator();
    }

    private static boolean isDevelopmentEnvironment() {
        return Laby.labyAPI().labyModLoader().isDevelopmentEnvironment();
    }
}
