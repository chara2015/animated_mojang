package net.labymod.api.client.component;

import java.util.Objects;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/TextComponent.class */
public interface TextComponent extends BaseComponent<TextComponent> {
    String getText();

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    TextComponent plainCopy();

    TextComponent text(String str);

    static Builder builder() {
        return new Builder();
    }

    @Override // net.labymod.api.client.component.builder.Buildable
    /* JADX INFO: renamed from: toBuilder, reason: merged with bridge method [inline-methods] */
    default Component.Builder<?, ?> toBuilder2() {
        return new Builder(this);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/TextComponent$Builder.class */
    public static class Builder extends Component.Builder<TextComponent, Builder> {
        protected String text;

        protected Builder() {
            this.text = "";
        }

        protected Builder(TextComponent component) {
            super(component);
            this.text = "";
            this.text = component.getText();
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        @Deprecated
        public Builder content(String content) {
            return text(content);
        }

        @Override // net.labymod.api.client.component.builder.StyleableBuilder
        public TextComponent build() {
            Objects.requireNonNull(this.text, "Text cannot be null!");
            return ComponentService.textComponent(this.text, isEmpty() ? null : buildStyle(), this.children);
        }
    }
}
