package net.labymod.api.util.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.core.client.render.schematic.block.ParameterType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/NBTTagTypeAdapter.class */
public class NBTTagTypeAdapter implements JsonSerializer<NBTTag<?>>, JsonDeserializer<NBTTag<?>> {
    private final NBTFactory nbtFactory = Laby.references().nbtFactory();

    public JsonElement serialize(NBTTag tag, Type type, JsonSerializationContext context) {
        return toJsonObject(tag, context);
    }

    private JsonObject toJsonObject(NBTTag<?> tag, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty(ParameterType.TYPE, tag.type().name());
        obj.add("value", toJsonElementValue(tag, context));
        return obj;
    }

    private JsonElement toJsonElementValue(NBTTag<?> tag, JsonSerializationContext context) {
        Object value = tag.value();
        if (value == null) {
            return JsonNull.INSTANCE;
        }
        switch (tag.type()) {
            case COMPOUND:
                JsonObject object = new JsonObject();
                NBTTagCompound compound = (NBTTagCompound) tag;
                for (String key : compound.keySet()) {
                    NBTTag<?> entryValue = compound.get(key);
                    if (entryValue != null) {
                        object.add(key, toJsonObject(entryValue, context));
                    }
                }
                return object;
            case BYTE_ARRAY:
                byte[] array = (byte[]) value;
                JsonArray jsonArray = new JsonArray();
                for (byte b : array) {
                    jsonArray.add(new JsonPrimitive(Byte.valueOf(b)));
                }
                return jsonArray;
            case INT_ARRAY:
                int[] array2 = (int[]) value;
                JsonArray jsonArray2 = new JsonArray();
                for (int i : array2) {
                    jsonArray2.add(new JsonPrimitive(Integer.valueOf(i)));
                }
                return jsonArray2;
            case LONG_ARRAY:
                long[] array3 = (long[]) value;
                JsonArray jsonArray3 = new JsonArray();
                for (long l : array3) {
                    jsonArray3.add(new JsonPrimitive(Long.valueOf(l)));
                }
                return jsonArray3;
            case LIST:
                JsonArray jsonArray4 = new JsonArray();
                NBTTagList list = (NBTTagList) tag;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    NBTTag<?> entryValue2 = list.get(i2);
                    if (entryValue2 != null) {
                        jsonArray4.add(toJsonObject(entryValue2, context));
                    }
                }
                return jsonArray4;
            default:
                return context.serialize(tag.value());
        }
    }

    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public NBTTag<?> m560deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        return fromJsonObject(json.getAsJsonObject(), context);
    }

    private NBTTag<?> fromJsonObject(JsonObject element, JsonDeserializationContext context) {
        JsonElement value = element.get("value");
        if (value.isJsonNull()) {
            return null;
        }
        NBTTagType type = NBTTagType.valueOf(element.get(ParameterType.TYPE).getAsString());
        switch (type) {
            case COMPOUND:
                NBTTagCompound compound = this.nbtFactory.compound();
                JsonObject obj = value.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                    String key = entry.getKey();
                    JsonObject objValue = obj.get(key).getAsJsonObject();
                    NBTTag<?> tag = fromJsonObject(objValue, context);
                    if (tag != null) {
                        compound.set(key, tag);
                    }
                }
                return compound;
            case BYTE_ARRAY:
                JsonArray array = value.getAsJsonArray();
                byte[] bytes = new byte[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    bytes[i] = array.get(i).getAsByte();
                }
                return this.nbtFactory.create(bytes);
            case INT_ARRAY:
                JsonArray array2 = value.getAsJsonArray();
                int[] ints = new int[array2.size()];
                for (int i2 = 0; i2 < array2.size(); i2++) {
                    ints[i2] = array2.get(i2).getAsInt();
                }
                return this.nbtFactory.create(ints);
            case LONG_ARRAY:
                JsonArray array3 = value.getAsJsonArray();
                long[] longs = new long[array3.size()];
                for (int i3 = 0; i3 < array3.size(); i3++) {
                    longs[i3] = array3.get(i3).getAsLong();
                }
                return this.nbtFactory.create(longs);
            case LIST:
                JsonArray array4 = value.getAsJsonArray();
                NBTTagList list = this.nbtFactory.list();
                for (int i4 = 0; i4 < array4.size(); i4++) {
                    JsonObject objValue2 = array4.get(i4).getAsJsonObject();
                    NBTTag<?> tag2 = fromJsonObject(objValue2, context);
                    if (tag2 != null) {
                        list.add(tag2);
                    }
                }
                return list;
            case STRING:
                return this.nbtFactory.create(value.getAsString());
            case BYTE:
                return this.nbtFactory.create(value.getAsByte());
            case SHORT:
                return this.nbtFactory.create(value.getAsShort());
            case INT:
            case ANY_NUMERIC:
                return this.nbtFactory.create(value.getAsInt());
            case LONG:
                return this.nbtFactory.create(value.getAsLong());
            case FLOAT:
                return this.nbtFactory.create(value.getAsFloat());
            case DOUBLE:
                return this.nbtFactory.create(value.getAsDouble());
            default:
                return null;
        }
    }
}
