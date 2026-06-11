package net.labymod.core.client.gui.screen.theme;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.gui.screen.theme.BasicThemeFile;
import net.labymod.api.client.gui.screen.theme.ThemeFileFinder;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.util.classpath.ClasspathUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/DefaultThemeFileFinder.class */
@Singleton
@Implements(ThemeFileFinder.class)
public class DefaultThemeFileFinder implements ThemeFileFinder {
    @Inject
    public DefaultThemeFileFinder() {
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeFileFinder
    public boolean exists(BasicThemeFile file) {
        ResourceLocation location = file.toResourceLocation();
        String themeLocation = "assets/" + location.getNamespace() + "/" + location.getPath();
        String namespace = file.getNamespace();
        Optional<LoadedAddon> addon = Laby.labyAPI().addonService().getAddon(namespace);
        if (addon.isPresent()) {
            AddonClassLoader classLoader = (AddonClassLoader) addon.get().getClassLoader();
            URL resourceObject = classLoader.findResourceObject(themeLocation);
            return resourceObject != null;
        }
        InputStream stream = null;
        try {
            stream = ClasspathUtil.getResourceAsInputStream(namespace, themeLocation, false);
            IOUtil.closeSilent(stream);
            return true;
        } catch (IOException e) {
            IOUtil.closeSilent(stream);
            return false;
        } catch (Throwable th) {
            IOUtil.closeSilent(stream);
            throw th;
        }
    }
}
