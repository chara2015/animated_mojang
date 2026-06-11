package net.labymod.api.laby3d.textures;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Arrays;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/textures/TextureBindingSet.class */
public final class TextureBindingSet {
    public static final TextureBindingSet EMPTY = new TextureBindingSet(new DeviceTextureView[0]);
    private final DeviceTextureView[] textures;

    private TextureBindingSet(DeviceTextureView[] textures) {
        this.textures = textures;
    }

    public static Builder builder() {
        return new Builder();
    }

    public DeviceTextureView[] textures() {
        return this.textures;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        TextureBindingSet that = (TextureBindingSet) object;
        return Arrays.equals(this.textures, that.textures);
    }

    public int hashCode() {
        return Arrays.hashCode(this.textures);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/textures/TextureBindingSet$Builder.class */
    public static class Builder {
        private final Int2ObjectMap<DeviceTextureView> textures = new Int2ObjectOpenHashMap();
        private int highestIndex = 0;

        public Builder setTexture(int index, ResourceLocation location) {
            ShaderTextures.setShaderTexture(index, location);
            return setTexture(index, ShaderTextures.getShaderTexture(index));
        }

        public Builder setTexture(int index, DeviceTextureView texture) {
            this.textures.put(index, texture);
            this.highestIndex = Math.max(this.highestIndex, index);
            return this;
        }

        public TextureBindingSet build() {
            DeviceTextureView[] textures = new DeviceTextureView[this.highestIndex + 1];
            ObjectIterator it = this.textures.int2ObjectEntrySet().iterator();
            while (it.hasNext()) {
                Int2ObjectMap.Entry<DeviceTextureView> entry = (Int2ObjectMap.Entry) it.next();
                textures[entry.getIntKey()] = (DeviceTextureView) entry.getValue();
            }
            return new TextureBindingSet(textures);
        }
    }
}
