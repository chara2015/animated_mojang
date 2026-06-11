package net.labymod.api.client.resources;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/ResourceLocationFactory.class */
@Referenceable
public interface ResourceLocationFactory {
    ResourceLocation create(@NotNull String str, @NotNull String str2);

    ResourceLocation createPath(@NotNull Path path, @NotNull String str, @NotNull String str2);

    @NotNull
    AnimatedResourceLocation.Builder builder();

    AnimatedResourceLocation createAnimated(String str, String str2, InputStream inputStream, int i, int i2, long j, @Nullable Runnable runnable);

    ThemeResourceLocation createThemeResource(String str);

    ThemeTextureLocation createThemeTexture(String str, int i, int i2);

    ThemeResourceLocation createThemeResource(String str, String str2);

    ThemeTextureLocation createThemeTexture(String str, String str2, int i, int i2);

    Map<String, ResourceLocation> getCachedResourceLocations();

    default ResourceLocation createMinecraft(@NotNull String path) {
        return create(Namespaces.MINECRAFT, path);
    }

    default ResourceLocation createLabyMod(@NotNull String path) {
        return create("labymod", path);
    }

    default ResourceLocation parse(@NotNull String value) {
        String[] seg = value.split(":", 2);
        if (seg.length == 1) {
            return createMinecraft(seg[0]);
        }
        return create(seg[0], seg[1]);
    }

    default ResourceLocation create(@NotNull Theme theme, @NotNull String namespace, @NotNull String path) {
        return create(namespace, theme.getDirectoryPath() + path);
    }

    default AnimatedResourceLocation createAnimated(String namespace, String path, InputStream spriteImageStream, int ratioWidth, int ratioHeight, long delay) {
        return createAnimated(namespace, path, spriteImageStream, ratioWidth, ratioHeight, delay, null);
    }

    @Deprecated
    default ThemeTextureLocation createThemeResource(String path, int width, int height) {
        return createThemeTexture(path, width, height);
    }

    @Deprecated
    default ThemeTextureLocation createThemeResource(String namespace, String path, int width, int height) {
        return createThemeTexture(namespace, path, width, height);
    }
}
