package net.labymod.api.client.gui.screen.theme;

import java.io.IOException;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/ResourceLoader.class */
public interface ResourceLoader {
    @Deprecated
    StyleSheet getOrLoadStyleSheet(@Nullable LinkMetaList linkMetaList, ThemeFile themeFile) throws IOException;
}
