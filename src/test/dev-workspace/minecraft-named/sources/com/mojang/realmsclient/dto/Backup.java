package com.mojang.realmsclient.dto;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.util.JsonUtils;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/Backup.class */
public class Backup extends ValueObject {
    private static final Logger LOGGER = LogUtils.getLogger();
    public final String backupId;
    public final Instant lastModified;
    public final long size;
    public boolean uploadedVersion;
    public final Map<String, String> metadata;
    public final Map<String, String> changeList = new HashMap();

    private Backup(String $$0, Instant $$1, long $$2, Map<String, String> $$3) {
        this.backupId = $$0;
        this.lastModified = $$1;
        this.size = $$2;
        this.metadata = $$3;
    }

    public ZonedDateTime lastModifiedDate() {
        return ZonedDateTime.ofInstant(this.lastModified, ZoneId.systemDefault());
    }

    public static Backup parse(JsonElement $$0) {
        JsonObject $$1 = $$0.getAsJsonObject();
        try {
            String $$2 = JsonUtils.getStringOr("backupId", $$1, "");
            Instant $$3 = JsonUtils.getDateOr("lastModifiedDate", $$1);
            long $$4 = JsonUtils.getLongOr(StructureTemplate.SIZE_TAG, $$1, 0L);
            Map<String, String> $$5 = new HashMap<>();
            if ($$1.has("metadata")) {
                JsonObject $$6 = $$1.getAsJsonObject("metadata");
                Set<Map.Entry<String, JsonElement>> $$7 = $$6.entrySet();
                for (Map.Entry<String, JsonElement> $$8 : $$7) {
                    if (!$$8.getValue().isJsonNull()) {
                        $$5.put($$8.getKey(), $$8.getValue().getAsString());
                    }
                }
            }
            return new Backup($$2, $$3, $$4, $$5);
        } catch (Exception $$9) {
            LOGGER.error("Could not parse Backup", $$9);
            return null;
        }
    }
}
