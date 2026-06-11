package net.labymod.api.client.gui.screen.widget.widgets.activity.title.header;

import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/title/header/LogoWidgetAccessor.class */
public interface LogoWidgetAccessor extends Widget {
    @Nullable
    Widget getMinecraftWidget();

    @Nullable
    Widget getEditionWidget();

    @Nullable
    SplashWidgetAccessor getSplashWidget();
}
