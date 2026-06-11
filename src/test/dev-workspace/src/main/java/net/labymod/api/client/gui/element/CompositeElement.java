package net.labymod.api.client.gui.element;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.gui.element.Element;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/element/CompositeElement.class */
public interface CompositeElement<E extends Element> extends Element {
    public static final Comparator<? super Element> ELEMENT_COMPARATOR = (a, b) -> {
        return 0;
    };

    List<? extends E> getChildren();

    default void sortChildren() {
        for (E element : getChildren()) {
            if (element instanceof CompositeElement) {
                ((CompositeElement) element).sortChildren();
            }
        }
        getChildren().sort(ELEMENT_COMPARATOR);
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    default boolean keyPressed(Key key, InputType type) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; i--) {
            E element = elements.get(i);
            if (element.isVisible() && element.isInteractable() && element.keyPressed(key, type)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    default boolean keyReleased(Key key, InputType type) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; i--) {
            E element = elements.get(i);
            if (element.isVisible() && element.isInteractable() && element.keyReleased(key, type)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    default boolean charTyped(Key key, char character) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; i--) {
            E element = elements.get(i);
            if (element.isVisible() && element.isInteractable() && element.charTyped(key, character)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    default boolean handlePreeditText(@Nullable PreeditText text) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; i--) {
            E element = elements.get(i);
            if (element.isVisible() && element.isInteractable() && element.handlePreeditText(text)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.Interactable
    default void tick() {
        for (E child : getChildren()) {
            if (child.isTicking()) {
                child.tick();
            }
        }
    }

    @Override // net.labymod.api.client.gui.MouseUser
    default boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; i--) {
            E element = elements.get(i);
            if (element.isInteractable()) {
                element.setDragging(false);
                element.transformMouse(mouse, () -> {
                    return element.mouseReleased(mouse, mouseButton);
                });
            }
        }
        return false;
    }

    default boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; i--) {
            E element = elements.get(i);
            if ((element.isHovered() || element.isInteractableOutside()) && element.isInteractable()) {
                if (mouseButton == MouseButton.LEFT) {
                    element.setDragging(true);
                }
                if (element.transformMouse(mouse, () -> {
                    return element.mouseClicked(mouse, mouseButton);
                })) {
                    return true;
                }
            }
        }
        return false;
    }

    default boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0; i--) {
            E element = elements.get(i);
            if ((element.isHovered() || element.isInteractableOutside()) && element.isInteractable() && element.transformMouse(mouse, () -> {
                return element.mouseScrolled(mouse, scrollDelta);
            })) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    default boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0; i--) {
            E element = elements.get(i);
            if (element.isInteractable() && element.transformMouse(mouse, () -> {
                return element.mouseDragged(mouse, button, deltaX, deltaY);
            })) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    default boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        List<? extends E> elements = getChildren();
        for (int i = elements.size() - 1; i >= 0; i--) {
            E element = elements.get(i);
            if ((element.isHovered() || element.isInteractableOutside()) && element.isInteractable() && element.transformMouse(mouse, () -> {
                return element.fileDropped(mouse, paths);
            })) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.ScreenUser
    default void doScreenAction(String action, Map<String, Object> parameters) {
        for (E child : getChildren()) {
            child.doScreenAction(action, parameters);
        }
    }
}
