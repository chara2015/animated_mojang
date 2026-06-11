package net.labymod.api.client.render.font.text;

import net.labymod.api.client.gfx.pipeline.renderer.text.Fonts;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/text/TextRendererProvider.class */
@Referenceable
public interface TextRendererProvider {
    TextRenderer getRenderer();

    boolean useCustomFont();

    void setUseCustomFont(boolean z);

    boolean isMinecraftRendererForced();

    void forceMinecraftRenderer(boolean z);

    boolean shouldUseMinecraftFont();

    void forceVanillaFont(boolean z, Runnable runnable);

    default boolean isVanillaFontRenderer() {
        return getRenderer().getCurrentFont() == Fonts.MINECRAFT;
    }
}
