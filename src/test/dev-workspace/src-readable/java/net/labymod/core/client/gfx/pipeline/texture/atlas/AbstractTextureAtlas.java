package net.labymod.core.client.gfx.pipeline.texture.atlas;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/AbstractTextureAtlas.class */
public class AbstractTextureAtlas implements TextureAtlas {
    protected final Map<ResourceLocation, TextureSprite> sprites = new HashMap();
    private final ResourceLocation resourceLocation;
    private final int width;
    private final int height;

    public AbstractTextureAtlas(ResourceLocation resourceLocation, int width, int height) {
        this.resourceLocation = resourceLocation;
        this.width = width;
        this.height = height;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public void register(ResourceLocation id, TextureSprite sprite) {
        this.sprites.put(id, sprite);
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public TextureSprite findSprite(ResourceLocation location) {
        return this.sprites.get(location);
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public ResourceLocation resource() {
        return this.resourceLocation;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasWidth() {
        return this.width;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasHeight() {
        return this.height;
    }

    protected void register16(ResourceLocation id, int x, int y) {
        registerTile(id, x, y, 16);
    }

    protected void register8(ResourceLocation id, int x, int y) {
        registerTile(id, x, y, 8);
    }

    protected void registerTile(ResourceLocation id, int x, int y, int size) {
        registerTile(id, x, y, size, size);
    }

    protected void registerTile(ResourceLocation id, int x, int y, int width, int height) {
        register(id, x * width, y * height, width, height, (width1, height1) -> {
            return new TileScaling((int) width1, (int) height1);
        });
    }

    protected void register(ResourceLocation id, int x, int y, int width, int height, SpriteScaling.Factory spriteScalingFactory) {
        TextureUV uv = createUV(x, y, width, height);
        register(id, new DefaultTextureSprite(uv, width, height, spriteScalingFactory));
    }

    public void registerAnimatedHorizontal(ResourceLocation id, int x, int y, int width, int height, int frames) {
        registerAnimated(id, x, y, width, height, frames, true);
    }

    public void registerAnimatedVertical(ResourceLocation id, int x, int y, int width, int height, int frames) {
        registerAnimated(id, x, y, width, height, frames, false);
    }

    public void registerAnimated(ResourceLocation id, int x, int y, int width, int height, int frames, boolean horizontal) {
        Int2ObjectArrayMap int2ObjectArrayMap = new Int2ObjectArrayMap(frames);
        int index = 0;
        while (index < frames) {
            if (horizontal) {
                x += index == 0 ? 0 : width;
            } else {
                y += index == 0 ? 0 : height;
            }
            TextureUV uv = createUV(x, y, width, height);
            int2ObjectArrayMap.put(index, new DefaultTextureSprite(uv, width, height, (w, h) -> {
                return new TileScaling((int) w, (int) h);
            }));
            index++;
        }
        register(id, new DefaultAnimatedTextureSprite(int2ObjectArrayMap));
    }

    protected ResourceLocation createMinecraft(String path) {
        return ResourceLocation.create(Namespaces.MINECRAFT, path);
    }

    protected ResourceLocation createLabyMod(String path) {
        return ResourceLocation.create("labymod", path);
    }

    private TextureUV createUV(int x, int y, int width, int height) {
        float minU = x / this.width;
        float minV = y / this.height;
        float maxU = (x + width) / this.width;
        float maxV = (y + height) / this.height;
        return new DefaultTextureUV(minU, minV, maxU, maxV);
    }
}
