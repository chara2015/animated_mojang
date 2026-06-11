package net.labymod.api.client.gui.screen.widget;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/WidgetIdentifier.class */
public interface WidgetIdentifier {
    String getIdentifier();

    default List<String> getIdentifiers() {
        return List.of(getIdentifier());
    }
}
