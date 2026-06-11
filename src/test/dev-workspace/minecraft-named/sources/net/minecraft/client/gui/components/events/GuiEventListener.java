package net.minecraft.client.gui.components.events;

import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.components.TabOrderedElement;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/events/GuiEventListener.class */
public interface GuiEventListener extends TabOrderedElement {
    void setFocused(boolean z);

    boolean isFocused();

    default void mouseMoved(double $$0, double $$1) {
    }

    default boolean mouseClicked(MouseButtonEvent $$0, boolean $$1) {
        return false;
    }

    default boolean mouseReleased(MouseButtonEvent $$0) {
        return false;
    }

    default boolean mouseDragged(MouseButtonEvent $$0, double $$1, double $$2) {
        return false;
    }

    default boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
        return false;
    }

    default boolean keyPressed(KeyEvent $$0) {
        return false;
    }

    default boolean keyReleased(KeyEvent $$0) {
        return false;
    }

    default boolean charTyped(CharacterEvent $$0) {
        return false;
    }

    default ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
        return null;
    }

    default boolean isMouseOver(double $$0, double $$1) {
        return false;
    }

    default boolean shouldTakeFocusAfterInteraction() {
        return true;
    }

    default ComponentPath getCurrentFocusPath() {
        if (isFocused()) {
            return ComponentPath.leaf(this);
        }
        return null;
    }

    default ScreenRectangle getRectangle() {
        return ScreenRectangle.empty();
    }

    default ScreenRectangle getBorderForArrowNavigation(ScreenDirection $$0) {
        return getRectangle().getBorder($$0);
    }
}
