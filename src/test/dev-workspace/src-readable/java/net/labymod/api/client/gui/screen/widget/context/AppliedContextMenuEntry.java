package net.labymod.api.client.gui.screen.widget.context;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/context/AppliedContextMenuEntry.class */
public class AppliedContextMenuEntry {
    private final ContextMenu contextMenu;
    private final ContextMenuEntry entry;

    public AppliedContextMenuEntry(@NotNull ContextMenu contextMenu, @NotNull ContextMenuEntry entry) {
        this.contextMenu = contextMenu;
        this.entry = entry;
    }

    @NotNull
    public ContextMenu contextMenu() {
        return this.contextMenu;
    }

    @NotNull
    public ContextMenuEntry entry() {
        return this.entry;
    }
}
