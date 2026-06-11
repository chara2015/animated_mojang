package net.minecraft.client.gui.screens.dialog;

import java.util.Optional;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.ServerLinks;
import net.minecraft.server.dialog.Dialog;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/dialog/DialogConnectionAccess.class */
public interface DialogConnectionAccess {
    void disconnect(Component component);

    void runCommand(String str, Screen screen);

    void openDialog(Holder<Dialog> holder, Screen screen);

    void sendCustomAction(Identifier identifier, Optional<Tag> optional);

    ServerLinks serverLinks();
}
