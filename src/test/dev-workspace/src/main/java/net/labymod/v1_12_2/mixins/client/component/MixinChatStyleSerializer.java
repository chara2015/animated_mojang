package net.labymod.v1_12_2.mixins.client.component;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.v1_12_2.client.component.VersionedNamedTextColors;
import net.labymod.v1_12_2.client.component.VersionedTextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/component/MixinChatStyleSerializer.class */
@Mixin({a.class})
public class MixinChatStyleSerializer implements Serializer {
    @Inject(method = {"deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/text/Style;"}, at = {@At("RETURN")})
    public void test(JsonElement element, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_, CallbackInfoReturnable<hn> cir) {
        hn style = (hn) cir.getReturnValue();
        if (style == null || !element.isJsonObject()) {
            return;
        }
        JsonObject jsonObject = element.getAsJsonObject();
        if (!jsonObject.has("color")) {
            return;
        }
        JsonElement jsonElement = jsonObject.get("color");
        if (!jsonElement.isJsonPrimitive()) {
            return;
        }
        JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
        if (!jsonPrimitive.isString()) {
            return;
        }
        String name = jsonPrimitive.getAsString();
        VersionedTextColor textColor = VersionedNamedTextColors.byName(name);
        style.a(textColor == null ? null : textColor.getFormatting());
    }
}
