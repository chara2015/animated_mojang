package net.labymod.api.client.gui.lss.style.function;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/function/Function.class */
public interface Function extends Element {
    String getName();

    Element[] parameters();

    ProcessedObject[][] allValues(Widget widget, String str) throws Exception;

    ProcessedObject[] firstValues(Widget widget, String str) throws Exception;

    String toString();
}
