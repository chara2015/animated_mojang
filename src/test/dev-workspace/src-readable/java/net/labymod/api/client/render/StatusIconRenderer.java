package net.labymod.api.client.render;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/StatusIconRenderer.class */
@Referenceable
@ApiStatus.Experimental
public interface StatusIconRenderer {
    StatusIconRenderer pos(float f, float f2);

    StatusIconRenderer statusIcon(StatusIcon... statusIconArr);

    StatusIconRenderer amount(int i);

    void render(ScreenContext screenContext);

    float getWidth(int i, float f);
}
