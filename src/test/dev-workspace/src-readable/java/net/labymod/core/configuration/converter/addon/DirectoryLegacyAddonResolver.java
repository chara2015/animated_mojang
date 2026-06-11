package net.labymod.core.configuration.converter.addon;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;
import net.labymod.api.configuration.converter.addon.LoadableLegacyAddon;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/addon/DirectoryLegacyAddonResolver.class */
public class DirectoryLegacyAddonResolver implements LegacyAddonResolver {
    private static final Logging LOGGER = Logging.create((Class<?>) DirectoryLegacyAddonResolver.class);

    @Override // net.labymod.api.configuration.converter.addon.LegacyAddonResolver
    public void resolveAddons(LegacyAddonConverter.Version version, Collection<LegacyAddon> addons) throws IOException {
        ZipFile zip;
        ZipEntry addonEntry;
        Path directory = version.getPath();
        if (Files.notExists(directory, new LinkOption[0])) {
            return;
        }
        Stream<Path> files = Files.list(directory);
        try {
            for (Path path : (List) files.collect(Collectors.toList())) {
                String name = path.getFileName().toString();
                if (name.endsWith(".jar")) {
                    try {
                        zip = new ZipFile(path.toFile());
                        try {
                            addonEntry = zip.getEntry("addon.json");
                        } catch (Throwable th) {
                            try {
                                zip.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        LOGGER.error("Failed to load legacy addon for version {} from {}", version.getVersion(), path.getFileName(), e);
                    }
                    if (addonEntry == null) {
                        LOGGER.warn("No addon json in LabyMod 3 addon {}", path);
                        zip.close();
                    } else {
                        Reader reader = new InputStreamReader(zip.getInputStream(addonEntry));
                        try {
                            JsonObject addonJson = (JsonObject) GsonUtil.DEFAULT_GSON.fromJson(reader, JsonObject.class);
                            if (addonJson != null && addonJson.has("uuid") && addonJson.has("name") && addonJson.has("mainClass")) {
                                addons.add(new LoadableLegacyAddon(path, version, addonJson.get("uuid").getAsString(), addonJson.get("name").getAsString(), addonJson.get("mainClass").getAsString()));
                                reader.close();
                                zip.close();
                            } else {
                                LOGGER.warn("Invalid addon json in LabyMod 3 addon {}", path);
                                reader.close();
                                zip.close();
                            }
                        } catch (Throwable th3) {
                            try {
                                reader.close();
                            } catch (Throwable th4) {
                                th3.addSuppressed(th4);
                            }
                            throw th3;
                        }
                    }
                }
            }
            if (files != null) {
                files.close();
            }
        } catch (Throwable th5) {
            if (files != null) {
                try {
                    files.close();
                } catch (Throwable th6) {
                    th5.addSuppressed(th6);
                }
            }
            throw th5;
        }
    }
}
