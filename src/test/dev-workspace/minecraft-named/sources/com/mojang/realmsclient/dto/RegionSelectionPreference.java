package com.mojang.realmsclient.dto;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RegionSelectionPreference.class */
public enum RegionSelectionPreference {
    AUTOMATIC_PLAYER(0, "realms.configuration.region_preference.automatic_player"),
    AUTOMATIC_OWNER(1, "realms.configuration.region_preference.automatic_owner"),
    MANUAL(2, "");

    public final int id;
    public final String translationKey;
    public static final RegionSelectionPreference DEFAULT_SELECTION = AUTOMATIC_PLAYER;

    RegionSelectionPreference(int $$0, String $$1) {
        this.id = $$0;
        this.translationKey = $$1;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RegionSelectionPreference$RegionSelectionPreferenceJsonAdapter.class */
    public static class RegionSelectionPreferenceJsonAdapter extends TypeAdapter<RegionSelectionPreference> {
        private static final Logger LOGGER = LogUtils.getLogger();

        public void write(JsonWriter $$0, RegionSelectionPreference $$1) throws IOException {
            $$0.value($$1.id);
        }

        /* JADX INFO: renamed from: read, reason: merged with bridge method [inline-methods] */
        public RegionSelectionPreference m123read(JsonReader $$0) throws IOException {
            int $$1 = $$0.nextInt();
            for (RegionSelectionPreference $$2 : RegionSelectionPreference.values()) {
                if ($$2.id == $$1) {
                    return $$2;
                }
            }
            LOGGER.warn("Unsupported RegionSelectionPreference {}", Integer.valueOf($$1));
            return RegionSelectionPreference.DEFAULT_SELECTION;
        }
    }
}
