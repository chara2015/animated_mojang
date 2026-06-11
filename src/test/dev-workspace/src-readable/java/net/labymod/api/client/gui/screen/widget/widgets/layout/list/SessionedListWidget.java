package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/list/SessionedListWidget.class */
public abstract class SessionedListWidget<T extends Widget> extends ListWidget<T> {
    protected final ListSession<T> session;

    protected SessionedListWidget(@NotNull ListSession<T> listSession) {
        Objects.requireNonNull(listSession, "ListSession cannot be null!");
        this.session = listSession;
    }

    protected SessionedListWidget() {
        this(new ListSession());
    }

    @NotNull
    public ListSession<T> listSession() {
        return this.session;
    }
}
