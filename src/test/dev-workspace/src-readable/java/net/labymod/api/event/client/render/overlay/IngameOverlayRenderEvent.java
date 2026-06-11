package net.labymod.api.event.client.render.overlay;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/IngameOverlayRenderEvent.class */
public class IngameOverlayRenderEvent extends RenderEvent {
    private final boolean guiHidden;
    private final ScreenContext context;

    public IngameOverlayRenderEvent(@NotNull ScreenContext context, @NotNull Phase phase, boolean guiHidden) {
        super(context.stack(), phase);
        this.guiHidden = guiHidden;
        this.context = context;
    }

    public ScreenContext context() {
        return this.context;
    }

    public boolean isGuiHidden() {
        return this.guiHidden;
    }
}
