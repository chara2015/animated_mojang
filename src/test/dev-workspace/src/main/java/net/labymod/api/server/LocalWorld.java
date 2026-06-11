package net.labymod.api.server;

import java.nio.file.Path;
import java.util.Optional;
import net.labymod.api.client.entity.player.GameMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/server/LocalWorld.class */
public class LocalWorld {
    private final String worldName;
    private final String folderName;
    private final GameMode gameMode;
    private final boolean allowCheats;
    private final int port;
    private final Path screenshotFile;

    public LocalWorld(@NotNull String worldName, @NotNull String folderName, @NotNull GameMode gameMode, boolean allowCheats, int port, @Nullable Path screenshotFile) {
        this.worldName = worldName;
        this.folderName = folderName;
        this.gameMode = gameMode;
        this.allowCheats = allowCheats;
        this.port = port;
        this.screenshotFile = screenshotFile;
    }

    @NotNull
    public String worldName() {
        return this.worldName;
    }

    @NotNull
    public String folderName() {
        return this.folderName;
    }

    @NotNull
    public GameMode gameMode() {
        return this.gameMode;
    }

    public boolean allowCheats() {
        return this.allowCheats;
    }

    public int port() {
        return this.port;
    }

    public boolean isOpen() {
        return this.port != -1;
    }

    @NotNull
    public Optional<Path> getScreenshotFile() {
        return Optional.ofNullable(this.screenshotFile);
    }
}
