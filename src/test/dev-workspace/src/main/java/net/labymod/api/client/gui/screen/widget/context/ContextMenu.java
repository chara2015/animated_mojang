package net.labymod.api.client.gui.screen.widget.context;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.context.ContextMenuListWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.util.bounds.ModifyReason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/context/ContextMenu.class */
public class ContextMenu {
    private static final float PADDING = 1.0f;
    private static final ModifyReason DROPDOWN_POSITION = ModifyReason.of("dropdownPosition");
    private final List<ContextMenuEntry> entries = new ArrayList();
    private WidgetReference reference;
    private Object source;
    private ContextMenu subMenu;

    public void open(Object source) {
        this.source = source;
        open();
    }

    public void open() {
        if (!hasEntries()) {
            return;
        }
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Window window = minecraft.minecraftWindow();
        Mouse mouse = minecraft.absoluteMouse().copy();
        this.reference = Laby.labyAPI().screenOverlayHandler().displayInOverlay(new ContextMenuListWidget(this));
        this.reference.boundsUpdater((ref, bounds) -> {
            float x;
            float y;
            ContextMenuListWidget subMenuList = null;
            if (isSubMenu()) {
                AppliedContextMenuEntry parent = getParent();
                subMenuList = (ContextMenuListWidget) parent.contextMenu().reference.widget();
                Widget widget = subMenuList.getEntryWidget(parent.entry());
                if (widget == null) {
                    widget = subMenuList;
                }
                x = subMenuList.bounds().getRight(BoundsType.OUTER);
                y = widget.bounds().getY(BoundsType.OUTER);
            } else {
                x = mouse.getX() + PADDING;
                y = mouse.getY();
            }
            boolean dropUp = y + bounds.getHeight() > ((float) window.getScaledHeight());
            boolean dropLeft = x + bounds.getWidth() > ((float) window.getScaledWidth());
            if (isSubMenu()) {
                if (dropLeft) {
                    x = subMenuList.bounds().getLeft(BoundsType.OUTER) - bounds.getWidth();
                }
            } else {
                x -= dropLeft ? bounds.getWidth() : 0.0f;
            }
            bounds.setPosition(x, y - (dropUp ? bounds.getHeight() : 0.0f), DROPDOWN_POSITION);
        });
        this.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
        this.reference.destroyHandlers().add(this::close);
    }

    public void openSubMenu(ContextMenu subMenu, ContextMenuEntry source) {
        if (this.reference == null) {
            return;
        }
        this.subMenu = subMenu;
        this.subMenu.open(new AppliedContextMenuEntry(this, source));
        this.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.NEVER);
    }

    public void close() {
        if (this.reference != null && this.reference.isAlive()) {
            this.reference.remove();
        }
        if (isSubMenu()) {
            ContextMenu parent = getParent().contextMenu();
            parent.close();
            if (parent.reference != null) {
                parent.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
            }
        }
        if (this.subMenu != null) {
            ContextMenu subMenu = this.subMenu;
            this.subMenu = null;
            subMenu.close();
            if (this.reference != null) {
                this.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
            }
        }
    }

    public void addEntry(ContextMenuEntry entry) {
        this.entries.add(entry);
    }

    public ContextMenu with(ContextMenuEntry entry) {
        this.entries.add(entry);
        return this;
    }

    public List<ContextMenuEntry> entries() {
        return this.entries;
    }

    public boolean isOpen() {
        return this.reference != null && this.reference.isAlive();
    }

    public boolean isSubMenu() {
        return this.source instanceof AppliedContextMenuEntry;
    }

    public AppliedContextMenuEntry getParent() {
        if (isSubMenu()) {
            return (AppliedContextMenuEntry) this.source;
        }
        return null;
    }

    public boolean hasEntries() {
        return !this.entries.isEmpty();
    }

    public Object getSource() {
        return this.source;
    }
}
