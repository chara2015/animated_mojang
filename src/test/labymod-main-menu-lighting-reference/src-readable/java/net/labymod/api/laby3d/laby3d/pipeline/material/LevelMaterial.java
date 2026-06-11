package net.labymod.api.laby3d.pipeline.material;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/LevelMaterial.class */
public final class LevelMaterial implements Material {
    private static final int OVERLAY_TEXTURE_SLOT = 1;
    private static final int LIGHTMAP_TEXTURE_SLOT = 2;
    private static final TextureMaterial[] EMPTY_TEXTURES = new TextureMaterial[0];
    private final RenderState renderState;
    private final Int2ObjectMap<TextureBinding> textures;
    private final boolean useLightmap;
    private final boolean useOverlay;

    private LevelMaterial(RenderState renderState, Int2ObjectMap<TextureBinding> textures, boolean useLightmap, boolean useOverlay) {
        this.renderState = renderState;
        this.textures = textures;
        this.useLightmap = useLightmap;
        this.useOverlay = useOverlay;
    }

    @NotNull
    public static Builder builder(@NotNull RenderState renderState) {
        return new Builder(renderState);
    }

    @Override // net.labymod.api.laby3d.pipeline.material.Material
    @NotNull
    public RenderState renderState() {
        return this.renderState;
    }

    @Override // net.labymod.api.laby3d.pipeline.material.Material
    @NotNull
    public TextureMaterial[] textureMaterials() {
        TextureMaterial textureMaterial;
        if (this.textures.isEmpty() && !this.useLightmap && !this.useOverlay) {
            return EMPTY_TEXTURES;
        }
        int size = computeEffectiveTextureSize();
        TextureMaterial[] textureMaterials = new TextureMaterial[size];
        Laby3D laby3D = Laby.references().laby3D();
        if (this.useOverlay) {
            textureMaterials[1] = new TextureMaterial(laby3D.overlayTextureView());
        }
        if (this.useLightmap) {
            textureMaterials[2] = new TextureMaterial(laby3D.lightTextureView());
        }
        TextureRepository textureRepository = Laby.references().textureRepository();
        for (int index = 0; index < textureMaterials.length; index++) {
            TextureBinding textureBinding = (TextureBinding) this.textures.get(index);
            if (textureBinding != null) {
                Texture texture = textureRepository.getOrCreateTexture(textureBinding.location());
                int i = index;
                if (texture == null) {
                    textureMaterial = null;
                } else {
                    textureMaterial = new TextureMaterial(texture.deviceTextureView());
                }
                textureMaterials[i] = textureMaterial;
            }
        }
        return textureMaterials;
    }

    @NotNull
    public Int2ObjectMap<TextureBinding> getTextures() {
        return this.textures;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        LevelMaterial that = (LevelMaterial) object;
        return this.useLightmap == that.useLightmap && this.useOverlay == that.useOverlay && Objects.equals(this.renderState, that.renderState) && Objects.equals(this.textures, that.textures);
    }

    public int hashCode() {
        int result = Objects.hashCode(this.renderState);
        return (31 * ((31 * ((31 * result) + Objects.hashCode(this.textures))) + Boolean.hashCode(this.useLightmap))) + Boolean.hashCode(this.useOverlay);
    }

    public boolean isUseLightmap() {
        return this.useLightmap;
    }

    public boolean isUseOverlay() {
        return this.useOverlay;
    }

    private int computeEffectiveTextureSize() {
        int size = this.textures.size();
        if (this.useOverlay) {
            size = Math.max(size, 2);
        }
        if (this.useLightmap) {
            size = Math.max(size, 3);
        }
        return size;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/LevelMaterial$Builder.class */
    public static class Builder {
        private final RenderState renderState;
        private boolean useLightmap = false;
        private boolean useOverlay = false;
        private final Int2ObjectMap<TextureBinding> textures = new Int2ObjectOpenHashMap();

        public Builder(@NotNull RenderState renderState) {
            this.renderState = renderState;
        }

        @NotNull
        public Builder setTexture(int index, @Nullable ResourceLocation location) {
            if (location != null) {
                this.textures.put(index, new TextureBinding(location));
            }
            return this;
        }

        @NotNull
        public Builder useOverlay() {
            this.useOverlay = true;
            return this;
        }

        @NotNull
        public Builder useLightmap() {
            this.useLightmap = true;
            return this;
        }

        @NotNull
        public Material build() {
            return new LevelMaterial(this.renderState, this.textures, this.useLightmap, this.useOverlay);
        }
    }
}
