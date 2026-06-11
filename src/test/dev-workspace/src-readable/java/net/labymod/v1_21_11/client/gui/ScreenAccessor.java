package net.labymod.v1_21_11.client.gui;

import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.gui.screen.VanillaScreen;
import net.minecraft.client.Minecraft;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gui/ScreenAccessor.class */
public interface ScreenAccessor extends VanillaScreen {
    void setMinecraft(Minecraft minecraft);

    void handleClickEvent(ClickEvent clickEvent);
}
