package net.labymod.api.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FontProperties.class */
public final class FontProperties {
    public static final ResourceLocation FONT_WEIGHT_KEY = buildProperty("font_weight");

    @NotNull
    private static ResourceLocation buildProperty(@NotNull String path) {
        return ResourceLocation.create("labymod", "font/properties/" + path);
    }
}
