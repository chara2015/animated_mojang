package net.labymod.api.client.gui;

import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/KeyboardUser.class */
public interface KeyboardUser {
    boolean keyPressed(Key key, InputType inputType);

    boolean keyReleased(Key key, InputType inputType);

    boolean charTyped(Key key, char c);

    boolean handlePreeditText(@Nullable PreeditText preeditText);
}
