package net.labymod.core.main.debug.filewatcher;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import net.labymod.api.util.io.IOUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/filewatcher/WatchablePath.class */
public class WatchablePath {
    private final Path path;
    private final PathType pathType;
    private boolean nioCalled;
    private boolean exists;
    private boolean modified;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/filewatcher/WatchablePath$WatchableEventType.class */
    public enum WatchableEventType {
        UNKNOWN,
        MODIFY,
        CREATE,
        DELETE,
        OVERFLOW
    }

    public WatchablePath(Path path) {
        this(path, NamedPathType.UNKNOWN);
    }

    public WatchablePath(Path path, PathType pathType) {
        this.path = path;
        this.pathType = pathType;
    }

    public Path getPath() {
        return this.path;
    }

    public InputStream openStream() throws IOException {
        return IOUtil.newInputStream(this.path);
    }

    public boolean exists() {
        if (this.nioCalled && this.exists) {
            return true;
        }
        this.nioCalled = true;
        this.exists = IOUtil.exists(this.path);
        return this.exists;
    }

    public void onUpdate(WatchableEventType eventType) {
        switch (eventType.ordinal()) {
            case 1:
                this.modified = true;
                break;
            case 2:
                this.exists = true;
                this.modified = true;
                break;
            case 3:
                this.exists = false;
                break;
        }
    }

    public boolean isModified() {
        boolean modified = this.modified;
        this.modified = false;
        return modified;
    }
}
