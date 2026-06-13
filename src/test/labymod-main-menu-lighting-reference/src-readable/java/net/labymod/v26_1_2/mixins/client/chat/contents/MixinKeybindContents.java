package net.labymod.v26_1_2.mixins.client.chat.contents;

import net.labymod.v26_1_2.client.network.chat.contents.KeybindContentsAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.KeybindContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/chat/contents/MixinKeybindContents.class */
@Mixin({KeybindContents.class})
public abstract class MixinKeybindContents implements KeybindContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private String name;

    @Shadow
    protected abstract Component getNestedComponent();

    @Override // net.labymod.v26_1_2.client.network.chat.contents.KeybindContentsAccessor
    public void setKeybind(String keybind) {
        this.name = keybind;
    }

    @Override // net.labymod.v26_1_2.client.network.chat.contents.KeybindContentsAccessor
    public net.labymod.api.client.component.Component resolveKeybind() {
        return getNestedComponent().getLabyComponent();
    }
}
