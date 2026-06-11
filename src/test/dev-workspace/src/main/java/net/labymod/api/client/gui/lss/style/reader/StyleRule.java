package net.labymod.api.client.gui.lss.style.reader;

import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/reader/StyleRule.class */
public interface StyleRule {
    @NotNull
    StyleSheet sourceStyleSheet();

    String getKey();

    String getValue();

    List<StyleBlock> getBlocks();

    Object process();
}
