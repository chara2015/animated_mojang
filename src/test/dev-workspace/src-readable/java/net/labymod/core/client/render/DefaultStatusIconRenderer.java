package net.labymod.core.client.render;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.StatusIcon;
import net.labymod.api.client.render.StatusIconRenderer;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/DefaultStatusIconRenderer.class */
@Singleton
@Implements(StatusIconRenderer.class)
public class DefaultStatusIconRenderer implements StatusIconRenderer {
    private final LabyAPI labyAPI;
    private StatusIcon[] statusIcons;
    private float x;
    private float y;
    private int amount;

    @Inject
    public DefaultStatusIconRenderer(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.client.render.StatusIconRenderer
    public StatusIconRenderer pos(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override // net.labymod.api.client.render.StatusIconRenderer
    public StatusIconRenderer statusIcon(StatusIcon... icons) {
        this.statusIcons = icons;
        return this;
    }

    @Override // net.labymod.api.client.render.StatusIconRenderer
    public StatusIconRenderer amount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override // net.labymod.api.client.render.StatusIconRenderer
    public void render(ScreenContext context) {
        MinecraftTextures textures = this.labyAPI.minecraft().textures();
        TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(textures.iconsTexture());
        ScreenCanvas screenCanvas = context.canvas();
        for (StatusIcon statusIcon : this.statusIcons) {
            float width = 0.0f;
            for (int i = 0; i < this.amount; i++) {
                screenCanvas.submitGuiSprite(atlas, statusIcon.location(), (int) (this.x + width), (int) this.y, 9.0f, 9.0f, -1);
                width += 8.0f;
            }
        }
    }

    @Override // net.labymod.api.client.render.StatusIconRenderer
    public float getWidth(int amount, float size) {
        return amount * size;
    }
}
