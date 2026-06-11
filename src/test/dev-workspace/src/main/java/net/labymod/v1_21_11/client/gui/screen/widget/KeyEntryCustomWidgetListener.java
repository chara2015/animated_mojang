package net.labymod.v1_21_11.client.gui.screen.widget;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftKeyEntry;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gui/screen/widget/KeyEntryCustomWidgetListener.class */
public class KeyEntryCustomWidgetListener implements gmm {
    private final MinecraftKeyEntry keyEntry;
    private boolean focused;

    public KeyEntryCustomWidgetListener(MinecraftKeyEntry keyEntry) {
        this.keyEntry = keyEntry;
    }

    public void b(boolean focused) {
        this.focused = focused;
    }

    public boolean aP_() {
        return this.focused;
    }

    public boolean a_(double mouseX, double mouseY) {
        return this.keyEntry.getCustomWidget().isHovered();
    }

    public boolean a(@NotNull gzc event, boolean doubleClick) {
        Widget widget = this.keyEntry.getCustomWidget();
        if (!widget.isHovered()) {
            return false;
        }
        double mouseX = event.u();
        double mouseY = event.v();
        int mouseButton = event.t();
        MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
        if (button != null) {
            return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return widget.mouseClicked(mouse, button);
            });
        }
        return false;
    }

    public boolean b(@NotNull gzc event) {
        Widget widget = this.keyEntry.getCustomWidget();
        if (!widget.isHovered()) {
            return false;
        }
        double mouseX = event.u();
        double mouseY = event.v();
        int mouseButton = event.t();
        MouseButton button = DefaultKeyMapper.getMouseButton(mouseButton);
        if (button != null) {
            return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return widget.mouseReleased(mouse, button);
            });
        }
        return false;
    }
}
