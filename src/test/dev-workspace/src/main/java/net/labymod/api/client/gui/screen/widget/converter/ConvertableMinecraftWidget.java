package net.labymod.api.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/ConvertableMinecraftWidget.class */
public interface ConvertableMinecraftWidget<K extends AbstractWidget<?>> {
    WidgetWatcher<K> getWatcher();
}
