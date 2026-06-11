package net.labymod.core.main.debug.filewatcher;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.core.main.debug.filewatcher.WatchablePath;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/filewatcher/WatchablePathManager.class */
@Singleton
@Referenceable
public class WatchablePathManager {
    private final Map<String, WatchablePath> watchablePaths = new HashMap();

    @Inject
    public WatchablePathManager() {
    }

    public WatchablePath create(Path path) {
        String filename = path.getFileName().toString();
        NamedPathType pathType = NamedPathType.UNKNOWN;
        for (NamedPathType value : NamedPathType.VALUES) {
            String[] fileExtensions = value.getFileExtensions();
            int length = fileExtensions.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    String extension = fileExtensions[i];
                    if (!filename.endsWith(extension)) {
                        i++;
                    } else {
                        pathType = value;
                        break;
                    }
                }
            }
        }
        WatchablePath watchablePath = new WatchablePath(path, pathType);
        this.watchablePaths.putIfAbsent(path.toAbsolutePath().normalize().toAbsolutePath().toString(), watchablePath);
        return watchablePath;
    }

    public void onUpdate(Path path, WatchablePath.WatchableEventType eventType) {
        WatchablePath watchablePath = this.watchablePaths.get(path.toAbsolutePath().toString());
        if (watchablePath == null) {
            return;
        }
        watchablePath.onUpdate(eventType);
    }
}
