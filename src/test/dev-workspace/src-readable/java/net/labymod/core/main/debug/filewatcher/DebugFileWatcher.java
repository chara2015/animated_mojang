package net.labymod.core.main.debug.filewatcher;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.debug.filewatcher.WatchablePath;
import net.labymod.util.property.SystemProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/filewatcher/DebugFileWatcher.class */
public class DebugFileWatcher {
    private final WatchablePathManager watchablePathManager = LabyMod.references().watchablePathManager();
    private WatchService watchService;

    public void collectDirectories() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
            List<Path> directories = SystemProperties.HOT_FILE_RELOADING_DIRECTORIES.get();
            for (Path directory : directories) {
                if (IOUtil.exists(directory) && IOUtil.isDirectory(directory)) {
                    registerWatcher(directory, this.watchService);
                    try {
                        Files.walkFileTree(directory, new FileWatcherVisitor(this.watchService));
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        } catch (IOException exception2) {
            exception2.printStackTrace();
        }
    }

    public void startFileWatcherThread() {
        WatchService watchService = this.watchService;
        if (watchService == null) {
            return;
        }
        Thread fileWatcherServiceThread = new DebugFileWatcherThread(this.watchablePathManager, watchService);
        fileWatcherServiceThread.start();
    }

    public void close() throws IOException {
        WatchService watchService = this.watchService;
        if (watchService == null) {
            return;
        }
        watchService.close();
    }

    static void registerWatcher(Path directory, WatchService watchService) {
        try {
            directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/filewatcher/DebugFileWatcher$FileWatcherVisitor.class */
    private static class FileWatcherVisitor extends SimpleFileVisitor<Path> {
        private final WatchService watchService;

        public FileWatcherVisitor(WatchService watchService) {
            this.watchService = watchService;
        }

        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            DebugFileWatcher.registerWatcher(dir, this.watchService);
            return FileVisitResult.CONTINUE;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/filewatcher/DebugFileWatcher$DebugFileWatcherThread.class */
    private static class DebugFileWatcherThread extends Thread {
        private final WatchablePathManager watchablePathManager;
        private final WatchService watchService;
        private boolean running;

        public DebugFileWatcherThread(WatchablePathManager watchablePathManager, WatchService watchService) {
            this.watchablePathManager = watchablePathManager;
            this.watchService = watchService;
            setName("FileWatcherService");
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            this.running = true;
            super.start();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            while (this.running) {
                executeWatchService();
            }
        }

        @Override // java.lang.Thread
        public void interrupt() {
            super.interrupt();
            this.running = false;
        }

        private void executeWatchService() {
            WatchablePath.WatchableEventType eventType;
            try {
                WatchKey key = this.watchService.take();
                for (WatchEvent<?> pollEvent : key.pollEvents()) {
                    if (pollEvent.context() instanceof Path) {
                        Path watchableDirectory = (Path) key.watchable();
                        Path path = watchableDirectory.resolve((Path) pollEvent.context());
                        LabyAPI labyAPI = Laby.labyAPI();
                        if (labyAPI != null && labyAPI.minecraft() != null && (eventType = getEventType(pollEvent.kind())) != WatchablePath.WatchableEventType.UNKNOWN) {
                            labyAPI.minecraft().executeOnRenderThread(() -> {
                                this.watchablePathManager.onUpdate(path, eventType);
                            });
                        }
                    }
                }
                key.reset();
            } catch (InterruptedException e) {
            } catch (ClosedWatchServiceException e2) {
                this.running = false;
            }
        }

        private WatchablePath.WatchableEventType getEventType(WatchEvent.Kind<?> watchEventKind) {
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.ENTRY_MODIFY)) {
                return WatchablePath.WatchableEventType.MODIFY;
            }
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.ENTRY_CREATE)) {
                return WatchablePath.WatchableEventType.CREATE;
            }
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.ENTRY_DELETE)) {
                return WatchablePath.WatchableEventType.DELETE;
            }
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.OVERFLOW)) {
                return WatchablePath.WatchableEventType.OVERFLOW;
            }
            return WatchablePath.WatchableEventType.UNKNOWN;
        }
    }
}
