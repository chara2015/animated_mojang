package net.labymod.v1_12_2.mixins.client.component;

import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.v1_12_2.client.component.VersionedKeybindComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/component/MixinKeybindComponent.class */
@Mixin({VersionedKeybindComponent.class})
public abstract class MixinKeybindComponent extends MixinChatComponentStyle<KeybindComponent> implements KeybindComponent {
    private String resolvedText;
    private Supplier<Component> resolvedKeybind;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.component.KeybindComponent
    @NotNull
    public String getKeybind() {
        return ((TextComponent) this).getText();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.component.KeybindComponent
    @NotNull
    public KeybindComponent keybind(@NotNull String keybind) {
        ((TextComponent) this).text(keybind);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public KeybindComponent plainCopy() {
        return (KeybindComponent) new VersionedKeybindComponent(getKeybind());
    }

    @Override // net.labymod.api.client.component.KeybindComponent
    public Component resolveKeybind() {
        String keybind = getKeybind();
        if (!keybind.equals(this.resolvedText) || this.resolvedKeybind == null) {
            Function<String, Supplier<Component>> keyResolver = VersionedKeybindComponent.getKeyResolver();
            this.resolvedText = keybind;
            this.resolvedKeybind = keyResolver.apply(keybind);
        }
        return this.resolvedKeybind.get();
    }
}
