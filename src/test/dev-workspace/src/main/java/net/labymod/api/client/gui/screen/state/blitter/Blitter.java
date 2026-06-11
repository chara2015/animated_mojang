package net.labymod.api.client.gui.screen.state.blitter;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/blitter/Blitter.class */
public class Blitter {
    public void blitSprite(ScreenContext context, TextureAtlas atlas, ResourceLocation spriteLocation, int x, int y, int width, int height, int argb) {
        TextureSprite sprite = atlas.findSprite(spriteLocation);
        blitSprite(context, atlas.resource(), sprite, x, y, width, height, argb);
    }

    public void blitSprite(ScreenContext context, TextureAtlas atlas, ResourceLocation spriteLocation, int x, int y, int width, int height, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int argb) {
        TextureSprite sprite = atlas.findSprite(spriteLocation);
        if (sprite == null) {
            return;
        }
        if (sprite.scaling() instanceof StretchScaling) {
            blitSprite(context, atlas.resource(), sprite, spriteWidth, spriteHeight, spriteX, spriteY, x, y, width, height, argb);
            return;
        }
        Scissor scissor = context.canvas().scissor();
        scissor.push(x, y, width, height);
        blitSprite(context, atlas.resource(), sprite, x - spriteX, y - spriteY, spriteWidth, spriteHeight, argb);
        scissor.pop();
    }

