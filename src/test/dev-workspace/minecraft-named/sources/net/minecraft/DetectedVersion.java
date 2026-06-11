package net.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.WorldVersion;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.storage.DataVersion;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/DetectedVersion.class */
public class DetectedVersion {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final WorldVersion BUILT_IN = createBuiltIn(UUID.randomUUID().toString().replaceAll("-", ""), "Development Version");

    public static WorldVersion createBuiltIn(String $$0, String $$1) {
        return createBuiltIn($$0, $$1, true);
    }

    public static WorldVersion createBuiltIn(String $$0, String $$1, boolean $$2) {
        return new WorldVersion.Simple($$0, $$1, new DataVersion(SharedConstants.WORLD_VERSION, "main"), SharedConstants.getProtocolVersion(), PackFormat.of(75, 0), PackFormat.of(94, 1), new Date(), $$2);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    private static WorldVersion createFromJson(JsonObject $$0) throws JsonSyntaxException {
        JsonObject $$1 = GsonHelper.getAsJsonObject($$0, "pack_version");
        return new WorldVersion.Simple(GsonHelper.getAsString($$0, Entity.TAG_ID), GsonHelper.getAsString($$0, JigsawBlockEntity.NAME), new DataVersion(GsonHelper.getAsInt($$0, "world_version"), GsonHelper.getAsString($$0, "series_id", "main")), GsonHelper.getAsInt($$0, "protocol_version"), PackFormat.of(GsonHelper.getAsInt($$1, "resource_major"), GsonHelper.getAsInt($$1, "resource_minor")), PackFormat.of(GsonHelper.getAsInt($$1, "data_major"), GsonHelper.getAsInt($$1, "data_minor")), Date.from(ZonedDateTime.parse(GsonHelper.getAsString($$0, "build_time")).toInstant()), GsonHelper.getAsBoolean($$0, "stable"));
    }

    public static WorldVersion tryDetectVersion() {
        try {
            InputStream $$0 = DetectedVersion.class.getResourceAsStream("/version.json");
            try {
                if ($$0 == null) {
                    LOGGER.warn("Missing version information!");
                    WorldVersion worldVersion = BUILT_IN;
                    if ($$0 != null) {
                        $$0.close();
                    }
                    return worldVersion;
                }
                InputStreamReader $$1 = new InputStreamReader($$0, StandardCharsets.UTF_8);
                try {
                    WorldVersion worldVersionCreateFromJson = createFromJson(GsonHelper.parse($$1));
                    $$1.close();
                    if ($$0 != null) {
                        $$0.close();
                    }
                    return worldVersionCreateFromJson;
                } catch (Throwable th) {
                    try {
                        $$1.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                if ($$0 != null) {
                    try {
                        $$0.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        } catch (IOException | JsonParseException $$2) {
            throw new IllegalStateException("Game version information is corrupt", $$2);
        }
    }
}
