package net.labymod.api.client.render;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/AtlasRenderer.class */
@Deprecated(forRemoval = true, since = "4.0.6")
public final class AtlasRenderer {
    private int color = ColorFormat.ARGB32.pack(255, 255, 255, 255);

    public void blitSprite(ResourceRenderContext context, TextureAtlas atlas, ResourceLocation location, int spriteWidth, int spriteHeight, int spriteX, int spriteY, int x, int y, int width, int height, int color) {
        TextureSprite sprite = atlas.findSprite(location);
        SpriteScaling scaling = sprite.scaling();
        if (scaling instanceof StretchScaling) {
            context.blitSprite(sprite.uv(), spriteWidth, spriteHeight, spriteX, spriteY, x, y, 0, width, height, color);
        }
    }

    public void blitSprite(ResourceRenderContext context, TextureAtlas atlas, ResourceLocation location, int x, int y, int width, int height, int color) {
        TextureSprite sprite = atlas.findSprite(location);
        blitSprite(context, sprite, x, y, width, height, color);
    }

    public void blitSprite(ResourceRenderContext context, TextureSprite sprite, int x, int y, int width, int height, int color) {
        blitSprite(context, sprite.uv(), sprite.scaling(), x, y, width, height, color);
    }

    public void blitSprite(ResourceRenderContext context, TextureUV uv, SpriteScaling scaling, int x, int y, int width, int height, int color) {
        blitSprite(context, uv, scaling, x, y, width, height, 0, color);
    }

    public void blitSprite(ResourceRenderContext context, TextureUV uv, SpriteScaling scaling, int x, int y, int width, int height, int zOffset, int color) {
        this.color = color;
        if (scaling instanceof StretchScaling) {
            context.blitSprite(x, y, width, height, zOffset, uv.getMinU(), uv.getMinV(), uv.getMaxU(), uv.getMaxV(), color);
        } else if (scaling instanceof TileScaling) {
            TileScaling tileScaling = (TileScaling) scaling;
            int tileWidth = tileScaling.width();
            int tileHeight = tileScaling.height();
            blitTiledSprite(context, uv, x, y, zOffset, width, height, 0, 0, tileWidth, tileHeight, tileWidth, tileHeight);
        } else if (scaling instanceof NineSpliceScaling) {
            NineSpliceScaling nineSpliceScaling = (NineSpliceScaling) scaling;
            blitNineSlicedSprite(context, uv, nineSpliceScaling, x, y, width, height, zOffset);
        }
        this.color = ColorFormat.ARGB32.pack(255, 255, 255, 255);
    }

