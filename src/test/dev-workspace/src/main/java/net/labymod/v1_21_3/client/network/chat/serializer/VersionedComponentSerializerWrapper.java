package net.labymod.v1_21_3.client.network.chat.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.v1_21_3.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_21_3.client.network.chat.VersionedBaseComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/network/chat/serializer/VersionedComponentSerializerWrapper.class */
public class VersionedComponentSerializerWrapper implements Serializer, JsonDeserializer<VersionedBaseComponent<?, ?>>, JsonSerializer<Component> {
    private final b serializerAdapter = new b(ke.b);

    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public VersionedBaseComponent<?, ?> m1899deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MutableComponentAccessor deserialized = this.serializerAdapter.a(json, typeOfT, context);
        return deserialized.getLabyComponent();
    }

    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        yj holder = ((VersionedBaseComponent) src).getHolder();
        return this.serializerAdapter.a(holder, typeOfSrc, context);
    }
}
