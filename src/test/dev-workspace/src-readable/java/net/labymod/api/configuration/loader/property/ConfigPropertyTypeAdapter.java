package net.labymod.api.configuration.loader.property;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import net.labymod.api.util.PrimitiveHelper;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/property/ConfigPropertyTypeAdapter.class */
public class ConfigPropertyTypeAdapter implements JsonSerializer<ConfigProperty<?>>, JsonDeserializer<Object> {
    public JsonElement serialize(ConfigProperty<?> src, Type typeOfSrc, JsonSerializationContext context) {
        if (typeOfSrc instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) typeOfSrc;
            if (Reflection.isType(parameterizedType, PrimitiveHelper.BOOLEAN)) {
                return new JsonPrimitive((Boolean) src.getActualValue());
            }
            if (Reflection.isType(parameterizedType, PrimitiveHelper.NUMBER_PRIMITIVES)) {
                return new JsonPrimitive((Number) src.getActualValue());
            }
            if (Reflection.isType(parameterizedType, String.class)) {
                return new JsonPrimitive((String) src.getActualValue());
            }
            if (Reflection.isType(parameterizedType, Character.class)) {
                return new JsonPrimitive((Character) src.getActualValue());
            }
            return context.serialize(src.getActualValue());
        }
        return null;
    }

    public Object deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type actualType = parameterizedType.getActualTypeArguments()[0];
            return context.deserialize(json, actualType);
        }
        return null;
    }
}
