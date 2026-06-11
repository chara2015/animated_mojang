package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/RootChatTabConfigTypeAdapter.class */
public class RootChatTabConfigTypeAdapter extends LabyGsonTypeAdapter<RootChatTabConfig.Type> {
    private static final String IDENTIFIER_KEY = "identifier";

    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public RootChatTabConfig.Type m565deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String typeIdentifier;
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            if (object.has(IDENTIFIER_KEY)) {
                typeIdentifier = object.get(IDENTIFIER_KEY).getAsString();
            } else {
                typeIdentifier = RootChatTabConfig.Type.CUSTOM.getIdentifier();
            }
        } else {
            typeIdentifier = json.getAsString();
        }
        return RootChatTabConfig.Type.of(typeIdentifier);
    }

    public JsonElement serialize(RootChatTabConfig.Type src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getIdentifier());
    }
}
