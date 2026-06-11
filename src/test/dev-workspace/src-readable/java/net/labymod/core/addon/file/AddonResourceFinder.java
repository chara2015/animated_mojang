package net.labymod.core.addon.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.labymod.api.util.io.IOUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/file/AddonResourceFinder.class */
public final class AddonResourceFinder {
    @Nullable
    public static AddonResource find(URL url, String localPath) throws URISyntaxException, IOException {
        Path path = Paths.get(url.toURI());
        if (!IOUtil.exists(path)) {
            return null;
        }
        if (IOUtil.isDirectory(path)) {
            Path resolvedPath = path.resolve(localPath);
            if (!IOUtil.exists(resolvedPath)) {
                return null;
            }
            return new AddonResource(resolvedPath.getFileName().toString(), resolvedPath.toUri(), () -> {
                return IOUtil.newInputStream(resolvedPath);
            });
        }
        ZipFile file = new ZipFile(path.toFile());
        try {
            ZipEntry entry = file.getEntry(localPath);
            if (entry != null) {
                InputStream stream = file.getInputStream(entry);
                try {
                    ByteArrayInputStream copiedStream = new ByteArrayInputStream(IOUtil.readBytes(stream));
                    String name = entry.getName();
                    AddonResource addonResource = new AddonResource(name, createJarFileURI(path, entry.getName()), () -> {
                        return copiedStream;
                    });
                    if (stream != null) {
                        stream.close();
                    }
                    file.close();
                    return addonResource;
                } finally {
                }
            }
            file.close();
            return null;
        } catch (Throwable th) {
            try {
                file.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static <T> List<T> discoverResources(URL url, Predicate<AddonResource> filter, Function<AddonResource, T> mapper) throws URISyntaxException, IOException {
        List<T> result = new ArrayList<>();
        Path path = Paths.get(url.toURI());
        if (!IOUtil.exists(path)) {
            return result;
        }
        if (IOUtil.isDirectory(path)) {
            AddonResourceLocationCollector collector = new AddonResourceLocationCollector();
            try {
                Files.walkFileTree(path, collector);
                List<AddonResource> resourceLocations = collector.getResourceLocations();
                for (AddonResource resourceLocation : resourceLocations) {
                    if (filter.test(resourceLocation)) {
                        result.add(mapper.apply(resourceLocation));
                    }
                }
            } catch (IOException e) {
                return result;
            }
        } else {
            ZipFile file = new ZipFile(path.toFile());
            try {
                Enumeration<? extends ZipEntry> entries = file.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    if (!entry.isDirectory()) {
                        InputStream inputStream = file.getInputStream(entry);
                        try {
                            ByteArrayInputStream buffer = new ByteArrayInputStream(IOUtil.readBytes(inputStream));
                            AddonResource location = new AddonResource(entry.getName(), createJarFileURI(path, entry.getName()), () -> {
                                return buffer;
                            });
                            if (filter.test(location)) {
                                result.add(mapper.apply(location));
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } finally {
                        }
                    }
                }
                file.close();
            } catch (Throwable th) {
                try {
                    file.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        return result;
    }

    private static URI createJarFileURI(Path path, String entry) {
        String filePath = path.toUri().toString();
        return URI.create("jar:" + filePath + "!/" + entry);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/file/AddonResourceFinder$AddonResourceLocationCollector.class */
    private static class AddonResourceLocationCollector extends SimpleFileVisitor<Path> {
        private final List<AddonResource> resourceLocations = new ArrayList();

        private AddonResourceLocationCollector() {
        }

        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            this.resourceLocations.add(new AddonResource(file.getFileName().toString(), file.toUri(), () -> {
                return IOUtil.newInputStream(file);
            }));
            return FileVisitResult.CONTINUE;
        }

        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        public List<AddonResource> getResourceLocations() {
            return this.resourceLocations;
        }
    }
}
