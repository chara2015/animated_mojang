package net.labymod.api.client.gui.screen.widget.attributes.rules.media;

import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/rules/media/MediaIdentifier.class */
public interface MediaIdentifier {
    String identifier();

    Rectangle rectangle();

    void updateRectangle(Rectangle rectangle);
}
