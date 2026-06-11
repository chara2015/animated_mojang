package net.labymod.api.client.options;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/options/MinecraftInputMapping.class */
public interface MinecraftInputMapping {
    boolean isDown();

    String getName();

    String getCategory();

    @NotNull
    Key key();

    boolean isMouse();

    void unpress();

    void press();

    int getKeyCode();

    boolean isActuallyDown();

    boolean isHidden();

    Widget getReplacement();

    boolean hasReplacement();

    static boolean isHiddenOrReplaced(MinecraftInputMapping mapping) {
        return mapping.isHidden() || mapping.hasReplacement();
    }

    default void addCategory(String category) {
    }
}
