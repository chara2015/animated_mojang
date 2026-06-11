package net.labymod.v26_1.client.gui.screen.widget;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftKeyEntry;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/screen/widget/KeyEntryCustomWidgetListener.class */
public class KeyEntryCustomWidgetListener implements GuiEventListener {
    private final MinecraftKeyEntry keyEntry;
    private boolean focused;

    public KeyEntryCustomWidgetListener(MinecraftKeyEntry keyEntry) {
        this.keyEntry = keyEntry;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.keyEntry.getCustomWidget().isHovered();
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubleClick) {
        Widget widget = this.keyEntry.getCustomWidget();
        if (!widget.isHovered()) {
            return false;
        }
        double mouseX = event.x();
        double mouseY = event.y();
        int mouseButton = event.button();
        MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
        if (button != null) {
            return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return widget.mouseClicked(mouse, button);
            });
        }
        return false;
    }

    public boolean mouseReleased(@NotNull MouseButtonEvent event) {
        Widget widget = this.keyEntry.getCustomWidget();
        if (!widget.isHovered()) {
            return false;
        }
        double mouseX = event.x();
        double mouseY = event.y();
        int mouseButton = event.button();
        MouseButton button = DefaultKeyMapper.getMouseButton(mouseButton);
        if (button != null) {
            return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return widget.mouseReleased(mouse, button);
            });
        }
        return false;
    }
}
