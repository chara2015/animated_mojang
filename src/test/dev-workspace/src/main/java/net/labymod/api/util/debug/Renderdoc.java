package net.labymod.api.util.debug;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.labymod.api.models.OperatingSystem;
import net.labymod.util.property.SystemProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/debug/Renderdoc.class */
public final class Renderdoc {
    private static boolean initialized;
    private static boolean loaded;

    private Renderdoc() {
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static void load() {
        if (initialized) {
            throw new IllegalStateException("Renderdoc already initialized");
        }
        initialized = true;
        OperatingSystem currentPlatform = OperatingSystem.getPlatform();
        if (!SystemProperties.RENDERDOC.get().booleanValue()) {
            return;
        }
        try {
            Path renderdocLibraryFile = getRenderdocLibraryFile(currentPlatform);
            System.load(renderdocLibraryFile.toAbsolutePath().toString());
            loaded = true;
            System.out.println("Renderdoc loaded");
        } catch (Throwable throwable) {
            System.err.println("Renderdoc could not be loaded: " + throwable.getMessage());
        }
    }

    private static Path getRenderdocLibraryFile(OperatingSystem system) {
        Path target;
        String path = System.getenv("RENDERDOC");
        if (path == null) {
            throw new IllegalStateException("Environment variable \"RENDERDOC\" is not set.");
        }
        Path dir = Path.of(path, new String[0]);
        if (Files.isDirectory(dir, new LinkOption[0])) {
            target = dir.resolve("renderdoc." + system.getLibraryExtensionName());
        } else {
            target = dir;
        }
        if (!Files.exists(target, new LinkOption[0])) {
            throw new IllegalStateException(String.valueOf(target) + " does not exist.");
        }
        if (!Files.isRegularFile(target, new LinkOption[0])) {
            throw new IllegalStateException(String.valueOf(target) + " is not a regular file.");
        }
        return target;
    }
}
