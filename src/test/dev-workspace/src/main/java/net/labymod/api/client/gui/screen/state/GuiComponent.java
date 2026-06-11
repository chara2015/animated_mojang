package net.labymod.api.client.gui.screen.state;

import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/GuiComponent.class */
public interface GuiComponent {
    Rectangle bounds();

    @Nullable
    ScissorArea getScissorArea();

    @Nullable
    default ClipShape clipShape() {
        return null;
    }
}
