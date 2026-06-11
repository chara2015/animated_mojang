package net.labymod.api.client.gui;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.version.VersionMultiRange;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/MouseUser.class */
public interface MouseUser {
    public static final VersionCompatibility FILE_DROP_COMPATIBILITY = new VersionMultiRange("1.17<*");

    boolean mouseReleased(MutableMouse mutableMouse, MouseButton mouseButton);

    boolean mouseClicked(MutableMouse mutableMouse, MouseButton mouseButton);

    boolean mouseScrolled(MutableMouse mutableMouse, double d);

    boolean mouseDragged(MutableMouse mutableMouse, MouseButton mouseButton, double d, double d2);

    boolean fileDropped(MutableMouse mutableMouse, List<Path> list);

    default boolean transformMouse(MutableMouse mouse, BooleanSupplier handler) {
        return handler.getAsBoolean();
    }
}
