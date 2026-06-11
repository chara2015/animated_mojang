package net.minecraft.client.gui.screens.dialog;

import java.util.stream.Stream;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.server.dialog.ActionButton;
import net.minecraft.server.dialog.MultiActionDialog;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/dialog/MultiButtonDialogScreen.class */
public class MultiButtonDialogScreen extends ButtonListDialogScreen<MultiActionDialog> {
    public MultiButtonDialogScreen(Screen $$0, MultiActionDialog $$1, DialogConnectionAccess $$2) {
        super($$0, $$1, $$2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.gui.screens.dialog.ButtonListDialogScreen
    public Stream<ActionButton> createListActions(MultiActionDialog $$0, DialogConnectionAccess $$1) {
        return $$0.actions().stream();
    }
}
