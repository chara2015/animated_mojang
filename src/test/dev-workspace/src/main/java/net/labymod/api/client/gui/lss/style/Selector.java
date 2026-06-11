package net.labymod.api.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/Selector.class */
public interface Selector {
    String buildSelector();

    boolean hasStateAttributes();

    boolean hasParentStateAttributes();

    boolean hasEnabledStateAttributes(Widget widget);

    PseudoClass lastPseudoClass();

    boolean match(Widget widget, boolean z);

    boolean match(int i, Widget widget, boolean z);
}
