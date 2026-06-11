package net.labymod.api.client.scoreboard;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/scoreboard/TabListHolder.class */
public interface TabListHolder {
    @Nullable
    Component getHeader();

    @Nullable
    Component getFooter();

    boolean isVisible();
}
