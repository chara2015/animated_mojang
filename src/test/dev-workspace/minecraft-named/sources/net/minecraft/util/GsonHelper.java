package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Strictness;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/GsonHelper.class */
public class GsonHelper {
    private static final Gson GSON = new GsonBuilder().create();

    public static boolean isStringValue(JsonObject $$0, String $$1) {
        if (!isValidPrimitive($$0, $$1)) {
            return false;
        }
        return $$0.getAsJsonPrimitive($$1).isString();
    }

    public static boolean isStringValue(JsonElement $$0) {
        if (!$$0.isJsonPrimitive()) {
            return false;
        }
        return $$0.getAsJsonPrimitive().isString();
    }

    public static boolean isNumberValue(JsonObject $$0, String $$1) {
        if (!isValidPrimitive($$0, $$1)) {
            return false;
        }
        return $$0.getAsJsonPrimitive($$1).isNumber();
    }

    public static boolean isNumberValue(JsonElement $$0) {
        if (!$$0.isJsonPrimitive()) {
            return false;
        }
        return $$0.getAsJsonPrimitive().isNumber();
    }

    public static boolean isBooleanValue(JsonObject $$0, String $$1) {
        if (!isValidPrimitive($$0, $$1)) {
            return false;
        }
        return $$0.getAsJsonPrimitive($$1).isBoolean();
    }

    public static boolean isBooleanValue(JsonElement $$0) {
        if (!$$0.isJsonPrimitive()) {
            return false;
        }
        return $$0.getAsJsonPrimitive().isBoolean();
    }

    public static boolean isArrayNode(JsonObject $$0, String $$1) {
        if (!isValidNode($$0, $$1)) {
            return false;
        }
        return $$0.get($$1).isJsonArray();
    }

    public static boolean isObjectNode(JsonObject $$0, String $$1) {
        if (!isValidNode($$0, $$1)) {
            return false;
        }
        return $$0.get($$1).isJsonObject();
    }

    public static boolean isValidPrimitive(JsonObject $$0, String $$1) {
        if (!isValidNode($$0, $$1)) {
            return false;
        }
        return $$0.get($$1).isJsonPrimitive();
    }

