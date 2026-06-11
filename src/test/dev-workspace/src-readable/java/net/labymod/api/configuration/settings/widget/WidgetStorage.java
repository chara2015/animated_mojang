package net.labymod.api.configuration.settings.widget;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/widget/WidgetStorage.class */
@FunctionalInterface
public interface WidgetStorage {
    void store(List<Class<? extends Widget>> list);
}
