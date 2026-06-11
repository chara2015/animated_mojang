package net.labymod.v1_12_2.client.component;

import com.google.gson.JsonObject;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import net.labymod.api.event.client.component.ComponentSerializeEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/component/VersionedKeybindComponentSerializer.class */
public class VersionedKeybindComponentSerializer {
    @Subscribe
    public void serializeKeybindComponent(ComponentSerializeEvent event) {
        if (event.wasSerialized()) {
            return;
        }
        Component component = event.component();
        if (!(component instanceof KeybindComponent)) {
            return;
        }
        KeybindComponent keybindComponent = (KeybindComponent) component;
        String keybind = keybindComponent.getKeybind();
        event.serialize(json -> {
            json.addProperty("keybind", keybind);
        });
    }

    @Subscribe
    public void deserializeKeybindComponent(ComponentDeserializeEvent event) {
        JsonObject json = event.json();
        if (!json.has("keybind")) {
            return;
        }
        String keybind = json.get("keybind").getAsString();
        event.setComponent(Component.keybind(keybind));
    }
}
