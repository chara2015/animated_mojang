package net.labymod.v1_21_5.client.network.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.v1_21_5.client.network.chat.contents.KeybindContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/network/chat/VersionedKeybindComponent.class */
public class VersionedKeybindComponent extends VersionedBaseComponent<KeybindComponent, xh> implements KeybindComponent {
    public VersionedKeybindComponent(xu holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.KeybindComponent
    public String getKeybind() {
        yk ykVar = (xh) getContents();
        if (ykVar instanceof yk) {
            yk keybind = ykVar;
            return keybind.b();
        }
        return ykVar.toString();
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
