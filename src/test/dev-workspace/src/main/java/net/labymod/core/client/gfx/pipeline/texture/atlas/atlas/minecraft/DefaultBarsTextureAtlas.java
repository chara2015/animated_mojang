package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/atlas/minecraft/DefaultBarsTextureAtlas.class */
public class DefaultBarsTextureAtlas extends AbstractTextureAtlas {
    private static final String[] COLOR_NAMES = {"pink", "blue", "red", "green", "yellow", "purple", "white"};
    private static final String[] NOTCH_REPEAT = {"6", "10", "12", "20"};

    public DefaultBarsTextureAtlas() {
        super(Atlases.BARS_ATLAS, 256, 256);
        for (int index = 0; index < COLOR_NAMES.length; index++) {
            register(createMinecraft("boss_bar/" + COLOR_NAMES[index] + "_background"), 0, index * 10, 182, 5, (width, height) -> {
                return new StretchScaling();
            });
            register(createMinecraft("boss_bar/" + COLOR_NAMES[index] + "_progress"), 0, 5 + (index * 10), 182, 5, (width2, height2) -> {
                return new StretchScaling();
            });
        }
        int notchedY = (COLOR_NAMES.length * 10) + 10;
        for (int index2 = 0; index2 < NOTCH_REPEAT.length; index2++) {
            register(createMinecraft("boss_bar/notched_" + NOTCH_REPEAT[index2] + "_background"), 0, notchedY + (index2 * 10), 182, 5, (width3, height3) -> {
                return new StretchScaling();
            });
            register(createMinecraft("boss_bar/notched_" + NOTCH_REPEAT[index2] + "_progress"), 0, notchedY + 5 + (index2 * 10), 182, 5, (width4, height4) -> {
                return new StretchScaling();
            });
        }
    }
}
