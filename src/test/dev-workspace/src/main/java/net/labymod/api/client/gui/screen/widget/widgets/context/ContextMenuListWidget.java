package net.labymod.api.client.gui.screen.widget.widgets.context;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuHandler;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.sound.SoundType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/context/ContextMenuListWidget.class */
@AutoWidget
@Link("activity/overlay/context/context.lss")
public class ContextMenuListWidget extends VerticalListWidget<ContextMenuEntryWidget> {
    private final ContextMenu contextMenu;

    public ContextMenuListWidget(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
        setSelectCallback(widget -> {
            if (widget.isDisabledOnInitialize()) {
                return;
            }
            ContextMenuEntry entry = widget.entry();
            ContextMenuHandler handler = entry.clickHandler();
            if (handler != null && handler.handle(entry)) {
                Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                contextMenu.close();
                return;
            }
            ContextMenu subMenu = entry.getSubMenu();
            if (subMenu != null) {
                Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                contextMenu.openSubMenu(subMenu, entry);
            }
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        for (ContextMenuEntry entry : this.contextMenu.entries()) {
            addChild(new ContextMenuEntryWidget(entry));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (mouseButton != MouseButton.LEFT) {
            return true;
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        playAnimation("fade-in");
    }

    public ContextMenuEntryWidget getEntryWidget(ContextMenuEntry entry) {
        for (T child : getChildren()) {
            if (child.entry() == entry) {
                return child;
            }
        }
        return null;
    }
}
