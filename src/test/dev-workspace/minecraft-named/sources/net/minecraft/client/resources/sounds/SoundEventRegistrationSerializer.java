package net.minecraft.client.resources.sounds;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import org.apache.commons.lang3.Validate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/sounds/SoundEventRegistrationSerializer.class */
public class SoundEventRegistrationSerializer implements JsonDeserializer<SoundEventRegistration> {
    private static final FloatProvider DEFAULT_FLOAT = ConstantFloat.of(1.0f);

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public SoundEventRegistration m1165deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException, JsonSyntaxException {
        JsonObject $$3 = GsonHelper.convertToJsonObject($$0, "entry");
        boolean $$4 = GsonHelper.getAsBoolean($$3, "replace", false);
        String $$5 = GsonHelper.getAsString($$3, "subtitle", null);
        List<Sound> $$6 = getSounds($$3);
        return new SoundEventRegistration($$6, $$4, $$5);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    private List<Sound> getSounds(JsonObject $$0) throws JsonSyntaxException {
        List<Sound> $$1 = Lists.newArrayList();
        if ($$0.has("sounds")) {
            JsonArray $$2 = GsonHelper.getAsJsonArray($$0, "sounds");
            for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
                JsonElement $$4 = $$2.get($$3);
                if (GsonHelper.isStringValue($$4)) {
                    Identifier $$5 = Identifier.parse(GsonHelper.convertToString($$4, "sound"));
                    $$1.add(new Sound($$5, DEFAULT_FLOAT, DEFAULT_FLOAT, 1, Sound.Type.FILE, false, false, 16));
                } else {
                    $$1.add(getSound(GsonHelper.convertToJsonObject($$4, "sound")));
                }
            }
        }
        return $$1;
    }

    private Sound getSound(JsonObject $$0) {
        Identifier $$1 = Identifier.parse(GsonHelper.getAsString($$0, JigsawBlockEntity.NAME));
        Sound.Type $$2 = getType($$0, Sound.Type.FILE);
        float $$3 = GsonHelper.getAsFloat($$0, "volume", 1.0f);
        Validate.isTrue($$3 > 0.0f, "Invalid volume", new Object[0]);
        float $$4 = GsonHelper.getAsFloat($$0, "pitch", 1.0f);
        Validate.isTrue($$4 > 0.0f, "Invalid pitch", new Object[0]);
        int $$5 = GsonHelper.getAsInt($$0, "weight", 1);
        Validate.isTrue($$5 > 0, "Invalid weight", new Object[0]);
        boolean $$6 = GsonHelper.getAsBoolean($$0, "preload", false);
        boolean $$7 = GsonHelper.getAsBoolean($$0, "stream", false);
        int $$8 = GsonHelper.getAsInt($$0, "attenuation_distance", 16);
        return new Sound($$1, ConstantFloat.of($$3), ConstantFloat.of($$4), $$5, $$2, $$7, $$6, $$8);
    }

    private Sound.Type getType(JsonObject $$0, Sound.Type $$1) {
        Sound.Type $$2 = $$1;
        if ($$0.has(ChunkRegionIoEvent.Fields.TYPE)) {
            $$2 = Sound.Type.getByName(GsonHelper.getAsString($$0, ChunkRegionIoEvent.Fields.TYPE));
            Objects.requireNonNull($$2, "Invalid type");
        }
        return $$2;
    }
}
