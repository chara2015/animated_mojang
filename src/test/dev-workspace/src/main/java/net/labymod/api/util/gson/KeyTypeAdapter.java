package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/KeyTypeAdapter.class */
public class KeyTypeAdapter extends LabyGsonTypeAdapter<Key> {
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public Key m559deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (json.getAsJsonPrimitive().isNumber()) {
            return KeyMapper.getKey(json.getAsInt());
        }
        return KeyMapper.getKey(json.getAsString());
    }

    public JsonElement serialize(Key key, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(key.getActualName());
    }
}
