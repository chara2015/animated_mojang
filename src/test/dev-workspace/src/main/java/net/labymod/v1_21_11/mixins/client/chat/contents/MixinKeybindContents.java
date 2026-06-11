package net.labymod.v1_21_11.mixins.client.chat.contents;

import net.labymod.api.client.component.Component;
import net.labymod.v1_21_11.client.network.chat.contents.KeybindContentsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/chat/contents/MixinKeybindContents.class */
@Mixin({zj.class})
public abstract class MixinKeybindContents implements KeybindContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private String b;

    @Shadow
    protected abstract yh c();

    @Override // net.labymod.v1_21_11.client.network.chat.contents.KeybindContentsAccessor
    public void setKeybind(String keybind) {
        this.b = keybind;
    }

    @Override // net.labymod.v1_21_11.client.network.chat.contents.KeybindContentsAccessor
    public Component resolveKeybind() {
        return c().getLabyComponent();
    }
}
