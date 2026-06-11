package net.labymod.api.client.gui.screen.widget.attributes.rules.media;

import net.labymod.api.Laby;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/rules/media/ScreenIdentifier.class */
public class ScreenIdentifier implements MediaIdentifier {
    private static final String IDENTIFIER = "screen";

    @Override // net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier
    public String identifier() {
        return IDENTIFIER;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier
    public Rectangle rectangle() {
        return Laby.labyAPI().minecraft().minecraftWindow().absoluteBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier
    public void updateRectangle(Rectangle rectangle) {
    }
}
