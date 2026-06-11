package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.models.addon.info.AddonEntrypoint;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/AddonEntrypointTypeAdapter.class */
public class AddonEntrypointTypeAdapter extends LabyGsonTypeAdapter<AddonEntrypoint> {
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public AddonEntrypoint m555deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new AddonEntrypoint(json.getAsString(), null, SubmissionOrders.DEBUG);
        }
        JsonObject object = json.getAsJsonObject();
        return new AddonEntrypoint(object.get("name").getAsString(), object.has("version") ? object.get("version").getAsString() : null, object.get("priority").getAsInt());
    }

    public JsonElement serialize(AddonEntrypoint src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}
