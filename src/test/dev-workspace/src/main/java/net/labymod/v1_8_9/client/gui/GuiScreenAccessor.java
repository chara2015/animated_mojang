package net.labymod.v1_8_9.client.gui;

import net.labymod.api.client.gui.screen.VanillaScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/GuiScreenAccessor.class */
public interface GuiScreenAccessor extends VanillaScreen {
    void setMinecraft(ave aveVar);

    boolean isHandleMouseInput();

    void setHandleMouseInput(boolean z);

    void resize(int i, int i2);
}
