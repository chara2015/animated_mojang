package net.labymod.v1_8_9.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/screen/LabyScreenRendererAccessor.class */
public interface LabyScreenRendererAccessor extends FileDropHandler {
    boolean wrappedMouseClicked(MutableMouse mutableMouse, int i);

    boolean wrappedMouseReleased(MutableMouse mutableMouse, int i);

    boolean wrappedKeyPressed(Key key, InputType inputType);

    boolean wrappedKeyReleased(Key key, InputType inputType);

    boolean wrappedCharTyped(Key key, char c);

    boolean mouseScrolled(MutableMouse mutableMouse, double d);

    boolean wrappedMouseClickMove(MutableMouse mutableMouse, int i, double d, double d2);

    @Override // net.labymod.core.client.gui.screen.accessor.FileDropHandler
    default boolean handleDroppedFiles(MutableMouse mouse, List<Path> paths) {
        return false;
    }
}
