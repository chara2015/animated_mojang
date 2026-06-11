package net.labymod.v1_8_9.mixins.client.component;

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
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import net.labymod.api.event.client.component.ComponentSerializeEvent;
import net.labymod.core.event.client.component.ComponentSerializeEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinIChatComponentSerializer.class */
@Mixin({a.class})
public abstract class MixinIChatComponentSerializer implements Serializer {
    @Shadow
    protected abstract void a(ez ezVar, JsonObject jsonObject, JsonSerializationContext jsonSerializationContext);

    @Overwrite
    public JsonElement a(eu value, Type type, JsonSerializationContext context) {
        ComponentSerializeEvent event = ComponentSerializeEventCaller.call((Component) value);
        Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            value = (eu) unwrappedComponent;
        }
        if (!event.wasSerialized() && (value instanceof fa) && value.b().g() && value.a().isEmpty()) {
            return new JsonPrimitive(((fa) value).g());
        }
        JsonObject json = event.getJson() != null ? event.getJson() : new JsonObject();
        if (!value.b().g()) {
            a(value.b(), json, context);
        }
        if (!value.a().isEmpty()) {
            JsonArray extra = new JsonArray();
            for (eu sibling : value.a()) {
                extra.add(a(sibling, sibling.getClass(), context));
            }
            json.add("extra", extra);
        }
        if (!event.wasSerialized()) {
            if (value instanceof fa) {
                json.addProperty("text", ((fa) value).g());
            } else if (value instanceof fb) {
                fb translateComponent = (fb) value;
                json.addProperty("translate", translateComponent.i());
                if (translateComponent.j() != null && translateComponent.j().length > 0) {
                    JsonArray args = new JsonArray();
                    for (Object arg : translateComponent.j()) {
                        if (arg instanceof eu) {
                            args.add(a((eu) arg, arg.getClass(), context));
                        } else {
                            args.add(new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", args);
                }
            } else if (value instanceof ex) {
                ex scoreComponent = (ex) value;
                JsonObject score = new JsonObject();
                score.addProperty("name", scoreComponent.g());
                score.addProperty("objective", scoreComponent.h());
                score.addProperty("value", scoreComponent.e());
                json.add("score", score);
            } else if (value instanceof ey) {
                ey selectorComponent = (ey) value;
                json.addProperty("selector", selectorComponent.g());
            } else {
                throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
            }
        }
        return json;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    @Overwrite
    public eu a(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        eu component;
        if (json.isJsonPrimitive()) {
            return new fa(json.getAsString());
        }
        if (!json.isJsonObject()) {
            if (json.isJsonArray()) {
                eu component2 = null;
                for (JsonElement jsonElement : json.getAsJsonArray()) {
                    eu element = a(jsonElement, jsonElement.getClass(), context);
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
            component = (eu) event.getComponent();
        } else if (object.has("text")) {
            component = new fa(object.get("text").getAsString());
        } else if (object.has("translate")) {
            String translationKey = object.get("translate").getAsString();
            if (object.has("with")) {
                JsonArray jsonArgs = object.getAsJsonArray("with");
                Object[] args = new Object[jsonArgs.size()];
                for (int i = 0; i < args.length; i++) {
                    args[i] = a(jsonArgs.get(i), type, context);
                    if (args[i] instanceof fa) {
                        fa textArg = (fa) args[i];
                        if (textArg.b().g() && textArg.a().isEmpty()) {
                            args[i] = textArg.g();
                        }
                    }
                }
                component = new fb(translationKey, args);
            } else {
                component = new fb(translationKey, new Object[0]);
            }
        } else if (object.has("score")) {
            JsonObject score = object.getAsJsonObject("score");
            if (!score.has("name") || !score.has("objective")) {
                throw new JsonParseException("A score component needs a least a name and an objective");
            }
            component = new ex(ni.h(score, "name"), ni.h(score, "objective"));
            if (score.has("value")) {
                ((ex) component).b(ni.h(score, "value"));
            }
        } else if (object.has("selector")) {
            component = new ey(ni.h(object, "selector"));
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
        component.a((ez) context.deserialize(json, ez.class));
        return component;
    }
}
