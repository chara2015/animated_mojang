package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.font.GlyphBitmap;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import java.util.function.Supplier;
import net.minecraft.client.gui.font.GlyphStitcher;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/glyphs/SpecialGlyphs.class */
public enum SpecialGlyphs implements GlyphInfo {
    WHITE(() -> {
        return generate(5, 8, ($$0, $$1) -> {
            return -1;
        });
    }),
    MISSING(() -> {
        return generate(5, 8, ($$0, $$1) -> {
            boolean $$2 = $$0 == 0 || $$0 + 1 == 5 || $$1 == 0 || $$1 + 1 == 8;
            return $$2 ? -1 : 0;
        });
    });

    final NativeImage image;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/glyphs/SpecialGlyphs$PixelProvider.class */
    @FunctionalInterface
    interface PixelProvider {
        int getColor(int i, int i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NativeImage generate(int $$0, int $$1, PixelProvider $$2) {
        NativeImage $$3 = new NativeImage(NativeImage.Format.RGBA, $$0, $$1, false);
        for (int $$4 = 0; $$4 < $$1; $$4++) {
            for (int $$5 = 0; $$5 < $$0; $$5++) {
                $$3.setPixel($$5, $$4, $$2.getColor($$5, $$4));
            }
        }
        $$3.untrack();
        return $$3;
    }

    SpecialGlyphs(Supplier supplier) {
        this.image = (NativeImage) supplier.get();
    }

    @Override // com.mojang.blaze3d.font.GlyphInfo
    public float getAdvance() {
        return this.image.getWidth() + 1;
    }

    public BakedSheetGlyph bake(GlyphStitcher $$0) {
        return $$0.stitch(this, new GlyphBitmap() { // from class: net.minecraft.client.gui.font.glyphs.SpecialGlyphs.1
            @Override // com.mojang.blaze3d.font.GlyphBitmap
            public int getPixelWidth() {
                return SpecialGlyphs.this.image.getWidth();
            }

            @Override // com.mojang.blaze3d.font.GlyphBitmap
            public int getPixelHeight() {
                return SpecialGlyphs.this.image.getHeight();
            }

            @Override // com.mojang.blaze3d.font.GlyphBitmap
            public float getOversample() {
                return 1.0f;
            }

            @Override // com.mojang.blaze3d.font.GlyphBitmap
            public void upload(int $$02, int $$1, GpuTexture $$2) {
                RenderSystem.getDevice().createCommandEncoder().writeToTexture($$2, SpecialGlyphs.this.image, 0, 0, $$02, $$1, SpecialGlyphs.this.image.getWidth(), SpecialGlyphs.this.image.getHeight(), 0, 0);
            }

            @Override // com.mojang.blaze3d.font.GlyphBitmap
            public boolean isColored() {
                return true;
            }
        });
    }
}
