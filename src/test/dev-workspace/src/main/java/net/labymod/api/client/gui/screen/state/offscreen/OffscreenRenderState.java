package net.labymod.api.client.gui.screen.state.offscreen;

import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.GuiTransform;
import net.labymod.api.client.gui.screen.state.RoundedData;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/OffscreenRenderState.class */
public interface OffscreenRenderState extends GuiComponent, GuiTransform {
    float getScale();

    @Nullable
    RoundedData getRoundedData();
}
