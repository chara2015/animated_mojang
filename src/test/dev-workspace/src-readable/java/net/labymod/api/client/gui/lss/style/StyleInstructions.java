package net.labymod.api.client.gui.lss.style;

import java.util.Map;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/StyleInstructions.class */
public interface StyleInstructions {
    Selector getSelector();

    Map<String, SingleInstruction> getInstructions();

    StyleBlock getBlock();
}
