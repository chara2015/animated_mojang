package net.labymod.api.client.render;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/HotbarRenderer.class */
@Referenceable
public interface HotbarRenderer {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/HotbarRenderer$Layout.class */
    public enum Layout {
        VERTICAL,
        HORIZONTAL
    }

    HotbarRenderer mode(RenderMode renderMode);

    HotbarRenderer pos(float f, float f2);

    HotbarRenderer layout(Layout layout);

    void render(ScreenContext screenContext);

    float getWidth();

    float getHeight();
}
