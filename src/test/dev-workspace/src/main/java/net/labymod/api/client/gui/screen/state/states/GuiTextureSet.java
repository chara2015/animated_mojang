package net.labymod.api.client.gui.screen.state.states;

import java.util.Objects;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiTextureSet.class */
@Deprecated(forRemoval = true, since = "4.3.37")
public class GuiTextureSet {
    public static final GuiTextureSet EMPTY = new GuiTextureSet(TextureBindingSet.EMPTY);
    private final TextureBindingSet bindingSet;

    private GuiTextureSet(TextureBindingSet bindingSet) {
        this.bindingSet = bindingSet;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static GuiTextureSet single(ResourceLocation texture) {
        return builder().setTexture(0, texture).build();
    }

    public static GuiTextureSet single(DeviceTextureView viewHandle) {
        return builder().setTexture(0, viewHandle).build();
    }

    public DeviceTextureView[] textures() {
        return this.bindingSet.textures();
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        GuiTextureSet that = (GuiTextureSet) object;
        return Objects.equals(this.bindingSet, that.bindingSet);
    }

    public int hashCode() {
        return Objects.hashCode(this.bindingSet);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiTextureSet$Builder.class */
    public static class Builder {
        private final TextureBindingSet.Builder builder = TextureBindingSet.builder();

        public Builder setTexture(int index, ResourceLocation texture) {
            this.builder.setTexture(index, texture);
            return this;
        }

        public Builder setTexture(int index, DeviceTextureView texture) {
            this.builder.setTexture(index, texture);
            return this;
        }

        public GuiTextureSet build() {
            return new GuiTextureSet(this.builder.build());
        }
    }
}
