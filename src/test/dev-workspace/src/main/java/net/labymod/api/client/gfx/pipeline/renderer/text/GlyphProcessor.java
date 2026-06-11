package net.labymod.api.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/GlyphProcessor.class */
public interface GlyphProcessor {
    boolean accept(int i, Style style, int i2);

    @ApiStatus.Internal
    default void finish() {
    }
}
