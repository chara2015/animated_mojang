package net.labymod.api.client.gui.lss.style.modifier.attribute.state;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/state/PseudoClass.class */
public interface PseudoClass {
    Element element();

    boolean matchesState(Widget widget);

    int getPriority();
}
