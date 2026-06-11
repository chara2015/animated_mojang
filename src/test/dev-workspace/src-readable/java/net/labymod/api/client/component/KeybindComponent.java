package net.labymod.api.client.component;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.options.MinecraftInputMapping;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/KeybindComponent.class */
public interface KeybindComponent extends BaseComponent<KeybindComponent> {
    String getKeybind();

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    KeybindComponent plainCopy();

    KeybindComponent keybind(String str);

    Component resolveKeybind();

    static Builder builder() {
        return new Builder();
    }

    @Nullable
    default MinecraftInputMapping getInputMapping() {
        return Laby.labyAPI().minecraft().options().getInputMapping(getKeybind());
    }

    default KeybindComponent keybind(MinecraftInputMapping keybind) {
        return keybind(keybind.getName());
    }

    @Override // net.labymod.api.client.component.builder.Buildable
    /* JADX INFO: renamed from: toBuilder */
    default Component.Builder<?, ?> toBuilder2() {
        return new Builder(this);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/KeybindComponent$Builder.class */
    public static class Builder extends Component.Builder<KeybindComponent, Builder> {
        protected String keybind;

        protected Builder() {
            this.keybind = "";
        }

        protected Builder(KeybindComponent component) {
            super(component);
            this.keybind = "";
            this.keybind = component.getKeybind();
        }

        public Builder keybind(String keybind) {
            this.keybind = keybind;
            return this;
        }

        public Builder keybind(MinecraftInputMapping keybind) {
            return keybind(keybind.getName());
        }

        @Override // net.labymod.api.client.component.builder.StyleableBuilder
        public KeybindComponent build() {
            Objects.requireNonNull(this.keybind, "Keybind cannot be null!");
            return ComponentService.keybindComponent(this.keybind, isEmpty() ? null : buildStyle(), this.children);
        }
    }
}