    private void blitNineSlicedSprite(ResourceRenderContext context, TextureUV texture, NineSpliceScaling scaling, int x, int y, int width, int height, int zOffset) {
        NineSpliceScaling.Border border = scaling.border();
        int left = Math.min(border.left(), width / 2);
        int right = Math.min(border.right(), width / 2);
        int top = Math.min(border.top(), height / 2);
        int bottom = Math.min(border.bottom(), height / 2);
        if (width == scaling.width() && height == scaling.height()) {
            blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, x, y, zOffset, width, height);
            return;
        }
        if (height == scaling.height()) {
            applyNonStretchedHorizontal(context, texture, scaling, x, y, zOffset, width, height, left, right);
        } else if (width == scaling.width()) {
            applyNonStretchedVertical(context, texture, scaling, x, y, zOffset, width, height, top, bottom);
        } else {
            applyStretched(context, texture, scaling, x, y, zOffset, width, height, left, right, top, bottom);
        }
    }

    private void applyNonStretchedHorizontal(ResourceRenderContext context, TextureUV texture, NineSpliceScaling scaling, int x, int y, int zOffset, int width, int height, int left, int right) {
        blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, x, y, zOffset, left, height);
        blitTiledSprite(context, texture, x + left, y, zOffset, (width - right) - left, height, left, 0, (scaling.width() - right) - left, scaling.height(), scaling.width(), scaling.height());
        blitSprite(context, texture, scaling.width(), scaling.height(), scaling.width() - right, 0, (x + width) - right, y, zOffset, right, height);
    }

    private void applyNonStretchedVertical(ResourceRenderContext context, TextureUV texture, NineSpliceScaling scaling, int x, int y, int zOffset, int width, int height, int top, int bottom) {
        blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, x, y, zOffset, width, top);
        blitTiledSprite(context, texture, x, y + top, zOffset, width, (height - bottom) - top, 0, top, scaling.width(), (scaling.height() - bottom) - top, scaling.width(), scaling.height());
        blitSprite(context, texture, scaling.width(), scaling.height(), 0, scaling.height() - bottom, x, (y + height) - bottom, zOffset, width, bottom);
    }

    private void applyStretched(ResourceRenderContext context, TextureUV texture, NineSpliceScaling scaling, int xPos, int yPos, int zOffset, int width, int height, int left, int right, int top, int bottom) {
        blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, xPos, yPos, zOffset, left, top);
        blitTiledSprite(context, texture, xPos + left, yPos, zOffset, (width - right) - left, top, left, 0, (scaling.width() - right) - left, top, scaling.width(), scaling.height());
        blitSprite(context, texture, scaling.width(), scaling.height(), scaling.width() - right, 0, (xPos + width) - right, yPos, zOffset, right, top);
        blitSprite(context, texture, scaling.width(), scaling.height(), 0, scaling.height() - bottom, xPos, (yPos + height) - bottom, zOffset, left, bottom);
        blitTiledSprite(context, texture, xPos + left, (yPos + height) - bottom, zOffset, (width - right) - left, bottom, left, scaling.height() - bottom, (scaling.width() - right) - left, bottom, scaling.width(), scaling.height());
        blitSprite(context, texture, scaling.width(), scaling.height(), scaling.width() - right, scaling.height() - bottom, (xPos + width) - right, (yPos + height) - bottom, zOffset, right, bottom);
        blitTiledSprite(context, texture, xPos, yPos + top, zOffset, left, (height - bottom) - top, 0, top, left, (scaling.height() - bottom) - top, scaling.width(), scaling.height());
        blitTiledSprite(context, texture, xPos + left, yPos + top, zOffset, (width - right) - left, (height - bottom) - top, left, top, (scaling.width() - right) - left, (scaling.height() - bottom) - top, scaling.width(), scaling.height());
        blitTiledSprite(context, texture, (xPos + width) - right, yPos + top, zOffset, left, (height - bottom) - top, scaling.width() - right, top, right, (scaling.height() - bottom) - top, scaling.width(), scaling.height());
    }

    private void blitTiledSprite(ResourceRenderContext context, TextureUV uv, int x, int y, int zOffset, int width, int height, int spriteX, int spriteY, int tileWidth, int tileHeight, int spriteWidth, int spriteHeight) {
        if (width < 0 || height < 0) {
            return;
        }
        if (tileWidth < 0 || tileHeight < 0) {
            throw new IllegalStateException("Tiled sprite texture size must be positive, got " + tileWidth + "x" + tileHeight);
        }
        int i = 0;
        while (true) {
            int currentWidth = i;
            if (currentWidth < width) {
                int toWidth = Math.min(tileWidth, width - currentWidth);
                int i2 = 0;
                while (true) {
                    int currentHeight = i2;
                    if (currentHeight < height) {
                        int toHeight = Math.min(tileHeight, height - currentHeight);
                        blitSprite(context, uv, spriteWidth, spriteHeight, spriteX, spriteY, x + currentWidth, y + currentHeight, zOffset, toWidth, toHeight);
                        i2 = currentHeight + tileHeight;
                    }
                }
                i = currentWidth + tileWidth;
            } else {
                return;
            }
        }
    }

    private void blitSprite(ResourceRenderContext context, TextureUV uv, int spriteWidth, int spriteHeight, int spriteX, int spriteY, int x, int y, int zOffset, int width, int height) {
        context.blitSprite(uv, spriteWidth, spriteHeight, spriteX, spriteY, x, y, zOffset, width, height, this.color);
    }
}
