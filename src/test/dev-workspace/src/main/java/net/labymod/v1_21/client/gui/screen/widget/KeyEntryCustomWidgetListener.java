package net.labymod.v1_21.client.gui.screen.widget;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftKeyEntry;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/gui/screen/widget/KeyEntryCustomWidgetListener.class */
public class KeyEntryCustomWidgetListener implements fki {
    private final MinecraftKeyEntry keyEntry;
    private boolean focused;

    public KeyEntryCustomWidgetListener(MinecraftKeyEntry keyEntry) {
        this.keyEntry = keyEntry;
    }

    public void a(boolean focused) {
        this.focused = focused;
    }

    public boolean aO_() {
        return this.focused;
    }

    public boolean c(double mouseX, double mouseY) {
        return this.keyEntry.getCustomWidget().isHovered();
    }

    public boolean a(double mouseX, double mouseY, int mouseButton) {
        MouseButton button;
        Widget widget = this.keyEntry.getCustomWidget();
        if (widget.isHovered() && (button = DefaultKeyMapper.pressMouse(mouseButton)) != null) {
            return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return widget.mouseClicked(mouse, button);
            });
        }
        return false;
    }

    public boolean b(double mouseX, double mouseY, int mouseButton) {
        MouseButton button;
        Widget widget = this.keyEntry.getCustomWidget();
        if (widget.isHovered() && (button = DefaultKeyMapper.getMouseButton(mouseButton)) != null) {
            return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return widget.mouseReleased(mouse, button);
            });
        }
        return false;
    }
}
