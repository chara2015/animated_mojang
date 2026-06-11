package net.labymod.api.client.render.draw.hover;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/hover/HoverBackgroundEffect.class */
@Referenceable(named = true)
public interface HoverBackgroundEffect {
    float getPadding();

    void render(ScreenContext screenContext, float f, float f2, float f3, float f4, RenderableComponent renderableComponent);

    default void render(ScreenContext context, float x, float y, float width, float height) {
        render(context, x, y, width, height, null);
    }

    default float getScale() {
        return 1.0f;
    }
}
