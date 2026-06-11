package net.labymod.api.client.gui.screen.widget.attributes.rules.media;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/rules/media/DocumentIdentifier.class */
public class DocumentIdentifier implements MediaIdentifier {
    private static final String IDENTIFIER = "document";
    private Rectangle rectangle = new PositionedBounds(0.0f, 0.0f, 0.0f, 0.0f);

    @Override // net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier
    public String identifier() {
        return IDENTIFIER;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier
    public Rectangle rectangle() {
        return this.rectangle;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier
    public void updateRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
