package net.labymod.api.client.gui;

import net.labymod.api.client.gui.screen.Parent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/Interactable.class */
public interface Interactable extends ScreenUser, MouseUser, KeyboardUser {
    void tick();

    void initialize(Parent parent);
}
