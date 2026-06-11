package net.labymod.v1_21_1.client.network.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.v1_21_1.client.network.chat.contents.KeybindContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/network/chat/VersionedKeybindComponent.class */
public class VersionedKeybindComponent extends VersionedBaseComponent<KeybindComponent, xa> implements KeybindComponent {
    public VersionedKeybindComponent(xn holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.KeybindComponent
    public String getKeybind() {
        yd ydVar = (xa) getContents();
        if (ydVar instanceof yd) {
            yd keybind = ydVar;
            return keybind.b();
        }
        return ydVar.toString();
    }

    @Override // net.labymod.api.client.component.KeybindComponent
    public KeybindComponent keybind(String keybind) {
        KeybindContentsAccessor contents = getContents();
        if (contents instanceof KeybindContentsAccessor) {
            KeybindContentsAccessor keybindContents = contents;
            keybindContents.setKeybind(keybind);
            return this;
        }
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public KeybindComponent plainCopy() {
        return ComponentService.keybindComponent(getKeybind());
    }

    @Override // net.labymod.api.client.component.KeybindComponent
    public Component resolveKeybind() {
        KeybindContentsAccessor contents = getContents();
        if (contents instanceof KeybindContentsAccessor) {
            KeybindContentsAccessor keybindContents = contents;
            return keybindContents.resolveKeybind();
        }
        return Component.text(getKeybind());
    }
}
