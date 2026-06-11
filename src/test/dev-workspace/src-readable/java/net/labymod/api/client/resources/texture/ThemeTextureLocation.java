package net.labymod.api.client.resources.texture;

import java.util.Locale;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.resources.ThemeResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/ThemeTextureLocation.class */
public interface ThemeTextureLocation extends ThemeResourceLocation {

    @Deprecated
    public static final ResourceLocationFactory FACTORY = ThemeResourceLocation.FACTORY;

    int getWidth();

    int getHeight();

    static ThemeTextureLocation of(String namespace, String path, int width, int height) {
        return ThemeResourceLocation.FACTORY.createThemeTexture(namespace, String.format(Locale.ROOT, "textures/%s.png", path), width, height);
    }

    static ThemeTextureLocation of(String path, int width, int height) {
        if (path.contains(":")) {
            String[] split = path.split(":");
            return of(split[0], split[1], width, height);
        }
        return of(null, path, width, height);
    }

    static ThemeTextureLocation of(String path, int size) {
        return of(path, size, size);
    }

    static ThemeTextureLocation of(String path) {
        return of(path, 128);
    }
}