    public static boolean isValidNode(JsonObject $$0, String $$1) {
        return ($$0 == null || $$0.get($$1) == null) ? false : true;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static JsonElement getNonNull(JsonObject $$0, String $$1) throws JsonSyntaxException {
        JsonElement $$2 = $$0.get($$1);
        if ($$2 == null || $$2.isJsonNull()) {
            throw new JsonSyntaxException("Missing field " + $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static String convertToString(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive()) {
            return $$0.getAsString();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a string, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static String getAsString(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToString($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a string");
    }

    @Contract("_,_,!null->!null;_,_,null->_")
    public static String getAsString(JsonObject $$0, String $$1, String $$2) {
        if ($$0.has($$1)) {
            return convertToString($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static Holder<Item> convertToItem(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive()) {
            String $$2 = $$0.getAsString();
            return BuiltInRegistries.ITEM.get(Identifier.parse($$2)).orElseThrow(() -> {
                return new JsonSyntaxException("Expected " + $$1 + " to be an item, was unknown string '" + $$2 + "'");
            });
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be an item, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static Holder<Item> getAsItem(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToItem($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find an item");
    }

    @Contract("_,_,!null->!null;_,_,null->_")
    public static Holder<Item> getAsItem(JsonObject $$0, String $$1, Holder<Item> $$2) {
        if ($$0.has($$1)) {
            return convertToItem($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static boolean convertToBoolean(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive()) {
            return $$0.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Boolean, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static boolean getAsBoolean(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToBoolean($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Boolean");
    }

    public static boolean getAsBoolean(JsonObject $$0, String $$1, boolean $$2) {
        if ($$0.has($$1)) {
            return convertToBoolean($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static double convertToDouble(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsDouble();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Double, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static double getAsDouble(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToDouble($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Double");
    }

    public static double getAsDouble(JsonObject $$0, String $$1, double $$2) {
        if ($$0.has($$1)) {
            return convertToDouble($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static float convertToFloat(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Float, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static float getAsFloat(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToFloat($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Float");
    }

    public static float getAsFloat(JsonObject $$0, String $$1, float $$2) {
        if ($$0.has($$1)) {
            return convertToFloat($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static long convertToLong(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsLong();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Long, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static long getAsLong(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToLong($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Long");
    }

    public static long getAsLong(JsonObject $$0, String $$1, long $$2) {
        if ($$0.has($$1)) {
            return convertToLong($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static int convertToInt(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Int, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static int getAsInt(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToInt($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Int");
    }

    public static int getAsInt(JsonObject $$0, String $$1, int $$2) {
        if ($$0.has($$1)) {
            return convertToInt($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static byte convertToByte(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsByte();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Byte, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static byte getAsByte(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToByte($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Byte");
    }

    public static byte getAsByte(JsonObject $$0, String $$1, byte $$2) {
        if ($$0.has($$1)) {
            return convertToByte($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static char convertToCharacter(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsCharacter();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Character, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static char getAsCharacter(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToCharacter($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Character");
    }

    public static char getAsCharacter(JsonObject $$0, String $$1, char $$2) {
        if ($$0.has($$1)) {
            return convertToCharacter($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static BigDecimal convertToBigDecimal(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsBigDecimal();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a BigDecimal, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static BigDecimal getAsBigDecimal(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToBigDecimal($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a BigDecimal");
    }

    public static BigDecimal getAsBigDecimal(JsonObject $$0, String $$1, BigDecimal $$2) {
        if ($$0.has($$1)) {
            return convertToBigDecimal($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static BigInteger convertToBigInteger(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsBigInteger();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a BigInteger, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static BigInteger getAsBigInteger(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToBigInteger($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a BigInteger");
    }

    public static BigInteger getAsBigInteger(JsonObject $$0, String $$1, BigInteger $$2) {
        if ($$0.has($$1)) {
            return convertToBigInteger($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static short convertToShort(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsShort();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a Short, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static short getAsShort(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToShort($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Short");
    }

    public static short getAsShort(JsonObject $$0, String $$1, short $$2) {
        if ($$0.has($$1)) {
            return convertToShort($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static JsonObject convertToJsonObject(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonObject()) {
            return $$0.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a JsonObject, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static JsonObject getAsJsonObject(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToJsonObject($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a JsonObject");
    }

    @Contract("_,_,!null->!null;_,_,null->_")
    public static JsonObject getAsJsonObject(JsonObject $$0, String $$1, JsonObject $$2) {
        if ($$0.has($$1)) {
            return convertToJsonObject($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static JsonArray convertToJsonArray(JsonElement $$0, String $$1) throws JsonSyntaxException {
        if ($$0.isJsonArray()) {
            return $$0.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + $$1 + " to be a JsonArray, was " + getType($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static JsonArray getAsJsonArray(JsonObject $$0, String $$1) throws JsonSyntaxException {
        if ($$0.has($$1)) {
            return convertToJsonArray($$0.get($$1), $$1);
        }
        throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a JsonArray");
    }

    @Contract("_,_,!null->!null;_,_,null->_")
    public static JsonArray getAsJsonArray(JsonObject $$0, String $$1, JsonArray $$2) {
        if ($$0.has($$1)) {
            return convertToJsonArray($$0.get($$1), $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static <T> T convertToObject(JsonElement jsonElement, String str, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> cls) throws JsonSyntaxException {
        if (jsonElement != null) {
            return (T) jsonDeserializationContext.deserialize(jsonElement, cls);
        }
        throw new JsonSyntaxException("Missing " + str);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static <T> T getAsObject(JsonObject jsonObject, String str, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> cls) throws JsonSyntaxException {
        if (jsonObject.has(str)) {
            return (T) convertToObject(jsonObject.get(str), str, jsonDeserializationContext, cls);
        }
        throw new JsonSyntaxException("Missing " + str);
    }

    @Contract("_,_,!null,_,_->!null;_,_,null,_,_->_")
    public static <T> T getAsObject(JsonObject jsonObject, String str, T t, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> cls) {
        if (jsonObject.has(str)) {
            return (T) convertToObject(jsonObject.get(str), str, jsonDeserializationContext, cls);
        }
        return t;
    }

    public static String getType(JsonElement $$0) {
        String $$1 = StringUtils.abbreviateMiddle(String.valueOf($$0), "...", 10);
        if ($$0 == null) {
            return "null (missing)";
        }
        if ($$0.isJsonNull()) {
            return "null (json)";
        }
        if ($$0.isJsonArray()) {
            return "an array (" + $$1 + ")";
        }
        if ($$0.isJsonObject()) {
            return "an object (" + $$1 + ")";
        }
        if ($$0.isJsonPrimitive()) {
            JsonPrimitive $$2 = $$0.getAsJsonPrimitive();
            if ($$2.isNumber()) {
                return "a number (" + $$1 + ")";
            }
            if ($$2.isBoolean()) {
                return "a boolean (" + $$1 + ")";
            }
        }
        return $$1;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    public static <T> T fromJson(Gson gson, Reader reader, Class<T> cls) throws JsonParseException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setStrictness(Strictness.STRICT);
            T t = (T) gson.getAdapter(cls).read(jsonReader);
            if (t == null) {
                throw new JsonParseException("JSON data was null or empty");
            }
            return t;
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    public static <T> T fromNullableJson(Gson gson, Reader reader, TypeToken<T> typeToken) throws JsonParseException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setStrictness(Strictness.STRICT);
            return (T) gson.getAdapter(typeToken).read(jsonReader);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    public static <T> T fromJson(Gson gson, Reader reader, TypeToken<T> typeToken) throws JsonParseException {
        T t = (T) fromNullableJson(gson, reader, typeToken);
        if (t == null) {
            throw new JsonParseException("JSON data was null or empty");
        }
        return t;
    }

    public static <T> T fromNullableJson(Gson gson, String str, TypeToken<T> typeToken) {
        return (T) fromNullableJson(gson, new StringReader(str), typeToken);
    }

    public static <T> T fromJson(Gson gson, String str, Class<T> cls) {
        return (T) fromJson(gson, new StringReader(str), cls);
    }

    public static JsonObject parse(String $$0) {
        return parse(new StringReader($$0));
    }

    public static JsonObject parse(Reader $$0) {
        return (JsonObject) fromJson(GSON, $$0, JsonObject.class);
    }

    public static JsonArray parseArray(String $$0) {
        return parseArray(new StringReader($$0));
    }

    public static JsonArray parseArray(Reader $$0) {
        return (JsonArray) fromJson(GSON, $$0, JsonArray.class);
    }

    public static String toStableString(JsonElement $$0) {
        StringWriter $$1 = new StringWriter();
        JsonWriter $$2 = new JsonWriter($$1);
        try {
            writeValue($$2, $$0, Comparator.naturalOrder());
            return $$1.toString();
        } catch (IOException $$3) {
            throw new AssertionError($$3);
        }
    }

    public static void writeValue(JsonWriter $$0, JsonElement $$1, Comparator<String> $$2) throws IOException {
        if ($$1 == null || $$1.isJsonNull()) {
            $$0.nullValue();
            return;
        }
        if ($$1.isJsonPrimitive()) {
            JsonPrimitive $$3 = $$1.getAsJsonPrimitive();
            if ($$3.isNumber()) {
                $$0.value($$3.getAsNumber());
                return;
            } else if ($$3.isBoolean()) {
                $$0.value($$3.getAsBoolean());
                return;
            } else {
                $$0.value($$3.getAsString());
                return;
            }
        }
        if ($$1.isJsonArray()) {
            $$0.beginArray();
            for (JsonElement $$4 : $$1.getAsJsonArray()) {
                writeValue($$0, $$4, $$2);
            }
            $$0.endArray();
            return;
        }
        if ($$1.isJsonObject()) {
            $$0.beginObject();
            for (Map.Entry<String, JsonElement> $$5 : sortByKeyIfNeeded($$1.getAsJsonObject().entrySet(), $$2)) {
                $$0.name($$5.getKey());
                writeValue($$0, $$5.getValue(), $$2);
            }
            $$0.endObject();
            return;
        }
        throw new IllegalArgumentException("Couldn't write " + String.valueOf($$1.getClass()));
    }

    private static Collection<Map.Entry<String, JsonElement>> sortByKeyIfNeeded(Collection<Map.Entry<String, JsonElement>> $$0, Comparator<String> $$1) {
        if ($$1 == null) {
            return $$0;
        }
        List<Map.Entry<String, JsonElement>> $$2 = new ArrayList<>($$0);
        $$2.sort(Map.Entry.comparingByKey($$1));
        return $$2;
    }

    public static boolean encodesLongerThan(JsonElement $$0, int $$1) {
        try {
            Streams.write($$0, new JsonWriter(Streams.writerForAppendable(new CountedAppendable($$1))));
            return false;
        } catch (IOException $$3) {
            throw new UncheckedIOException($$3);
        } catch (IllegalStateException e) {
            return true;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/GsonHelper$CountedAppendable.class */
    static class CountedAppendable implements Appendable {
        private int totalCount;
        private final int limit;

        public CountedAppendable(int $$0) {
            this.limit = $$0;
        }

        private Appendable accountChars(int $$0) {
            this.totalCount += $$0;
            if (this.totalCount > this.limit) {
                throw new IllegalStateException("Character count over limit: " + this.totalCount + " > " + this.limit);
            }
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence $$0) {
            return accountChars($$0.length());
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence $$0, int $$1, int $$2) {
            return accountChars($$2 - $$1);
        }

        @Override // java.lang.Appendable
        public Appendable append(char $$0) {
            return accountChars(1);
        }
    }
}
