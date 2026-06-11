package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/VanillaWindowBuilder.class */
@Referenceable
public interface VanillaWindowBuilder {
    VanillaWindowBuilder top(boolean z);

    VanillaWindowBuilder bottom(boolean z);

    VanillaWindowBuilder rescalable(boolean z);

    VanillaWindowBuilder position(Rectangle rectangle);

    VanillaWindowBuilder render(ScreenContext screenContext);
}
