package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/PostProcessorTargetTypeAdapter.class */
public class PostProcessorTargetTypeAdapter extends LabyGsonTypeAdapter<PostProcessorTarget> {
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public PostProcessorTarget m563deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new PostProcessorTarget.FullSizeTarget(json.getAsString());
        }
        if (!json.isJsonObject()) {
            throw new JsonParseException("Expected an object");
        }
        JsonObject object = json.getAsJsonObject();
        if (object.has("width") || object.has("height")) {
            return (PostProcessorTarget) context.deserialize(json, PostProcessorTarget.CustomSizeTarget.class);
        }
        return (PostProcessorTarget) context.deserialize(json, PostProcessorTarget.FullSizeTarget.class);
    }

    public JsonElement serialize(PostProcessorTarget src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        if (src instanceof PostProcessorTarget.FullSizeTarget) {
            return new JsonPrimitive(src.getName());
        }
        return context.serialize(src);
    }
}
