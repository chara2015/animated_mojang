package net.labymod.v1_21_4.client.network.chat.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.serialization.JsonOps;
import java.lang.reflect.Type;
import net.labymod.api.client.component.serializer.Serializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/network/chat/serializer/VersionedStyleSerializer.class */
public class VersionedStyleSerializer implements Serializer, JsonDeserializer<xm>, JsonSerializer<xm> {
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public xm m1956deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (xm) b.b.parse(JsonOps.INSTANCE, json).getOrThrow(JsonParseException::new);
    }

    public JsonElement serialize(xm src, Type typeOfSrc, JsonSerializationContext context) {
        return (JsonElement) b.b.encodeStart(JsonOps.INSTANCE, src).getOrThrow(JsonParseException::new);
    }
}
