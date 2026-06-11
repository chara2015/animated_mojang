package net.labymod.api.event.client.gui.screen.tree;

import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/tree/ScreenTreeTopHandler.class */
public interface ScreenTreeTopHandler {
    default void mouseClicked(ScreenPhase phase, MouseUser screen, MutableMouse mouse, MouseButton mouseButton) {
    }

    default void mouseReleased(ScreenPhase phase, MouseUser screen, MutableMouse mouse, MouseButton mouseButton) {
    }

    default void mouseScrolled(ScreenPhase phase, MouseUser screen, MutableMouse mouse, double scrollDelta) {
    }

    default void mouseDragged(ScreenPhase phase, MouseUser screen, MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
    }

    default void fileDropped(ScreenPhase phase, MouseUser screen, MutableMouse mouse, List<Path> paths) {
    }

    default void keyPressed(ScreenPhase phase, KeyboardUser screen, Key key, InputType type) {
    }

    default void keyReleased(ScreenPhase phase, KeyboardUser screen, Key key, InputType type) {
    }

    default void charTyped(ScreenPhase phase, KeyboardUser screen, Key key, char character) {
    }
}
