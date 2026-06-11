package net.labymod.api.client.component;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.object.ObjectSprite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/ObjectComponent.class */
public interface ObjectComponent extends BaseComponent<ObjectComponent> {
    public static final String PLACEHOLDER = Character.toString(65532);

    ObjectSprite getObjectSprite();

    static Builder builder() {
        return new Builder();
    }

    @Override // net.labymod.api.client.component.builder.Buildable
    /* JADX INFO: renamed from: toBuilder */
    default Component.Builder<?, ?> toBuilder2() {
        return new Builder(this);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/ObjectComponent$Builder.class */
    public static class Builder extends Component.Builder<ObjectComponent, Builder> {
        private ObjectSprite objectInfo;

        protected Builder() {
        }

        protected Builder(ObjectComponent component) {
            super(component);
            this.objectInfo = component.getObjectSprite();
        }

        public Builder objectInfo(ObjectSprite objectInfo) {
            this.objectInfo = objectInfo;
            return this;
        }

        @Override // net.labymod.api.client.component.builder.StyleableBuilder
        public ObjectComponent build() {
            Objects.requireNonNull(this.objectInfo, "Object info cannot be null!");
            return ComponentService.objectComponent(this.objectInfo, isEmpty() ? null : buildStyle(), this.children);
        }
    }
}
