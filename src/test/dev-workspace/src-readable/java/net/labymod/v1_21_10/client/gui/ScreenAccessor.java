package net.labymod.v1_21_10.client.gui;

import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.gui.screen.VanillaScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/gui/ScreenAccessor.class */
public interface ScreenAccessor extends VanillaScreen {
    void setMinecraft(fzz fzzVar);

    void handleClickEvent(ClickEvent clickEvent);
}
