package net.labymod.api.client.render.draw.hover;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/hover/HoverBackgroundEffectRenderer.class */
@Referenceable
public interface HoverBackgroundEffectRenderer {
    HoverBackgroundEffectRenderer hoverEffect(HoverBackgroundEffect hoverBackgroundEffect);

    HoverBackgroundEffectRenderer x(float f);

    HoverBackgroundEffectRenderer y(float f);

    HoverBackgroundEffectRenderer width(float f);

    HoverBackgroundEffectRenderer height(float f);

    HoverBackgroundEffectRenderer pos(float f, float f2, float f3, float f4);

    HoverBackgroundEffectRenderer component(RenderableComponent renderableComponent);

    void render(ScreenContext screenContext);
}
