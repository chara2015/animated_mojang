package net.labymod.api.client.gui.screen.widget.window;

import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/window/ScreenWindowManagement.class */
@Referenceable
@Deprecated
public interface ScreenWindowManagement extends MouseUser, KeyboardUser {
    void registerOverlay(AbstractScreenWindowOverlay abstractScreenWindowOverlay);

    void widgetPreInitialize(Widget widget, Parent parent);

    void renderWidgetOverlay(ScreenContext screenContext, Widget widget);

    void preRenderActivity(ScreenContext screenContext, Activity activity);

    <T extends AbstractScreenWindowOverlay> T overlay(Class<T> cls);
}
