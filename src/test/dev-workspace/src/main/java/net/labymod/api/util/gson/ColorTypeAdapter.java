package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/ColorTypeAdapter.class */
public class ColorTypeAdapter extends LabyGsonTypeAdapter<Color> {
    private static final String VALUE_KEY = "value";
    private static final String CHROMA_KEY = "chroma";
    private static final String CHROMA_SPEED_KEY = "chromaSpeed";

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public Color m556deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException, JsonSyntaxException {
        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return Color.of(primitive.getAsInt());
            }
        }
        if (!json.isJsonObject()) {
            throw new JsonSyntaxException("Expected color to be a number or json object, but was " + getType(json));
        }
        JsonObject colorObject = json.getAsJsonObject();
        if (!colorObject.has(VALUE_KEY)) {
            throw new JsonSyntaxException("Expected color to have a value");
        }
        JsonElement valueElement = colorObject.get(VALUE_KEY);
        if (!valueElement.isJsonPrimitive()) {
            throw new JsonSyntaxException("Expected color value to be a number, but was " + getType(valueElement));
        }
        JsonPrimitive valuePrimitive = valueElement.getAsJsonPrimitive();
        if (!valuePrimitive.isNumber()) {
            throw new JsonSyntaxException("Expected color value to be a number, but was " + getType(valueElement));
        }
        Color color = Color.of(valuePrimitive.getAsInt());
        Float speed = null;
        if (colorObject.has(CHROMA_SPEED_KEY)) {
            JsonElement chromaSpeedElement = colorObject.get(CHROMA_SPEED_KEY);
            if (chromaSpeedElement.isJsonPrimitive()) {
                JsonPrimitive chromaSpeedPrimitive = chromaSpeedElement.getAsJsonPrimitive();
                if (chromaSpeedPrimitive.isNumber()) {
                    speed = Float.valueOf(chromaSpeedPrimitive.getAsFloat());
                }
            }
        }
        if (colorObject.has(CHROMA_KEY)) {
            JsonElement chromaElement = colorObject.get(CHROMA_KEY);
            if (chromaElement.isJsonPrimitive()) {
                JsonPrimitive chromaPrimitive = chromaElement.getAsJsonPrimitive();
                if (chromaPrimitive.isBoolean() && chromaPrimitive.getAsBoolean()) {
                    return speed != null ? color.withChroma(speed.floatValue()) : color.withChroma();
                }
            }
        }
        return speed != null ? color.withoutChroma(speed.floatValue()) : color;
    }

    public JsonElement serialize(Color color, Type typeOfSrc, JsonSerializationContext context) {
        boolean chroma = color.isChroma();
        boolean defaultChromaSpeed = color.isDefaultChromaSpeed();
        if (chroma || !defaultChromaSpeed) {
            JsonObject colorObject = new JsonObject();
            colorObject.addProperty(VALUE_KEY, Integer.valueOf(color.withoutChroma().get()));
            if (chroma) {
                colorObject.addProperty(CHROMA_KEY, true);
            }
            if (!defaultChromaSpeed) {
                colorObject.addProperty(CHROMA_SPEED_KEY, Float.valueOf(color.getChromaSpeed()));
            }
            return colorObject;
        }
        return new JsonPrimitive(Integer.valueOf(color.get()));
    }

    private String getType(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return "boolean";
            }
            if (primitive.isString()) {
                return "string";
            }
            if (primitive.isNumber()) {
                return "number";
            }
            return "unknown";
        }
        if (element.isJsonArray()) {
            return "array";
        }
        if (element.isJsonObject()) {
            return "object";
        }
        if (element.isJsonNull()) {
            return "null";
        }
        return "unknown";
    }
}
