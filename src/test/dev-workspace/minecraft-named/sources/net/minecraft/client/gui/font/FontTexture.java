package net.minecraft.client.gui.font;

import com.mojang.blaze3d.font.GlyphBitmap;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.TextureFormat;
import java.nio.file.Path;
import java.util.function.Supplier;
import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.Dumpable;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/FontTexture.class */
public class FontTexture extends AbstractTexture implements Dumpable {
    private static final int SIZE = 256;
    private final GlyphRenderTypes renderTypes;
    private final boolean colored;
    private final Node root = new Node(0, 0, 256, 256);

    public FontTexture(Supplier<String> $$0, GlyphRenderTypes $$1, boolean $$2) {
        this.colored = $$2;
        GpuDevice $$3 = RenderSystem.getDevice();
        this.texture = $$3.createTexture($$0, 7, $$2 ? TextureFormat.RGBA8 : TextureFormat.RED8, 256, 256, 1, 1);
        this.sampler = RenderSystem.getSamplerCache().getRepeat(FilterMode.NEAREST);
        this.textureView = $$3.createTextureView(this.texture);
        this.renderTypes = $$1;
    }

    public BakedSheetGlyph add(GlyphInfo $$0, GlyphBitmap $$1) {
        Node $$2;
        if ($$1.isColored() == this.colored && ($$2 = this.root.insert($$1)) != null) {
            $$1.upload($$2.x, $$2.y, getTexture());
            return new BakedSheetGlyph($$0, this.renderTypes, getTextureView(), ($$2.x + 0.01f) / 256.0f, (($$2.x - 0.01f) + $$1.getPixelWidth()) / 256.0f, ($$2.y + 0.01f) / 256.0f, (($$2.y - 0.01f) + $$1.getPixelHeight()) / 256.0f, $$1.getLeft(), $$1.getRight(), $$1.getTop(), $$1.getBottom());
        }
        return null;
    }

    @Override // net.minecraft.client.renderer.texture.Dumpable
    public void dumpContents(Identifier $$0, Path $$1) {
        if (this.texture == null) {
            return;
        }
        String $$2 = $$0.toDebugFileName();
        TextureUtil.writeAsPNG($$1, $$2, this.texture, 0, $$02 -> {
            if (($$02 & (-16777216)) == 0) {
                return -16777216;
            }
            return $$02;
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/FontTexture$Node.class */
    static class Node {
        final int x;
        final int y;
        private final int width;
        private final int height;
        private Node left;
        private Node right;
        private boolean occupied;

        Node(int $$0, int $$1, int $$2, int $$3) {
            this.x = $$0;
            this.y = $$1;
            this.width = $$2;
            this.height = $$3;
        }

        Node insert(GlyphBitmap $$0) {
            if (this.left != null && this.right != null) {
                Node $$1 = this.left.insert($$0);
                if ($$1 == null) {
                    $$1 = this.right.insert($$0);
                }
                return $$1;
            }
            if (this.occupied) {
                return null;
            }
            int $$2 = $$0.getPixelWidth();
            int $$3 = $$0.getPixelHeight();
            if ($$2 > this.width || $$3 > this.height) {
                return null;
            }
            if ($$2 == this.width && $$3 == this.height) {
                this.occupied = true;
                return this;
            }
            int $$4 = this.width - $$2;
            int $$5 = this.height - $$3;
            if ($$4 > $$5) {
                this.left = new Node(this.x, this.y, $$2, this.height);
                this.right = new Node(this.x + $$2 + 1, this.y, (this.width - $$2) - 1, this.height);
            } else {
                this.left = new Node(this.x, this.y, this.width, $$3);
                this.right = new Node(this.x, this.y + $$3 + 1, this.width, (this.height - $$3) - 1);
            }
            return this.left.insert($$0);
        }
    }
}
