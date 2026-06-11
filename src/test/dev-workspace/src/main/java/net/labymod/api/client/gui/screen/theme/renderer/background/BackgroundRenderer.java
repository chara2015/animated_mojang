package net.labymod.api.client.gui.screen.theme.renderer.background;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/renderer/background/BackgroundRenderer.class */
public interface BackgroundRenderer {
    boolean renderBackground(ScreenContext screenContext, Object obj);

    @Nullable
    default ResourceLocation getBackgroundLocation() {
        return null;
    }
}
