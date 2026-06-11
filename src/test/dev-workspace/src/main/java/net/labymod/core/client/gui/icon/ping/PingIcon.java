package net.labymod.core.client.gui.icon.ping;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/icon/ping/PingIcon.class */
public abstract class PingIcon {
    private static final ResourceLocation ICONS = Atlases.ICON_ATLAS;

    public abstract Icon get(int i);

    protected Icon getIcon(ResourceLocation location) {
        TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(ICONS);
        return Icon.sprite(atlas, atlas.findSprite(location));
    }
}
