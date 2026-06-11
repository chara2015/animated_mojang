package net.labymod.api.client.gui.screen;

import java.util.List;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.matrix.Stack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/VanillaScreen.class */
public interface VanillaScreen {
    void renderComponentHoverEffect(Stack stack, Style style, int i, int i2);

    List<Object> getWrappedWidgets();
}
