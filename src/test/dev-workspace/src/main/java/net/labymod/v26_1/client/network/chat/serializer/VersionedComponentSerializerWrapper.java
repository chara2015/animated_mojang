package net.labymod.v26_1.client.network.chat.serializer;

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
import net.labymod.api.util.CastUtil;
import net.labymod.v26_1.client.network.chat.MutableComponentAccessor;
import net.labymod.v26_1.client.network.chat.VersionedBaseComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/network/chat/serializer/VersionedComponentSerializerWrapper.class */
public class VersionedComponentSerializerWrapper implements Serializer, JsonDeserializer<VersionedBaseComponent<?, ?>>, JsonSerializer<Component> {
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public VersionedBaseComponent<?, ?> m2180deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MutableComponentAccessor deserialized = (MutableComponentAccessor) CastUtil.requireInstanceOf(deserialize(json, RegistryAccess.EMPTY), MutableComponentAccessor.class);
        return deserialized.getLabyComponent();
    }

    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        MutableComponent holder = ((VersionedBaseComponent) src).getHolder();
        return serialize(holder, RegistryAccess.EMPTY);
    }

    MutableComponent deserialize(JsonElement element, HolderLookup.Provider provider) {
        return (MutableComponent) ComponentSerialization.CODEC.parse(provider.createSerializationContext(JsonOps.INSTANCE), element).getOrThrow(JsonParseException::new);
    }

    JsonElement serialize(net.minecraft.network.chat.Component component, HolderLookup.Provider provider) {
        return (JsonElement) ComponentSerialization.CODEC.encodeStart(provider.createSerializationContext(JsonOps.INSTANCE), component).getOrThrow(JsonParseException::new);
    }
}
