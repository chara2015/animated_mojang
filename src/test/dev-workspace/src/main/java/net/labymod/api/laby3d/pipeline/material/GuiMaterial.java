package net.labymod.api.laby3d.pipeline.material;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.Arrays;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.states.GuiTextureSet;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.util.logging.Logging;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/GuiMaterial.class */
public final class GuiMaterial implements Material {
    private static final Reference2ObjectMap<RenderState, Material> CACHE = new Reference2ObjectOpenHashMap();
    private final RenderState renderState;
    private final TextureMaterial[] textures;

    private GuiMaterial(RenderState renderState, TextureMaterial[] textures) {
        this.renderState = renderState;
        this.textures = textures;
    }

    public static Builder builder(RenderState renderState) {
        return new Builder(renderState);
    }

    public static Material textured(RenderState renderState, ResourceLocation location) {
        return builder(renderState).setTexture(0, location).build();
    }

    public static Material textured(RenderState renderState, DeviceTextureView textureView) {
        return builder(renderState).setTexture(0, textureView).build();
    }

    public static Material untextured(RenderState renderState) {
        Material guiMaterial = (Material) CACHE.get(renderState);
        if (guiMaterial != null) {
            return guiMaterial;
        }
        Material newGuiMaterial = builder(renderState).build();
        CACHE.put(renderState, newGuiMaterial);
        return newGuiMaterial;
    }

    @Deprecated(forRemoval = true, since = "4.3.37")
    public static Material fromLegacy(RenderState renderState, GuiTextureSet textureSet) {
        Builder builder = builder(renderState);
        DeviceTextureView[] textures = textureSet.textures();
        for (int index = 0; index < textures.length; index++) {
            builder.setTexture(index, textures[index]);
        }
        return builder.build();
    }

    @Override // net.labymod.api.laby3d.pipeline.material.Material
    public RenderState renderState() {
        return this.renderState;
    }

    @Override // net.labymod.api.laby3d.pipeline.material.Material
    public TextureMaterial[] textureMaterials() {
        return this.textures;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        GuiMaterial that = (GuiMaterial) object;
        return Objects.equals(this.renderState, that.renderState) && Arrays.equals(this.textures, that.textures);
    }

    public int hashCode() {
        int result = Objects.hashCode(this.renderState);
        return (31 * result) + Arrays.hashCode(this.textures);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/GuiMaterial$Builder.class */
    public static class Builder {
        private static final Logging LOGGER = Logging.getLogger();
        private static final TextureMaterial[] EMPTY_TEXTURES = new TextureMaterial[0];
        private final RenderState renderState;
        private int highestTextureIndex = 0;
        private final Int2ObjectMap<Object> textures = new Int2ObjectOpenHashMap();
        private final TextureRepository textureRepository = Laby.references().textureRepository();

        public Builder(RenderState renderState) {
            this.renderState = renderState;
        }

        public Builder setTexture(int index, ResourceLocation location) {
            return assignTexture(index, location);
        }

        public Builder setTexture(int index, Texture texture) {
            return assignTexture(index, texture);
        }

        public Builder setTexture(int index, DeviceTextureView textureView) {
            return assignTexture(index, textureView);
        }

        public Material build() {
            TextureMaterial[] textureMaterialArr;
            if (this.textures.isEmpty()) {
                textureMaterialArr = EMPTY_TEXTURES;
            } else {
                textureMaterialArr = new TextureMaterial[this.highestTextureIndex + 1];
            }
            TextureMaterial[] textureMaterials = textureMaterialArr;
            ObjectIterator it = this.textures.int2ObjectEntrySet().iterator();
            while (it.hasNext()) {
                Int2ObjectMap.Entry<Object> entry = (Int2ObjectMap.Entry) it.next();
                textureMaterials[entry.getIntKey()] = new TextureMaterial(resolveTextureView(entry.getValue()));
            }
            return new GuiMaterial(this.renderState, textureMaterials);
        }

        private Builder assignTexture(int index, Object obj) {
            this.textures.put(index, obj);
            this.highestTextureIndex = Math.max(this.highestTextureIndex, index);
            return this;
        }

        private DeviceTextureView resolveTextureView(Object object) {
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), DeviceTextureView.class, Texture.class, ResourceLocation.class).dynamicInvoker().invoke(object, 0) /* invoke-custom */) {
                case -1:
                default:
                    LOGGER.warn("Failed to resolve texture view for object: " + String.valueOf(object), new Object[0]);
                    return null;
                case 0:
                    DeviceTextureView textureView = (DeviceTextureView) object;
                    return textureView;
                case 1:
                    return ((Texture) object).deviceTextureView();
                case 2:
                    ResourceLocation location = (ResourceLocation) object;
                    Texture texture = this.textureRepository.getOrCreateTexture(location);
                    if (texture == null) {
                        return null;
                    }
                    return texture.deviceTextureView();
            }
        }
    }
}
