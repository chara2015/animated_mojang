package net.labymod.core.loader.isolated;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.Constants;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.loader.isolated.util.IsolatedClassLoader;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/ManifestIsolatedLibraryLoader.class */
public class ManifestIsolatedLibraryLoader extends IsolatedLibraryLoader {
    @Override // net.labymod.core.loader.isolated.IsolatedLibraryLoader
    public void onLoad(ClassLoader loader) {
        URL resource = loader.getResource("isolated_libraries.json");
        if (resource == null) {
            return;
        }
        try {
            InputStream stream = resource.openStream();
            try {
                InputStreamReader reader = new InputStreamReader(stream);
                try {
                    IsolatedLibraryManifest manifest = (IsolatedLibraryManifest) GsonUtil.DEFAULT_GSON.fromJson(reader, IsolatedLibraryManifest.class);
                    reader.close();
                    if (stream != null) {
                        stream.close();
                    }
                    for (IsolatedLibraryPredicate predicate : this.predicates) {
                        for (IsolatedLibrary library : manifest.getLibraries()) {
                            if (isSameVersion(library)) {
                                boolean filtered = predicate.test(library);
                                if (filtered) {
                                    continue;
                                } else {
                                    IsolatedClassLoader classLoader = getClassLoader(library.getGroup());
                                    Path libraryPath = Constants.Files.LIBRARIES.resolve(library.getPath());
                                    if (IOUtil.exists(libraryPath)) {
                                        boolean corrupted = IOUtil.isCorrupted(libraryPath);
                                        if (!corrupted) {
                                            addUrl(classLoader, library);
                                        } else {
                                            LOGGER.error("{} is a corrupted library", libraryPath.toAbsolutePath());
                                            try {
                                                IOUtil.deleteIfExits(libraryPath);
                                            } catch (IOException exception) {
                                                LOGGER.error("Could not delete library {}", libraryPath, exception);
                                            }
                                            try {
                                                IOUtil.createDirectories(libraryPath.getParent());
                                                DownloadTask downloadTask = new DownloadTask(library.getUrl(), libraryPath, () -> {
                                                    addUrl(classLoader, library);
                                                });
                                                downloadTask.download();
                                            } catch (IOException exception2) {
                                                throw new RuntimeException("Failed to create directories", exception2);
                                            }
                                        }
                                    } else {
                                        IOUtil.createDirectories(libraryPath.getParent());
                                        DownloadTask downloadTask2 = new DownloadTask(library.getUrl(), libraryPath, () -> {
                                            addUrl(classLoader, library);
                                        });
                                        downloadTask2.download();
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    try {
                        reader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (IOException exception3) {
            throw new RuntimeException(exception3);
        }
    }

    private boolean isSameVersion(IsolatedLibrary library) {
        List<String> versions = library.getVersions();
        if (versions.isEmpty()) {
            return true;
        }
        for (String version : versions) {
            if (version.equals(MinecraftVersions.current().getRawVersion())) {
                return true;
            }
        }
        return false;
    }

    private void addUrl(IsolatedClassLoader classLoader, IsolatedLibrary library) {
        try {
            Path path = Constants.Files.LIBRARIES.resolve(library.getPath());
            classLoader.addPath(path);
            LOGGER.info("Library {}", path.toAbsolutePath().toString());
            this.loadedLibraries.add(library);
        } catch (IOException exception) {
            LOGGER.error("Could not load library {}", library.getPath(), exception);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/ManifestIsolatedLibraryLoader$DownloadTask.class */
    public static final class DownloadTask {
        private static final Logging LOGGER = Logging.getLogger();
        private static final int MAX_TRIES = 3;
        private final String libraryUrl;
        private final Path destination;
        private final Runnable downloadHandler;
        private int tries = 1;

        public DownloadTask(String libraryUrl, Path destination, Runnable downloadHandler) {
            this.libraryUrl = libraryUrl;
            this.destination = destination;
            this.downloadHandler = downloadHandler;
        }

        /* JADX WARN: Type inference failed for: r0v6, types: [net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request] */
        public void download() {
            if (this.tries > 3) {
                throw new RuntimeException("Failed to download library " + this.libraryUrl);
            }
            AtomicInteger currentProgress = new AtomicInteger(-1);
            Response<Path> response = DefaultWebResolver.resolveSync(Request.ofFile(this.destination, percentage -> {
                int progressPercentage = MathHelper.floor(percentage.doubleValue());
                if (currentProgress.get() >= progressPercentage) {
                    return;
                }
                currentProgress.set(progressPercentage);
                LOGGER.info("Downloading library {} (Progress {}/100)", this.libraryUrl, currentProgress);
            }).url(this.libraryUrl, new Object[0]));
            if (response.hasException()) {
                LOGGER.error("Failed to download library {}. Retrying ({}/{})", this.libraryUrl, Integer.valueOf(this.tries), 3, response.exception());
                this.tries++;
                download();
                return;
            }
            this.downloadHandler.run();
        }
    }
}
