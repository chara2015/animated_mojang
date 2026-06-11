package net.minecraft.server.packs;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.FileUtil;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/PathPackResources.class */
public class PathPackResources extends AbstractPackResources {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Joiner PATH_JOINER = Joiner.on("/");
    private final Path root;

    public PathPackResources(PackLocationInfo $$0, Path $$1) {
        super($$0);
        this.root = $$1;
    }

    @Override // net.minecraft.server.packs.PackResources
    public IoSupplier<InputStream> getRootResource(String... $$0) {
        FileUtil.validatePath($$0);
        Path $$1 = FileUtil.resolvePath(this.root, List.of((Object[]) $$0));
        if (Files.exists($$1, new LinkOption[0])) {
            return IoSupplier.create($$1);
        }
        return null;
    }

    public static boolean validatePath(Path $$0) {
        if (!SharedConstants.DEBUG_VALIDATE_RESOURCE_PATH_CASE || $$0.getFileSystem() != FileSystems.getDefault()) {
            return true;
        }
        try {
            return $$0.toRealPath(new LinkOption[0]).endsWith($$0);
        } catch (IOException $$1) {
            LOGGER.warn("Failed to resolve real path for {}", $$0, $$1);
            return false;
        }
    }

    @Override // net.minecraft.server.packs.PackResources
    public IoSupplier<InputStream> getResource(PackType $$0, Identifier $$1) {
        Path $$2 = this.root.resolve($$0.getDirectory()).resolve($$1.getNamespace());
        return getResource($$1, $$2);
    }

    public static IoSupplier<InputStream> getResource(Identifier $$0, Path $$1) {
        return (IoSupplier) FileUtil.decomposePath($$0.getPath()).mapOrElse($$12 -> {
            Path $$2 = FileUtil.resolvePath($$1, $$12);
            return returnFileIfExists($$2);
        }, $$13 -> {
            LOGGER.error("Invalid path {}: {}", $$0, $$13.message());
            return null;
        });
    }

    private static IoSupplier<InputStream> returnFileIfExists(Path $$0) {
        if (Files.exists($$0, new LinkOption[0]) && validatePath($$0)) {
            return IoSupplier.create($$0);
        }
        return null;
    }

    @Override // net.minecraft.server.packs.PackResources
    public void listResources(PackType $$0, String $$1, String $$2, PackResources.ResourceOutput $$3) {
        FileUtil.decomposePath($$2).ifSuccess($$32 -> {
            Path $$4 = this.root.resolve($$0.getDirectory()).resolve($$1);
            listPath($$1, $$4, $$32, $$3);
        }).ifError($$12 -> {
            LOGGER.error("Invalid path {}: {}", $$2, $$12.message());
        });
    }

    public static void listPath(String $$0, Path $$1, List<String> $$2, PackResources.ResourceOutput $$3) {
        Path $$4 = FileUtil.resolvePath($$1, $$2);
        try {
            Stream<Path> $$5 = Files.find($$4, Integer.MAX_VALUE, PathPackResources::isRegularFile, new FileVisitOption[0]);
            try {
                $$5.forEach($$32 -> {
                    String $$42 = PATH_JOINER.join($$1.relativize($$32));
                    Identifier $$52 = Identifier.tryBuild($$0, $$42);
                    if ($$52 != null) {
                        $$3.accept($$52, IoSupplier.create($$32));
                    } else {
                        Util.logAndPauseIfInIde(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", $$0, $$42));
                    }
                });
                if ($$5 != null) {
                    $$5.close();
                }
            } finally {
            }
        } catch (NoSuchFileException | NotDirectoryException e) {
        } catch (IOException $$6) {
            LOGGER.error("Failed to list path {}", $$4, $$6);
        }
    }

    private static boolean isRegularFile(Path $$0, BasicFileAttributes $$1) {
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            return $$1.isRegularFile() && !StringUtils.equalsIgnoreCase($$0.getFileName().toString(), ".ds_store");
        }
        return $$1.isRegularFile();
    }

    @Override // net.minecraft.server.packs.PackResources
    public Set<String> getNamespaces(PackType $$0) {
        Set<String> $$1 = Sets.newHashSet();
        Path $$2 = this.root.resolve($$0.getDirectory());
        try {
            DirectoryStream<Path> $$3 = Files.newDirectoryStream($$2);
            try {
                for (Path $$4 : $$3) {
                    String $$5 = $$4.getFileName().toString();
                    if (Identifier.isValidNamespace($$5)) {
                        $$1.add($$5);
                    } else {
                        LOGGER.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", $$5, this.root);
                    }
                }
                if ($$3 != null) {
                    $$3.close();
                }
            } catch (Throwable th) {
                if ($$3 != null) {
                    try {
                        $$3.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (NoSuchFileException | NotDirectoryException e) {
        } catch (IOException $$6) {
            LOGGER.error("Failed to list path {}", $$2, $$6);
        }
        return $$1;
    }

    @Override // net.minecraft.server.packs.PackResources, java.lang.AutoCloseable
    public void close() {
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/PathPackResources$PathResourcesSupplier.class */
    public static class PathResourcesSupplier implements Pack.ResourcesSupplier {
        private final Path content;

        public PathResourcesSupplier(Path $$0) {
            this.content = $$0;
        }

        @Override // net.minecraft.server.packs.repository.Pack.ResourcesSupplier
        public PackResources openPrimary(PackLocationInfo $$0) {
            return new PathPackResources($$0, this.content);
        }

        @Override // net.minecraft.server.packs.repository.Pack.ResourcesSupplier
        public PackResources openFull(PackLocationInfo $$0, Pack.Metadata $$1) {
            PackResources $$2 = openPrimary($$0);
            List<String> $$3 = $$1.overlays();
            if ($$3.isEmpty()) {
                return $$2;
            }
            List<PackResources> $$4 = new ArrayList<>($$3.size());
            for (String $$5 : $$3) {
                Path $$6 = this.content.resolve($$5);
                $$4.add(new PathPackResources($$0, $$6));
            }
            return new CompositePackResources($$2, $$4);
        }
    }
}
