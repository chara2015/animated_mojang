package net.labymod.v1_21_11.client.network.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.v1_21_11.client.network.chat.contents.KeybindContentsAccessor;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.KeybindContents;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/network/chat/VersionedKeybindComponent.class */
public class VersionedKeybindComponent extends VersionedBaseComponent<KeybindComponent, ComponentContents> implements KeybindComponent {
    public VersionedKeybindComponent(MutableComponent holder) {
        super(holder);
    }

    public String getKeybind() {
        KeybindContents keybindContents = (ComponentContents) getContents();
        if (keybindContents instanceof KeybindContents) {
            KeybindContents keybind = keybindContents;
            return keybind.getName();
        }
        return keybindContents.toString();
    }

    public KeybindComponent keybind(String keybind) {
        KeybindContentsAccessor contents = getContents();
        if (contents instanceof KeybindContentsAccessor) {
            KeybindContentsAccessor keybindContents = contents;
            keybindContents.setKeybind(keybind);
            return this;
        }
        return this;
    }

    /* JADX INFO: renamed from: plainCopy, reason: merged with bridge method [inline-methods] */
    public KeybindComponent m19plainCopy() {
        return ComponentService.keybindComponent(getKeybind());
    }

    public Component resolveKeybind() {
        KeybindContentsAccessor contents = getContents();
        if (contents instanceof KeybindContentsAccessor) {
            KeybindContentsAccessor keybindContents = contents;
            return keybindContents.resolveKeybind();
        }
        return Component.text(getKeybind());
    }
}
