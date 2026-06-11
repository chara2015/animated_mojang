package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/MipmapGenerator.class */
public class MipmapGenerator {
    private static final String ITEM_PREFIX = "item/";
    private static final float ALPHA_CUTOFF = 0.5f;
    private static final float STRICT_ALPHA_CUTOFF = 0.3f;

    private MipmapGenerator() {
    }

    private static float alphaTestCoverage(NativeImage $$0, float $$1, float $$2) {
        int $$3 = $$0.getWidth();
        int $$4 = $$0.getHeight();
        float $$5 = 0.0f;
        for (int $$7 = 0; $$7 < $$4 - 1; $$7++) {
            for (int $$8 = 0; $$8 < $$3 - 1; $$8++) {
                float $$9 = Math.clamp(ARGB.alphaFloat($$0.getPixel($$8, $$7)) * $$2, 0.0f, 1.0f);
                float $$10 = Math.clamp(ARGB.alphaFloat($$0.getPixel($$8 + 1, $$7)) * $$2, 0.0f, 1.0f);
                float $$11 = Math.clamp(ARGB.alphaFloat($$0.getPixel($$8, $$7 + 1)) * $$2, 0.0f, 1.0f);
                float $$12 = Math.clamp(ARGB.alphaFloat($$0.getPixel($$8 + 1, $$7 + 1)) * $$2, 0.0f, 1.0f);
                float $$13 = 0.0f;
                for (int $$14 = 0; $$14 < 4; $$14++) {
                    float $$15 = ($$14 + 0.5f) / 4.0f;
                    for (int $$16 = 0; $$16 < 4; $$16++) {
                        float $$17 = ($$16 + 0.5f) / 4.0f;
                        float $$18 = ($$9 * (1.0f - $$17) * (1.0f - $$15)) + ($$10 * $$17 * (1.0f - $$15)) + ($$11 * (1.0f - $$17) * $$15) + ($$12 * $$17 * $$15);
                        if ($$18 > $$1) {
                            $$13 += 1.0f;
                        }
                    }
                }
                $$5 += $$13 / 16.0f;
            }
        }
        return $$5 / (($$3 - 1) * ($$4 - 1));
    }

    private static void scaleAlphaToCoverage(NativeImage $$0, float $$1, float $$2, float $$3) {
        float $$4 = 0.0f;
        float $$5 = 4.0f;
        float $$6 = 1.0f;
        float $$7 = 1.0f;
        float $$8 = Float.MAX_VALUE;
        int $$9 = $$0.getWidth();
        int $$10 = $$0.getHeight();
        for (int $$11 = 0; $$11 < 5; $$11++) {
            float $$12 = alphaTestCoverage($$0, $$2, $$6);
            float $$13 = Math.abs($$12 - $$1);
            if ($$13 < $$8) {
                $$8 = $$13;
                $$7 = $$6;
            }
            if ($$12 < $$1) {
                $$4 = $$6;
            } else if ($$12 <= $$1) {
                break;
            } else {
                $$5 = $$6;
            }
            $$6 = ($$4 + $$5) * 0.5f;
        }
        for (int $$14 = 0; $$14 < $$10; $$14++) {
            for (int $$15 = 0; $$15 < $$9; $$15++) {
                int $$16 = $$0.getPixel($$15, $$14);
                float $$17 = ARGB.alphaFloat($$16);
                $$0.setPixel($$15, $$14, ARGB.color(Math.clamp(($$17 * $$7) + $$3 + 0.025f, 0.0f, 1.0f), $$16));
            }
        }
    }

