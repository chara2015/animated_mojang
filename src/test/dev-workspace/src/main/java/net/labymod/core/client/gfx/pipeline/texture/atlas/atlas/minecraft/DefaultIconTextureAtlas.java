package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/atlas/minecraft/DefaultIconTextureAtlas.class */
public class DefaultIconTextureAtlas extends AbstractTextureAtlas {
    private static final boolean ICONS_ATLAS = MinecraftVersions.V1_8_9.orOlder();

    public DefaultIconTextureAtlas() {
        super(Atlases.ICON_ATLAS, 256, 256);
        AtlasUtil.buildIcons((x$0, x$1, x$2, x$3, x$4, x$5) -> {
            this.register(x$0, x$1, x$2, x$3, x$4, x$5);
        });
        if (ICONS_ATLAS) {
            register(createMinecraft("boss_bar/pink_background"), 0, 74, 182, 5, (width, height) -> {
                return new StretchScaling();
            });
            register(createMinecraft("boss_bar/pink_progress"), 0, 79, 182, 5, (width2, height2) -> {
                return new StretchScaling();
            });
        }
    }
}
