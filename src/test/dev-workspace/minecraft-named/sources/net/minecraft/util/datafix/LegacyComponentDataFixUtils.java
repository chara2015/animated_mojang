package net.minecraft.util.datafix;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LenientJsonParser;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/LegacyComponentDataFixUtils.class */
public class LegacyComponentDataFixUtils {
    private static final String EMPTY_CONTENTS = createTextComponentJson("");

    public static <T> Dynamic<T> createPlainTextComponent(DynamicOps<T> $$0, String $$1) {
        String $$2 = createTextComponentJson($$1);
        return new Dynamic<>($$0, $$0.createString($$2));
    }

    public static <T> Dynamic<T> createEmptyComponent(DynamicOps<T> $$0) {
        return new Dynamic<>($$0, $$0.createString(EMPTY_CONTENTS));
    }

    public static String createTextComponentJson(String $$0) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty(Display.TextDisplay.TAG_TEXT, $$0);
        return GsonHelper.toStableString($$1);
    }

    public static String createTranslatableComponentJson(String $$0) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty("translate", $$0);
        return GsonHelper.toStableString($$1);
    }

    public static <T> Dynamic<T> createTranslatableComponent(DynamicOps<T> $$0, String $$1) {
        String $$2 = createTranslatableComponentJson($$1);
        return new Dynamic<>($$0, $$0.createString($$2));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static String rewriteFromLenient(String $$0) throws JsonSyntaxException {
        if ($$0.isEmpty() || $$0.equals("null")) {
            return EMPTY_CONTENTS;
        }
        char $$1 = $$0.charAt(0);
        char $$2 = $$0.charAt($$0.length() - 1);
        if (($$1 == '\"' && $$2 == '\"') || (($$1 == '{' && $$2 == '}') || ($$1 == '[' && $$2 == ']'))) {
            try {
                JsonElement $$3 = LenientJsonParser.parse($$0);
                if ($$3.isJsonPrimitive()) {
                    return createTextComponentJson($$3.getAsString());
                }
                return GsonHelper.toStableString($$3);
            } catch (JsonParseException e) {
            }
        }
        return createTextComponentJson($$0);
    }

    public static boolean isStrictlyValidJson(Dynamic<?> $$0) {
        return $$0.asString().result().filter($$02 -> {
            try {
                StrictJsonParser.parse($$02);
                return true;
            } catch (JsonParseException e) {
                return false;
            }
        }).isPresent();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static Optional<String> extractTranslationString(String $$0) throws JsonSyntaxException {
        try {
            JsonElement $$1 = LenientJsonParser.parse($$0);
            if ($$1.isJsonObject()) {
                JsonObject $$2 = $$1.getAsJsonObject();
                JsonElement $$3 = $$2.get("translate");
                if ($$3 != null && $$3.isJsonPrimitive()) {
                    return Optional.of($$3.getAsString());
                }
            }
        } catch (JsonParseException e) {
        }
        return Optional.empty();
    }
}
