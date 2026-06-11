package net.labymod.api.client.gui.lss.style.reader;

import java.util.Map;
import net.labymod.api.client.gui.lss.style.StyleSelectorList;
import net.labymod.api.client.gui.lss.style.StyleSheet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/reader/StyleBlock.class */
public interface StyleBlock extends StyleSelectorList {
    StyleSheet styleSheet();

    Map<String, SingleInstruction> getInstructions();

    void put(SingleInstruction singleInstruction);

    void setRawSelector(int i, String str);

    String getRawSelector();

    int getDepth();

    int getLineOf(String str);
}
