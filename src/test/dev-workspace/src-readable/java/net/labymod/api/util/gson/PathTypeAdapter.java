package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/PathTypeAdapter.class */
public class PathTypeAdapter extends LabyGsonTypeAdapter<Path> {
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public Path m562deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) {
            return null;
        }
        if (!json.isJsonPrimitive()) {
            throw new JsonParseException("Json cannot be parsed to a path: " + String.valueOf(json));
        }
        return Paths.get(json.getAsString(), new String[0]);
    }

    public JsonElement serialize(Path src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        return new JsonPrimitive(src.toString());
    }
}
