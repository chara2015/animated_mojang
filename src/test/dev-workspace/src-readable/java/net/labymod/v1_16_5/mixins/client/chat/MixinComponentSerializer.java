package net.labymod.v1_16_5.mixins.client.chat;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import net.labymod.api.event.client.component.ComponentSerializeEvent;
import net.labymod.core.event.client.component.ComponentSerializeEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/chat/MixinComponentSerializer.class */
@Mixin({a.class})
public abstract class MixinComponentSerializer implements Serializer {
    @Shadow
    protected abstract void a(ob obVar, JsonObject jsonObject, JsonSerializationContext jsonSerializationContext);

    @Overwrite
    public JsonElement a(nr value, Type type, JsonSerializationContext context) {
        ComponentSerializeEvent event = ComponentSerializeEventCaller.call((Component) value);
        Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            value = (nr) unwrappedComponent;
        }
        JsonObject json = event.getJson() != null ? event.getJson() : new JsonObject();
        if (!value.c().g()) {
            a(value.c(), json, context);
        }
        if (!value.b().isEmpty()) {
            JsonArray extra = new JsonArray();
            for (nr sibling : value.b()) {
                extra.add(a(sibling, sibling.getClass(), context));
            }
            json.add("extra", extra);
        }
        if (!event.wasSerialized()) {
            if (value instanceof TextComponent) {
                json.addProperty("text", ((TextComponent) value).getText());
            } else if (value instanceof of) {
                of translateComponent = (of) value;
                json.addProperty("translate", translateComponent.i());
                if (translateComponent.j().length > 0) {
                    JsonArray args = new JsonArray();
                    for (Object arg : translateComponent.j()) {
                        if (arg instanceof nr) {
                            args.add(a((nr) arg, arg.getClass(), context));
                        } else {
                            args.add(new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", args);
                }
            } else if (value instanceof nz) {
                nz scoreComponent = (nz) value;
                JsonObject score = new JsonObject();
                score.addProperty("name", scoreComponent.h());
                score.addProperty("objective", scoreComponent.j());
                json.add("score", score);
            } else if (value instanceof oa) {
                oa selectorComponent = (oa) value;
                json.addProperty("selector", selectorComponent.h());
            } else if (value instanceof nw) {
                KeybindComponent keybindComponent = (KeybindComponent) value;
                json.addProperty("keybind", keybindComponent.getKeybind());
            } else if (value instanceof ny) {
                ny nbtComponent = (ny) value;
                json.addProperty("nbt", nbtComponent.h());
                json.addProperty("interpret", Boolean.valueOf(nbtComponent.i()));
                if (value instanceof a) {
                    a blockNbtComponent = (a) value;
                    json.addProperty("block", blockNbtComponent.j());
                } else if (value instanceof b) {
                    b entityNbtComponent = (b) value;
                    json.addProperty("entity", entityNbtComponent.j());
                } else if (value instanceof c) {
                    c storageNbtComponent = (c) value;
                    json.addProperty("storage", storageNbtComponent.j().toString());
                } else {
                    throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                }
            } else {
                throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
            }
        }
        return json;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    @Overwrite
    public nx a(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        nx component;
        if (json.isJsonPrimitive()) {
            return new oe(json.getAsString());
        }
        if (!json.isJsonObject()) {
            if (json.isJsonArray()) {
                nx component2 = null;
                for (JsonElement jsonElement : json.getAsJsonArray()) {
                    nx element = a(jsonElement, jsonElement.getClass(), context);
                    if (component2 == null) {
                        component2 = element;
                    } else {
                        component2.a(element);
                    }
                }
                return component2;
            }
            throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
        }
        JsonObject object = json.getAsJsonObject();
        ComponentDeserializeEvent event = (ComponentDeserializeEvent) Laby.fireEvent(new ComponentDeserializeEvent(object));
        if (event.getComponent() != null) {
            component = (nx) event.getComponent();
        } else if (object.has("text")) {
            component = new oe(afd.h(object, "text"));
        } else if (object.has("translate")) {
            String translationKey = afd.h(object, "translate");
            if (object.has("with")) {
                JsonArray jsonArgs = afd.u(object, "with");
                Object[] args = new Object[jsonArgs.size()];
                for (int i = 0; i < args.length; i++) {
                    args[i] = a(jsonArgs.get(i), type, context);
                    if (args[i] instanceof oe) {
                        oe textArg = (oe) args[i];
                        if (textArg.c().g() && textArg.b().isEmpty()) {
                            args[i] = textArg.h();
                        }
                    }
                }
                component = new of(translationKey, args);
            } else {
                component = new of(translationKey);
            }
        } else if (object.has("score")) {
            JsonObject score = afd.t(object, "score");
            if (!score.has("name") || !score.has("objective")) {
                throw new JsonParseException("A score component needs a least a name and an objective");
            }
            component = new nz(afd.h(score, "name"), afd.h(score, "objective"));
        } else if (object.has("selector")) {
            component = new oa(afd.h(object, "selector"));
        } else if (object.has("keybind")) {
            component = new nw(afd.h(object, "keybind"));
        } else if (object.has("nbt")) {
            String nbtPath = afd.h(object, "nbt");
            boolean interpreting = afd.a(object, "interpret", false);
            if (object.has("block")) {
                component = new a(nbtPath, interpreting, afd.h(object, "block"));
            } else if (object.has("entity")) {
                component = new b(nbtPath, interpreting, afd.h(object, "entity"));
            } else if (object.has("storage")) {
                component = new c(nbtPath, interpreting, new vk(afd.h(object, "storage")));
            } else {
                throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
            }
        } else {
            throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
        }
        if (object.has("extra")) {
            JsonArray extra = object.getAsJsonArray("extra");
            if (extra.size() <= 0) {
                throw new JsonParseException("Unexpected empty array of components");
            }
            Iterator it = extra.iterator();
            while (it.hasNext()) {
                component.a(a((JsonElement) it.next(), type, context));
            }
        }
        component.a((ob) context.deserialize(json, ob.class));
        return component;
    }
}
