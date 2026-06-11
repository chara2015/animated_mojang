package net.labymod.api.platform.launcher;

import java.util.Objects;
import java.util.UUID;
import net.labymod.api.models.version.Version;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/platform/launcher/LauncherService.class */
@Referenceable
public interface LauncherService {
    @Nullable
    String getLauncher();

    @Nullable
    String getLauncherVersion();

    @Nullable
    UUID getGameSessionId();

    @Nullable
    String getModPackId();

    @Nullable
    String getModPackName();

    @Nullable
    Version getModPackVersion();

    boolean isUsingLabyModLauncher();

    boolean isUsingModLoader();

    String getModLoader();

    void restart();

    boolean isConnectedToLauncher();

    @NotNull
    default String getLauncherOrDefault(@NotNull String defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue");
        String launcher = getLauncher();
        return launcher == null ? defaultValue : launcher;
    }

    default boolean hasGameSessionId() {
        return getGameSessionId() != null;
    }

    default boolean isUsingModPack() {
        return getModPackId() != null;
    }
}
