package com.mojang.realmsclient.dto;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsSlot.class */
public final class RealmsSlot implements ReflectionBasedSerialization {

    @SerializedName("slotId")
    public int slotId;

    @SerializedName("options")
    @JsonAdapter(RealmsWorldOptionsJsonAdapter.class)
    public RealmsWorldOptions options;

    @SerializedName("settings")
    public List<RealmsSetting> settings;

    public RealmsSlot(int $$0, RealmsWorldOptions $$1, List<RealmsSetting> $$2) {
        this.slotId = $$0;
        this.options = $$1;
        this.settings = $$2;
    }

    public static RealmsSlot defaults(int $$0) {
        return new RealmsSlot($$0, RealmsWorldOptions.createEmptyDefaults(), List.of(RealmsSetting.hardcoreSetting(false)));
    }

    public RealmsSlot copy() {
        return new RealmsSlot(this.slotId, this.options.copy(), new ArrayList(this.settings));
    }

    public boolean isHardcore() {
        return RealmsSetting.isHardcore(this.settings);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsSlot$RealmsWorldOptionsJsonAdapter.class */
    static class RealmsWorldOptionsJsonAdapter extends TypeAdapter<RealmsWorldOptions> {
        private RealmsWorldOptionsJsonAdapter() {
        }

        public void write(JsonWriter $$0, RealmsWorldOptions $$1) throws IOException {
            $$0.jsonValue(new GuardedSerializer().toJson($$1));
        }

        /* JADX INFO: renamed from: read, reason: merged with bridge method [inline-methods] */
        public RealmsWorldOptions m120read(JsonReader $$0) throws IOException {
            String $$1 = $$0.nextString();
            return RealmsWorldOptions.parse(new GuardedSerializer(), $$1);
        }
    }
}
