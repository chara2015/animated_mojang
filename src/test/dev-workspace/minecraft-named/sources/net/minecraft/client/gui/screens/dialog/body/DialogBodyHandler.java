package net.minecraft.client.gui.screens.dialog.body;

import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.dialog.DialogScreen;
import net.minecraft.server.dialog.body.DialogBody;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/dialog/body/DialogBodyHandler.class */
public interface DialogBodyHandler<T extends DialogBody> {
    LayoutElement createControls(DialogScreen<?> dialogScreen, T t);
}
