package net.labymod.v26_1.client.network.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.v26_1.client.network.chat.contents.KeybindContentsAccessor;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.KeybindContents;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/network/chat/VersionedKeybindComponent.class */
public class VersionedKeybindComponent extends VersionedBaseComponent<KeybindComponent, ComponentContents> implements KeybindComponent {
    public VersionedKeybindComponent(MutableComponent holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.KeybindComponent
    public String getKeybind() {
        KeybindContents keybindContents = (ComponentContents) getContents();
        if (keybindContents instanceof KeybindContents) {
            KeybindContents keybind = keybindContents;
            return keybind.getName();
        }
        return keybindContents.toString();
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
