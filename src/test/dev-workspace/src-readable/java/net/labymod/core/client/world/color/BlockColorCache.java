package net.labymod.core.client.world.color;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.MinecraftVersion;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.gson.ResourceLocationTypeAdapter;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.util.classpath.ClasspathUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/color/BlockColorCache.class */
public class BlockColorCache {
    private static final Logging LOGGER = Logging.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Version.class, new VersionDeserializer()).registerTypeAdapter(ResourceLocation.class, new ResourceLocationTypeAdapter()).create();
    private static final TypeToken<?> TOKEN = TypeToken.getParameterized(Map.class, new Type[]{Version.class, TypeToken.getParameterized(Map.class, new Type[]{String.class, BlockColor.class}).getType()});
    private final Map<Version, Map<String, BlockColor>> colors = new HashMap();
    private final Map<String, BlockColor> currentColors = new HashMap();

    public BlockColorCache() {
        load();
    }

    private void load() {
        try {
            InputStreamReader reader = new InputStreamReader(ClasspathUtil.getResourceAsInputStream("labymod", "assets/labymod/data/block_colors.json", false));
            try {
                this.colors.putAll((Map) GSON.fromJson(reader, TOKEN.getType()));
                this.currentColors.putAll(findCurrentColors(MinecraftVersions.current()));
                reader.close();
            } finally {
            }
        } catch (IOException exception) {
            LOGGER.error("Failed to load block colors", exception);
        }
    }

    public BlockColor getBlockColor(String blockId) {
        return this.currentColors.get(blockId);
    }

    private Map<String, BlockColor> findCurrentColors(MinecraftVersion version) {
        return this.colors.get(version.version());
    }
}
