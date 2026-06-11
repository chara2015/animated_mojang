package net.labymod.core.addon.loader.initial;

import com.google.gson.JsonObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.file.AddonResource;
import net.labymod.core.addon.file.AddonResourceFinder;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/initial/ClasspathAddonLoader.class */
public class ClasspathAddonLoader extends AddonLoaderSubService {
    private AddonClassLoader classLoader;

    public ClasspathAddonLoader(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.INITIAL);
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        InstalledAddonInfo installedAddonInfoMerge;
        if (!LabyMod.getInstance().labyModLoader().isAddonDevelopmentEnvironment()) {
            return;
        }
        try {
            List<URL> urls = PlatformEnvironment.getPlatformClassloader().getPotentialClasspathAddons();
            InstalledAddonInfo installedAddonInfo = null;
            for (URL url : urls) {
                AddonResource resource = AddonResourceFinder.find(url, "addon.json");
                if (resource != null) {
                    InputStream stream = resource.openStream();
                    try {
                        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                        try {
                            JsonObject object = (JsonObject) AddonLoader.GSON.fromJson(reader, JsonObject.class);
                            if (object == null) {
                                throw new AddonLoadException("Invalid addon.json: not a json object");
                            }
                            InstalledAddonInfo addonInfo = (InstalledAddonInfo) AddonLoader.GSON.fromJson(object, InstalledAddonInfo.class);
                            if (installedAddonInfo == null) {
                                installedAddonInfoMerge = addonInfo;
                            } else {
                                installedAddonInfoMerge = installedAddonInfo.merge(addonInfo);
                            }
                            installedAddonInfo = installedAddonInfoMerge;
                            reader.close();
                            if (stream != null) {
                                stream.close();
                            }
                        } finally {
                        }
                    } finally {
                    }
                }
            }
            if (installedAddonInfo == null) {
                throw new AddonLoadException("No addon json was found on the classpath");
            }
            this.classLoader = new AddonClassLoader((URL[]) urls.toArray(new URL[0]), ClasspathAddonLoader.class.getClassLoader(), installedAddonInfo);
            PlatformEnvironment.getPlatformClassloader().registerChildClassloader(installedAddonInfo.getNamespace(), this.classLoader);
        } catch (Exception exception) {
            throw new AddonLoadException("An error occurred while searching for the addon json", exception);
        }
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void completed() throws Exception {
        if (this.classLoader == null) {
            return;
        }
        this.addonLoader.addonPreparer().prepareAddon(this.classLoader, AddonPreparer.AddonPrepareContext.CLASSPATH);
    }

    public InstalledAddonInfo getAddonInfo() {
        if (this.classLoader == null) {
            return null;
        }
        return this.classLoader.getAddonInfo();
    }
}
