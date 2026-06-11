package net.labymod.core.client.resources.pack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.PackType;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.base.Joiner;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/pack/FolderResourcePack.class */
public class FolderResourcePack implements ResourcePack {
    private static final Logging LOGGER = Logging.getLogger();
    private static final Joiner PATH_JOINER = Joiner.on('/');
    private final Path directory;
    private final String namespace;
    private final Set<String> namespaces;

    public FolderResourcePack(String namespace, Path directory) {
        this.directory = directory;
        this.namespace = namespace;
        this.namespaces = Set.of(namespace);
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack
    public String getName() {
        return this.namespace;
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack
    public InputStream getResource(PackType type, ResourceLocation location) throws IOException {
        Path packDirectory = this.directory.resolve(type.directory()).resolve(location.getNamespace());
        InputStream stream = (InputStream) IOUtil.decomposePath(location.getPath()).mapOrElse(segments -> {
            Path file = IOUtil.resolvePath(packDirectory, segments);
            return openStream(file);
        }, error -> {
            LOGGER.error("Invalid path {}: {}", location, error.message());
            return null;
        });
        if (stream == null) {
            throw new FileNotFoundException(location.toString());
        }
        return stream;
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack
    public boolean hasResource(PackType type, ResourceLocation location) {
        Path packDirectory = this.directory.resolve(type.directory()).resolve(location.getNamespace());
        return ((Boolean) IOUtil.decomposePath(location.getPath()).mapOrElse(segments -> {
            Path file = IOUtil.resolvePath(packDirectory, segments);
            return Boolean.valueOf(Files.exists(file, new LinkOption[0]));
        }, error -> {
            LOGGER.error("Invalid path {}: {}", location, error.message());
            return false;
        })).booleanValue();
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack
    public void listResources(PackType type, String namespace, String path, ResourcePack.ResourceOutput output) {
        IOUtil.decomposePath(path).ifSuccess(segments -> {
            Path baseDirectory = this.directory.resolve(type.directory()).resolve(namespace);
            listPath(namespace, baseDirectory, segments, output);
        }).ifError(error -> {
            LOGGER.error("Invalid path {}: {}", path, error);
        });
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack
    public Set<String> getNamespaces(PackType type) {
        return this.namespaces;
    }

    private void listPath(String namespace, Path baseDirectory, List<String> segments, ResourcePack.ResourceOutput output) {
        Path start = IOUtil.resolvePath(baseDirectory, segments);
        try {
            Stream<Path> stream = Files.find(start, Integer.MAX_VALUE, this::isRegularFile, new FileVisitOption[0]);
            try {
                stream.forEach(file -> {
                    String relativePath = PATH_JOINER.join(baseDirectory.relativize(file));
                    ResourceLocation location = ResourceLocation.create(namespace, relativePath);
                    if (location == null) {
                        IdeUtil.doPause(() -> {
                            LOGGER.error("Invalid path in pack: {}:{}, ignoring", namespace, relativePath);
                        });
                    } else {
                        output.accept(location, () -> {
                            return openStream(file);
                        });
                    }
                });
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (NoSuchFileException | NotDirectoryException e) {
        } catch (IOException exception) {
            LOGGER.error("Failed to list path {}", baseDirectory, exception);
        }
    }

    private boolean isRegularFile(Path path, BasicFileAttributes attributes) {
        return attributes.isRegularFile();
    }

    @Nullable
    private InputStream openStream(Path file) {
        if (Files.exists(file, new LinkOption[0])) {
            try {
                return Files.newInputStream(file, new OpenOption[0]);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
