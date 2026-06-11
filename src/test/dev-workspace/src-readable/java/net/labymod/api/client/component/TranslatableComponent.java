package net.labymod.api.client.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/TranslatableComponent.class */
public interface TranslatableComponent extends BaseComponent<TranslatableComponent> {
    String getKey();

    List<Component> getArguments();

    TranslatableComponent argument(Component component);

    TranslatableComponent arguments(Component... componentArr);

    TranslatableComponent arguments(Collection<Component> collection);

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    TranslatableComponent plainCopy();

    static Builder builder() {
        return new Builder();
    }

    @Nullable
    default String getFallback() {
        return null;
    }

    @Deprecated
    default String key() {
        return getKey();
    }

    @Deprecated
    default List<Component> args() {
        return getArguments();
    }

    @Deprecated
    default TranslatableComponent args(Component... args) {
        return arguments(args);
    }

    @Deprecated
    default TranslatableComponent args(List<Component> args) {
        return arguments(args);
    }

    @Override // net.labymod.api.client.component.builder.Buildable
    /* JADX INFO: renamed from: toBuilder */
    default Component.Builder<?, ?> toBuilder2() {
        return new Builder(this);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/TranslatableComponent$Builder.class */
    public static class Builder extends Component.Builder<TranslatableComponent, Builder> {
        protected String key;
        protected List<Component> arguments;

        protected Builder() {
            this.key = null;
            this.arguments = null;
        }

        protected Builder(TranslatableComponent component) {
            super(component);
            this.key = null;
            this.arguments = null;
            this.key = component.getKey();
            List<Component> arguments = component.getArguments();
            if (!arguments.isEmpty()) {
                this.arguments = new ArrayList(arguments);
            }
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder argument(Component argument) {
            if (this.arguments == null) {
                this.arguments = new ArrayList();
            }
            this.arguments.add(argument);
            return this;
        }

        public Builder arguments(Component... arguments) {
            for (Component argument : arguments) {
                argument(argument);
            }
            return this;
        }

        public Builder arguments(Collection<Component> arguments) {
            for (Component argument : arguments) {
                argument(argument);
            }
            return this;
        }

        @Deprecated
        public Builder args(Component... args) {
            return arguments(args);
        }

        @Deprecated
        public Builder args(Collection<Component> args) {
            return arguments(args);
        }

        @Override // net.labymod.api.client.component.builder.StyleableBuilder
        public TranslatableComponent build() {
            Objects.requireNonNull(this.key, "Key cannot be null");
            return ComponentService.translatableComponent(this.key, isEmpty() ? null : buildStyle(), this.children, this.arguments);
        }
    }
}
