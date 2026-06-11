package net.labymod.api.client.gui.screen.widget.util;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/util/WidgetUtil.class */
public final class WidgetUtil {
    private WidgetUtil() {
    }

    public static Widget unwrapWidget(Widget widget) {
        while (widget instanceof WrappedWidget) {
            WrappedWidget wrappedWidget = (WrappedWidget) widget;
            widget = wrappedWidget.childWidget();
        }
        return widget;
    }
}
