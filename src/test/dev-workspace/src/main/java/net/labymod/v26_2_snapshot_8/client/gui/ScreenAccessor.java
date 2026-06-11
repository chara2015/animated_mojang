package net.labymod.v26_2_snapshot_8.client.gui;

import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.gui.screen.VanillaScreen;
import net.minecraft.client.Minecraft;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gui/ScreenAccessor.class */
public interface ScreenAccessor extends VanillaScreen {
    void setMinecraft(Minecraft minecraft);

    void handleClickEvent(ClickEvent clickEvent);
}
