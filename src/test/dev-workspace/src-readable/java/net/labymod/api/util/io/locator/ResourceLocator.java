package net.labymod.api.util.io.locator;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/locator/ResourceLocator.class */
public abstract class ResourceLocator<T> {
    private static final Logging LOGGER = Logging.getLogger();
    private final ClassLoader loader;
    private final String resourcePath;
    private final BiFunction<Path, T, T> resourceProcessor;

    protected abstract T map(Path path);

    public ResourceLocator(ClassLoader loader, String resourcePath) {
        this(loader, resourcePath, null);
    }

    public ResourceLocator(ClassLoader loader, String resourcePath, BiFunction<Path, T, T> resourceProcessor) {
        this.loader = loader;
        this.resourcePath = resourcePath;
        this.resourceProcessor = resourceProcessor;
    }

    public List<T> locate() {
        try {
            Enumeration<URL> resources = PlatformEnvironment.getResources(this.loader, this.resourcePath);
            if (!resources.hasMoreElements()) {
                LOGGER.error("File {} was not found.", this.resourcePath);
                return Collections.emptyList();
            }
            List<T> locatedResources = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                locatedResources.addAll(locateResources(url));
            }
            return locatedResources;
        } catch (IOException exception) {
            LOGGER.error("Unable to locate the resource {}.", this.resourcePath, exception);
            return Collections.emptyList();
        }
    }

    private List<T> locateResources(URL url) {
        try {
            URI uri = url.toURI();
            String scheme = uri.getScheme();
            if (!"jar".equals(scheme) && !"file".equals(scheme)) {
                LOGGER.warn("Unsupported scheme {}.", scheme);
            }
            Path path = safeGetPath(uri);
            Path parent = path.getParent();
            List<T> locatedResources = new ArrayList<>();
            walkDirectory(locatedResources, parent);
            processResources(locatedResources, parent);
            return locatedResources;
        } catch (Exception exception) {
            LOGGER.error("Unable to locate the resource {}.", this.resourcePath, exception);
            return Collections.emptyList();
        }
    }

    private void processResources(List<T> locatedResources, Path directory) {
        if (this.resourceProcessor == null) {
            return;
        }
        locatedResources.replaceAll(resource -> {
            return this.resourceProcessor.apply(directory, resource);
        });
    }

    private void walkDirectory(List<T> locatedResources, Path directory) {
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
            try {
                stream.forEach(path -> {
                    if (Files.isDirectory(path, new LinkOption[0])) {
                        walkDirectory(locatedResources, path);
                    } else {
                        locatedResources.add(map(path));
                    }
                });
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            LOGGER.error("Unable to walk directory {}.", directory, exception);
            LabySentry.capture(exception);
        }
    }

    private Path safeGetPath(URI uri) throws IOException {
        try {
            return Paths.get(uri);
        } catch (FileSystemNotFoundException e) {
            try {
                FileSystems.newFileSystem(uri, (Map<String, ?>) Collections.emptyMap());
            } catch (FileSystemAlreadyExistsException e2) {
            }
            return Paths.get(uri);
        } catch (Throwable throwable) {
            LOGGER.warn("Unable to get path from URI {}.", uri, throwable);
            FileSystems.newFileSystem(uri, (Map<String, ?>) Collections.emptyMap());
            return Paths.get(uri);
        }
    }
}
