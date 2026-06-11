package net.labymod.v1_21_1.mixins.client.chat.contents;

import net.labymod.api.client.component.Component;
import net.labymod.v1_21_1.client.network.chat.contents.KeybindContentsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/chat/contents/MixinKeybindContents.class */
@Mixin({yd.class})
public abstract class MixinKeybindContents implements KeybindContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private String c;

    @Shadow
    protected abstract wz c();

    @Override // net.labymod.v1_21_1.client.network.chat.contents.KeybindContentsAccessor
    public void setKeybind(String keybind) {
        this.c = keybind;
    }

    @Override // net.labymod.v1_21_1.client.network.chat.contents.KeybindContentsAccessor
    public Component resolveKeybind() {
        return c().getLabyComponent();
    }
}
