package net.labymod.core.client.resources;

import java.io.InputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.IllegalResourceLocationException;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.resources.ThemeResourceLocation;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.client.resources.DefaultThemeResourceLocation;
import net.labymod.core.client.resources.texture.DefaultThemeTextureLocation;
import net.labymod.util.property.SystemProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/AbstractResourceLocationFactory.class */
public abstract class AbstractResourceLocationFactory implements ResourceLocationFactory {
    private static final String MINECRAFT_NAMESPACE = "minecraft";
    private static final Collection<Path> DIRECTORIES = SystemProperties.HOT_FILE_RELOADING_DIRECTORIES.get();
    private final Map<String, ResourceLocation> cachedLocations = new ConcurrentHashMap();
    private final Set<String> lockComputes = Collections.newSetFromMap(new ConcurrentHashMap());

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public ResourceLocation createPath(@NotNull Path path, @NotNull String locationNamespace, @NotNull String locationPath) {
        return new PathResourceLocation(path, locationNamespace, locationPath);
    }

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    @NotNull
    public AnimatedResourceLocation.Builder builder() {
        return Laby.references().animatedResourceLocationBuilder();
    }

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public AnimatedResourceLocation createAnimated(String namespace, String path, InputStream spriteImageStream, int ratioWidth, int ratioHeight, long delay, @Nullable Runnable completableListener) {
        return new DefaultAnimatedResourceLocation(namespace, path, spriteImageStream, ratioWidth, ratioHeight, delay, completableListener);
    }

    public ResourceLocation apply(@NotNull String namespace, @NotNull String path, @NotNull BiFunction<String, String, ResourceLocation> locationFactory) {
        String locationAsString = namespace + ":" + path;
        if ("minecraft".equals(namespace) || path.startsWith("sounds")) {
            return locationFactory.apply(namespace, path);
        }
        if (this.lockComputes.contains(locationAsString)) {
            return locationFactory.apply(namespace, path);
        }
        try {
            this.lockComputes.add(locationAsString);
            ResourceLocation resourceLocationComputeIfAbsent = this.cachedLocations.computeIfAbsent(locationAsString, l -> {
                ResourceLocation location = (ResourceLocation) locationFactory.apply(namespace, path);
                if (!IdeUtil.RUNNING_IN_IDE) {
                    return location;
                }
                return resolveResourceLocation(location, namespace, path);
            });
            this.lockComputes.remove(locationAsString);
            return resourceLocationComputeIfAbsent;
        } catch (Throwable th) {
            this.lockComputes.remove(locationAsString);
            throw th;
        }
    }

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public ThemeResourceLocation createThemeResource(String path) {
        return new DefaultThemeResourceLocation.Builder().path(path).build();
    }

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public ThemeTextureLocation createThemeTexture(String path, int width, int height) {
        return new DefaultThemeTextureLocation.Builder().path(path).width(width).height(height).build();
    }

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public ThemeResourceLocation createThemeResource(String namespace, String path) {
        return new DefaultThemeResourceLocation.Builder().namespace(namespace).path(path).build();
    }

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public ThemeTextureLocation createThemeTexture(String namespace, String path, int width, int height) {
        return new DefaultThemeTextureLocation.Builder().namespace(namespace).path(path).width(width).height(height).build();
    }

    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public Map<String, ResourceLocation> getCachedResourceLocations() {
        return this.cachedLocations;
    }

    private ResourceLocation resolveResourceLocation(ResourceLocation delegate, String namespace, String path) {
        for (Path directory : DIRECTORIES) {
            try {
                Path resourcePath = directory.resolve("assets").resolve(namespace).resolve(path);
                if (IOUtil.exists(resourcePath)) {
                    PathResourceLocation newLocation = new PathResourceLocation(resourcePath, delegate.getNamespace(), delegate.getPath());
                    newLocation.metadata().set(delegate.metadata());
                    return newLocation;
                }
            } catch (InvalidPathException exception) {
                throw new IllegalResourceLocationException(exception);
            }
        }
        return delegate;
    }
}
