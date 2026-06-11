package net.labymod.api.client.component.event;

import java.net.URL;
import java.util.Objects;
import net.labymod.api.client.component.ComponentService;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/event/ClickEvent.class */
public interface ClickEvent {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/event/ClickEvent$Action.class */
    public enum Action {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        SHOW_DIALOG,
        CHANGE_PAGE,
        COPY_TO_CLIPBOARD,
        CUSTOM
    }

    Action action();

    String getValue();

    static ClickEvent openUrl(@NotNull String url) {
        return ComponentService.clickEvent(Action.OPEN_URL, url);
    }

    static ClickEvent openUrl(@NotNull URL url) {
        Objects.requireNonNull(url, "URL cannot be null!");
        return openUrl(url.toExternalForm());
    }

    static ClickEvent openFile(@NotNull String path) {
        return ComponentService.clickEvent(Action.OPEN_FILE, path);
    }

    static ClickEvent runCommand(@NotNull String command) {
        return ComponentService.clickEvent(Action.RUN_COMMAND, command);
    }

    static ClickEvent suggestCommand(@NotNull String command) {
        return ComponentService.clickEvent(Action.SUGGEST_COMMAND, command);
    }

    static ClickEvent changePage(@NotNull String page) {
        return ComponentService.clickEvent(Action.CHANGE_PAGE, page);
    }

    static ClickEvent changePage(int page) {
        return changePage(String.valueOf(page));
    }

    static ClickEvent copyToClipboard(@NotNull String text) {
        return ComponentService.clickEvent(Action.COPY_TO_CLIPBOARD, text);
    }
}
