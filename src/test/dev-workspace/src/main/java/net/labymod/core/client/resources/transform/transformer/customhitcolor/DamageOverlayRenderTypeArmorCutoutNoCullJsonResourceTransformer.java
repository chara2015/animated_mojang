package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.models.Implements;
import net.labymod.api.util.InjectionNames;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/transform/transformer/customhitcolor/DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer.class */
@Singleton
@Implements(value = ResourceTransformer.class, key = InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_JSON)
public class DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer implements ResourceTransformer {
    @Inject
    public DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer() {
    }

    @Override // net.labymod.api.client.resources.transform.ResourceTransformer
    public byte[] transform(byte[] resourceData) {
        String content = new String(resourceData);
        JsonElement shaderElement = new JsonParser().parse(content);
        if (!shaderElement.isJsonObject()) {
            return convertToByteArray(shaderElement);
        }
        JsonObject object = shaderElement.getAsJsonObject();
        if (!object.has("samplers") || !object.get("samplers").isJsonArray()) {
            return convertToByteArray(shaderElement);
        }
        JsonArray samplers = object.get("samplers").getAsJsonArray();
        JsonObject samplerObject = new JsonObject();
        samplerObject.addProperty("name", "Sampler1");
        samplers.add(samplerObject);
        object.add("samplers", samplers);
        return convertToByteArray(shaderElement);
    }

    private byte[] convertToByteArray(@NotNull JsonElement element) {
        return element.toString().getBytes(StandardCharsets.UTF_8);
    }
}
