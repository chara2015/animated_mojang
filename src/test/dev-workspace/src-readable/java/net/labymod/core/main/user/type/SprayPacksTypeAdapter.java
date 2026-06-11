package net.labymod.core.main.user.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.labymod.core.main.user.shop.spray.model.SprayPacks;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/type/SprayPacksTypeAdapter.class */
public class SprayPacksTypeAdapter implements JsonDeserializer<SprayPacks>, JsonSerializer<SprayPacks> {
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public SprayPacks m1157deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SprayPacks sprayPacks = new SprayPacks();
        if (!json.isJsonObject()) {
            return sprayPacks;
        }
        JsonObject stickerObject = json.getAsJsonObject();
        if (stickerObject.has("p") && stickerObject.get("p").isJsonArray()) {
            JsonArray packArray = stickerObject.get("p").getAsJsonArray();
            for (int i = 0; i < packArray.size(); i++) {
                sprayPacks.getShorts().add(Short.valueOf(packArray.get(i).getAsShort()));
            }
        }
        return sprayPacks;
    }

    public JsonElement serialize(SprayPacks packs, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        for (Short id : packs.getShorts()) {
            array.add(new JsonPrimitive(id));
        }
        obj.add("p", array);
        return obj;
    }
}
