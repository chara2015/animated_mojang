package net.labymod.api.client.scoreboard;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/scoreboard/TabList.class */
public interface TabList {
    @NotNull
    TabListHolder holder();

    @Nullable
    default Component header() {
        return holder().getHeader();
    }

    @Nullable
    default Component footer() {
        return holder().getFooter();
    }

    default boolean isVisible() {
        return holder().isVisible();
    }
}
