package net.labymod.api.client.gui.lss;

import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/StyleSheetLoader.class */
@Referenceable
public interface StyleSheetLoader {
    void invalidate();

    StyleSheet load(ThemeFile themeFile);
}
