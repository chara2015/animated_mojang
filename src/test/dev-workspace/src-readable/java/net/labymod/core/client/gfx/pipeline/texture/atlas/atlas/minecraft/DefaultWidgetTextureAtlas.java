package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/atlas/minecraft/DefaultWidgetTextureAtlas.class */
public class DefaultWidgetTextureAtlas extends AbstractTextureAtlas {
    public DefaultWidgetTextureAtlas() {
        super(Atlases.WIDGET_ATLAS, 256, 256);
        register(createMinecraft("widget/button_disabled"), 0, 46, 200, 20, (width, height) -> {
            return createNineSpliceScaling(width, height, 1);
        });
        register(createMinecraft("widget/button"), 0, 66, 200, 20, (width2, height2) -> {
            return createNineSpliceScaling(width2, height2, 3);
        });
        register(createMinecraft("widget/button_highlighted"), 0, 86, 200, 20, (width3, height3) -> {
            return createNineSpliceScaling(width3, height3, 3);
        });
        register(createMinecraft("hud/hotbar"), 0, 0, 182, 22, (width4, height4) -> {
            return new StretchScaling();
        });
        register(createMinecraft("hud/hotbar_selection"), 0, 22, 24, 24, (width5, height5) -> {
            return new StretchScaling();
        });
        AtlasUtil.buildIcons((x$0, x$1, x$2, x$3, x$4, x$5) -> {
            this.register(x$0, x$1, x$2, x$3, x$4, x$5);
        });
    }

    private SpriteScaling createNineSpliceScaling(float width, float height, int borderSize) {
        int w = MathHelper.ceil(width);
        int h = MathHelper.ceil(height);
        return new NineSpliceScaling(w, h, new NineSpliceScaling.Border(borderSize, borderSize, borderSize, borderSize));
    }
}
