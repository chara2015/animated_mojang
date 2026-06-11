package net.labymod.v1_21_10.client.network.chat.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.serialization.JsonOps;
import java.lang.reflect.Type;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.v1_21_10.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_21_10.client.network.chat.VersionedBaseComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/network/chat/serializer/VersionedComponentSerializerWrapper.class */
public class VersionedComponentSerializerWrapper implements Serializer, JsonDeserializer<VersionedBaseComponent<?, ?>>, JsonSerializer<Component> {
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public VersionedBaseComponent<?, ?> m1776deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MutableComponentAccessor deserialized = deserialize(json, jy.b);
        return deserialized.getLabyComponent();
    }

    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        ym holder = ((VersionedBaseComponent) src).getHolder();
        return serialize(holder, jy.b);
    }

    ym deserialize(JsonElement element, a provider) {
        return (ym) xz.a.parse(provider.a(JsonOps.INSTANCE), element).getOrThrow(JsonParseException::new);
    }

    JsonElement serialize(xx component, a provider) {
        return (JsonElement) xz.a.encodeStart(provider.a(JsonOps.INSTANCE), component).getOrThrow(JsonParseException::new);
    }
}