    public static NativeImage[] generateMipLevels(Identifier $$0, NativeImage[] $$1, int $$2, MipmapStrategy $$3, float $$4) {
        int iMeanLinear;
        if ($$3 == MipmapStrategy.AUTO) {
            $$3 = hasTransparentPixel($$1[0]) ? MipmapStrategy.CUTOUT : MipmapStrategy.MEAN;
        }
        if ($$1.length == 1 && !$$0.getPath().startsWith(ITEM_PREFIX)) {
            if ($$3 == MipmapStrategy.CUTOUT || $$3 == MipmapStrategy.STRICT_CUTOUT) {
                TextureUtil.solidify($$1[0]);
            } else if ($$3 == MipmapStrategy.DARK_CUTOUT) {
                TextureUtil.fillEmptyAreasWithDarkColor($$1[0]);
            }
        }
        if ($$2 + 1 <= $$1.length) {
            return $$1;
        }
        NativeImage[] $$5 = new NativeImage[$$2 + 1];
        $$5[0] = $$1[0];
        boolean $$6 = $$3 == MipmapStrategy.CUTOUT || $$3 == MipmapStrategy.STRICT_CUTOUT || $$3 == MipmapStrategy.DARK_CUTOUT;
        float $$7 = $$3 == MipmapStrategy.STRICT_CUTOUT ? 0.3f : 0.5f;
        float $$8 = $$6 ? alphaTestCoverage($$1[0], $$7, 1.0f) : 0.0f;
        for (int $$9 = 1; $$9 <= $$2; $$9++) {
            if ($$9 < $$1.length) {
                $$5[$$9] = $$1[$$9];
            } else {
                NativeImage $$10 = $$5[$$9 - 1];
                NativeImage $$11 = new NativeImage($$10.getWidth() >> 1, $$10.getHeight() >> 1, false);
                int $$12 = $$11.getWidth();
                int $$13 = $$11.getHeight();
                for (int $$14 = 0; $$14 < $$12; $$14++) {
                    for (int $$15 = 0; $$15 < $$13; $$15++) {
                        int $$16 = $$10.getPixel(($$14 * 2) + 0, ($$15 * 2) + 0);
                        int $$17 = $$10.getPixel(($$14 * 2) + 1, ($$15 * 2) + 0);
                        int $$18 = $$10.getPixel(($$14 * 2) + 0, ($$15 * 2) + 1);
                        int $$19 = $$10.getPixel(($$14 * 2) + 1, ($$15 * 2) + 1);
                        if ($$3 == MipmapStrategy.DARK_CUTOUT) {
                            iMeanLinear = darkenedAlphaBlend($$16, $$17, $$18, $$19);
                        } else {
                            iMeanLinear = ARGB.meanLinear($$16, $$17, $$18, $$19);
                        }
                        int $$21 = iMeanLinear;
                        $$11.setPixel($$14, $$15, $$21);
                    }
                }
                $$5[$$9] = $$11;
            }
            if ($$6) {
                scaleAlphaToCoverage($$5[$$9], $$8, $$7, $$4);
            }
        }
        return $$5;
    }

    private static boolean hasTransparentPixel(NativeImage $$0) {
        for (int $$1 = 0; $$1 < $$0.getWidth(); $$1++) {
            for (int $$2 = 0; $$2 < $$0.getHeight(); $$2++) {
                if (ARGB.alpha($$0.getPixel($$1, $$2)) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int darkenedAlphaBlend(int $$0, int $$1, int $$2, int $$3) {
        float $$4 = 0.0f;
        float $$5 = 0.0f;
        float $$6 = 0.0f;
        float $$7 = 0.0f;
        if (ARGB.alpha($$0) != 0) {
            $$4 = 0.0f + ARGB.srgbToLinearChannel(ARGB.alpha($$0));
            $$5 = 0.0f + ARGB.srgbToLinearChannel(ARGB.red($$0));
            $$6 = 0.0f + ARGB.srgbToLinearChannel(ARGB.green($$0));
            $$7 = 0.0f + ARGB.srgbToLinearChannel(ARGB.blue($$0));
        }
        if (ARGB.alpha($$1) != 0) {
            $$4 += ARGB.srgbToLinearChannel(ARGB.alpha($$1));
            $$5 += ARGB.srgbToLinearChannel(ARGB.red($$1));
            $$6 += ARGB.srgbToLinearChannel(ARGB.green($$1));
            $$7 += ARGB.srgbToLinearChannel(ARGB.blue($$1));
        }
        if (ARGB.alpha($$2) != 0) {
            $$4 += ARGB.srgbToLinearChannel(ARGB.alpha($$2));
            $$5 += ARGB.srgbToLinearChannel(ARGB.red($$2));
            $$6 += ARGB.srgbToLinearChannel(ARGB.green($$2));
            $$7 += ARGB.srgbToLinearChannel(ARGB.blue($$2));
        }
        if (ARGB.alpha($$3) != 0) {
            $$4 += ARGB.srgbToLinearChannel(ARGB.alpha($$3));
            $$5 += ARGB.srgbToLinearChannel(ARGB.red($$3));
            $$6 += ARGB.srgbToLinearChannel(ARGB.green($$3));
            $$7 += ARGB.srgbToLinearChannel(ARGB.blue($$3));
        }
        return ARGB.color(ARGB.linearToSrgbChannel($$4 / 4.0f), ARGB.linearToSrgbChannel($$5 / 4.0f), ARGB.linearToSrgbChannel($$6 / 4.0f), ARGB.linearToSrgbChannel($$7 / 4.0f));
    }
}
