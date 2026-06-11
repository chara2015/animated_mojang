package net.labymod.core.client.gui.screen.widget.window.debug.util;

import java.util.List;
import net.labymod.api.client.gui.screen.VanillaScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/window/debug/util/VersionedDocument.class */
public class VersionedDocument extends VersionedWidget {
    private final VanillaScreen vanillaScreen;

    public VersionedDocument(VanillaScreen vanillaScreen) {
        this.vanillaScreen = vanillaScreen;
    }

    @Override // net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget
    public List<VersionedWidget> getChildren() {
        return this.vanillaScreen.getWrappedWidgets();
    }

    @Override // net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget
    public String getIdentifier() {
        return "VersionedDocument";
    }
}