    public void blitSprite(ScreenContext context, ResourceLocation location, TextureSprite sprite, int x, int y, int width, int height, int argb) {
        if (sprite == null) {
            return;
        }
        SpriteScaling scaling = sprite.scaling();
        if (scaling instanceof StretchScaling) {
            blitTextureSprite(context, location, sprite, x, y, width, height, argb);
            return;
        }
        if (scaling instanceof TileScaling) {
            TileScaling tileScaling = (TileScaling) scaling;
            try {
                int tileWidth = tileScaling.width();
                if (1 != 0) {
                    int tileHeight = tileScaling.height();
                    if (1 != 0) {
                        blitTiled(context, location, sprite, x, y, width, height, 0, 0, tileWidth, tileHeight, tileWidth, tileHeight, argb);
                        return;
                    }
                }
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        if (scaling instanceof NineSpliceScaling) {
            NineSpliceScaling nineSpliceScaling = (NineSpliceScaling) scaling;
            blitNineSliced(context, location, sprite, nineSpliceScaling, x, y, width, height, argb);
        }
    }

    private void blitNineSliced(ScreenContext context, ResourceLocation location, TextureSprite sprite, NineSpliceScaling scaling, int x, int y, int width, int height, int argb) {
        NineSpliceScaling.Border border = scaling.border();
        int leftBorder = Math.min(border.left(), width / 2);
        int rightBorder = Math.min(border.right(), width / 2);
        int topBorder = Math.min(border.top(), height / 2);
        int bottomBorder = Math.min(border.bottom(), height / 2);
        if (width == scaling.width() && height == scaling.height()) {
            blitSprite(context, location, sprite, scaling.width(), scaling.height(), 0, 0, x, y, width, height, argb);
            return;
        }
        if (height == scaling.height()) {
            blitSprite(context, location, sprite, scaling.width(), scaling.height(), 0, 0, x, y, leftBorder, height, argb);
            blitNineSliceInnerSegment(context, location, scaling, sprite, x + leftBorder, y, (width - rightBorder) - leftBorder, height, leftBorder, 0, (scaling.width() - rightBorder) - leftBorder, scaling.height(), scaling.width(), scaling.height(), argb);
            blitSprite(context, location, sprite, scaling.width(), scaling.height(), scaling.width() - rightBorder, 0, (x + width) - rightBorder, y, rightBorder, height, argb);
        } else {
            if (width == scaling.width()) {
                blitSprite(context, location, sprite, scaling.width(), scaling.height(), 0, 0, x, y, width, topBorder, argb);
                blitNineSliceInnerSegment(context, location, scaling, sprite, x, y + topBorder, width, (height - bottomBorder) - topBorder, 0, topBorder, scaling.width(), (scaling.height() - bottomBorder) - topBorder, scaling.width(), scaling.height(), argb);
                blitSprite(context, location, sprite, scaling.width(), scaling.height(), 0, scaling.height() - bottomBorder, x, (y + height) - bottomBorder, width, bottomBorder, argb);
                return;
            }
            blitSprite(context, location, sprite, scaling.width(), scaling.height(), 0, 0, x, y, leftBorder, topBorder, argb);
            blitNineSliceInnerSegment(context, location, scaling, sprite, x + leftBorder, y, (width - rightBorder) - leftBorder, topBorder, leftBorder, 0, (scaling.width() - rightBorder) - leftBorder, topBorder, scaling.width(), scaling.height(), argb);
            blitSprite(context, location, sprite, scaling.width(), scaling.height(), scaling.width() - rightBorder, 0, (x + width) - rightBorder, y, rightBorder, topBorder, argb);
            blitSprite(context, location, sprite, scaling.width(), scaling.height(), 0, scaling.height() - bottomBorder, x, (y + height) - bottomBorder, leftBorder, bottomBorder, argb);
            blitNineSliceInnerSegment(context, location, scaling, sprite, x + leftBorder, (y + height) - bottomBorder, (width - rightBorder) - leftBorder, bottomBorder, leftBorder, scaling.height() - bottomBorder, (scaling.width() - rightBorder) - leftBorder, bottomBorder, scaling.width(), scaling.height(), argb);
            blitSprite(context, location, sprite, scaling.width(), scaling.height(), scaling.width() - rightBorder, scaling.height() - bottomBorder, (x + width) - rightBorder, (y + height) - bottomBorder, rightBorder, bottomBorder, argb);
            blitNineSliceInnerSegment(context, location, scaling, sprite, x, y + topBorder, leftBorder, (height - bottomBorder) - topBorder, 0, topBorder, leftBorder, (scaling.height() - bottomBorder) - topBorder, scaling.width(), scaling.height(), argb);
            blitNineSliceInnerSegment(context, location, scaling, sprite, x + leftBorder, y + topBorder, (width - rightBorder) - leftBorder, (height - bottomBorder) - topBorder, leftBorder, topBorder, (scaling.width() - rightBorder) - leftBorder, (scaling.height() - bottomBorder) - topBorder, scaling.width(), scaling.height(), argb);
            blitNineSliceInnerSegment(context, location, scaling, sprite, (x + width) - rightBorder, y + topBorder, rightBorder, (height - bottomBorder) - topBorder, scaling.width() - rightBorder, topBorder, rightBorder, (scaling.height() - bottomBorder) - topBorder, scaling.width(), scaling.height(), argb);
        }
    }

    private void blitNineSliceInnerSegment(ScreenContext context, ResourceLocation location, NineSpliceScaling scaling, TextureSprite sprite, int x, int y, int width, int height, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int textureAtlasWidth, int textureAtlasHeight, int argb) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (scaling.stretchInner()) {
            TextureUV uv = sprite.uv();
            blit(context, location, x, y, x + width, y + height, uv.getU(spriteX / textureAtlasWidth), uv.getV(spriteY / textureAtlasHeight), uv.getU((spriteX + spriteWidth) / textureAtlasWidth), uv.getV((spriteY + spriteHeight) / textureAtlasHeight), argb);
        } else {
            blitTiled(context, location, sprite, x, y, width, height, spriteX, spriteY, spriteWidth, spriteHeight, textureAtlasWidth, textureAtlasHeight, argb);
        }
    }

    private void blitTiled(ScreenContext context, ResourceLocation location, TextureSprite sprite, int x, int y, int targetWidth, int targetHeight, int uOffset, int vOffset, int tileTextureWidth, int tileTextureHeight, int spriteTextureWidth, int spriteTextureHeight, int argb) {
        if (targetWidth <= 0 || targetHeight <= 0) {
            return;
        }
        if (tileTextureWidth <= 0 || tileTextureHeight <= 0) {
            throw new IllegalArgumentException("Tiled sprite texture size must be greater than zero. (Got " + tileTextureWidth + "x" + tileTextureHeight + ")");
        }
        int i = 0;
        while (true) {
            int currentX = i;
            if (currentX < targetWidth) {
                int currentTileRenderWidth = Math.min(tileTextureWidth, targetWidth - currentX);
                int i2 = 0;
                while (true) {
                    int currentY = i2;
                    if (currentY < targetHeight) {
                        int currentTileRenderHeight = Math.min(tileTextureHeight, targetHeight - currentY);
                        blitSprite(context, location, sprite, spriteTextureWidth, spriteTextureHeight, uOffset, vOffset, x + currentX, y + currentY, currentTileRenderWidth, currentTileRenderHeight, argb);
                        i2 = currentY + tileTextureHeight;
                    }
                }
                i = currentX + tileTextureWidth;
            } else {
                return;
            }
        }
    }

    private void blitTextureSprite(ScreenContext context, ResourceLocation location, TextureSprite sprite, int x, int y, int width, int height, int argb) {
        if (width == 0 || height == 0) {
            return;
        }
        TextureUV uv = sprite.uv();
        blit(context, location, x, y, width, height, uv.getMinU(), uv.getMinV(), uv.getMaxU(), uv.getMaxV(), argb);
    }

    private void blitSprite(ScreenContext context, ResourceLocation location, TextureSprite sprite, int textureAtlasWidth, int textureAtlasHeight, int spriteX, int spriteY, int screenX, int screenY, int width, int height, int argb) {
        if (width == 0 || height == 0) {
            return;
        }
        TextureUV uv = sprite.uv();
        blit(context, location, screenX, screenY, width, height, uv.getU(spriteX / textureAtlasWidth), uv.getV(spriteY / textureAtlasHeight), uv.getU((spriteX + width) / textureAtlasWidth), uv.getV((spriteY + height) / textureAtlasHeight), argb);
    }

    private void blit(ScreenContext context, ResourceLocation location, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int argb) {
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, location), x, y, width, height, minU, minV, maxU, maxV, argb);
    }
}
