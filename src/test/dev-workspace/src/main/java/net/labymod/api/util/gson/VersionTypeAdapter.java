package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.serial.VersionDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/VersionTypeAdapter.class */
public class VersionTypeAdapter implements JsonSerializer<Version>, JsonDeserializer<Version> {
    public JsonElement serialize(Version version, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(version.toString());
    }

    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public Version m568deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String version = json.getAsString();
        return VersionDeserializer.from(version);
    }
}
