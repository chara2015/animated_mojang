package net.labymod.api.client.gui.screen;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/Parent.class */
public interface Parent {
    Parent getParent();

    Bounds bounds();

    Parent getRoot();

    default boolean hasAutoBounds(Widget child, AutoAlignType type) {
        return false;
    }
}
