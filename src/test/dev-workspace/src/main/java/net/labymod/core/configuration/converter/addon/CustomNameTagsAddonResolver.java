package net.labymod.core.configuration.converter.addon;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.api.configuration.converter.addon.CustomLegacyAddon;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;
import net.labymod.api.util.GsonUtil;
import net.labymod.core.flint.FlintUrls;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/addon/CustomNameTagsAddonResolver.class */
public class CustomNameTagsAddonResolver implements LegacyAddonResolver {
    @Override // net.labymod.api.configuration.converter.addon.LegacyAddonResolver
    public void resolveAddons(LegacyAddonConverter.Version version, Collection<LegacyAddon> addons) throws IOException {
        Path path = LegacyConverter.LEGACY_PATH.resolve("tags.json");
        if (Files.notExists(path, new LinkOption[0])) {
            return;
        }
        Reader reader = new InputStreamReader(Files.newInputStream(path, new OpenOption[0]));
        try {
            JsonObject object = (JsonObject) GsonUtil.DEFAULT_GSON.fromJson(reader, JsonObject.class);
            if (object != null && object.has(FlintUrls.QUERY_TAGS_PARAM) && object.getAsJsonObject(FlintUrls.QUERY_TAGS_PARAM).entrySet().size() != 0) {
                addons.add(new CustomLegacyAddon(version, "CustomNameTags", "customnametags"));
            }
            reader.close();
        } catch (Throwable th) {
            try {
                reader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
