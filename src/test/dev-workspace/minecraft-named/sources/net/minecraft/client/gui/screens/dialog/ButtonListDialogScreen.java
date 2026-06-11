package net.minecraft.client.gui.screens.dialog;

import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.server.dialog.ActionButton;
import net.minecraft.server.dialog.ButtonListDialog;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/dialog/ButtonListDialogScreen.class */
public abstract class ButtonListDialogScreen<T extends ButtonListDialog> extends DialogScreen<T> {
    public static final int FOOTER_MARGIN = 5;

    protected abstract Stream<ActionButton> createListActions(T t, DialogConnectionAccess dialogConnectionAccess);

    public ButtonListDialogScreen(Screen $$0, T $$1, DialogConnectionAccess $$2) {
        super($$0, $$1, $$2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.gui.screens.dialog.DialogScreen
    public void populateBodyElements(LinearLayout $$0, DialogControlSet $$1, T $$2, DialogConnectionAccess $$3) {
        super.populateBodyElements($$0, $$1, $$2, $$3);
        List<Button> $$4 = createListActions($$2, $$3).map($$12 -> {
            return $$1.createActionButton($$12).build();
        }).toList();
        $$0.addChild(packControlsIntoColumns($$4, $$2.columns()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.gui.screens.dialog.DialogScreen
    public void updateHeaderAndFooter(HeaderAndFooterLayout $$0, DialogControlSet $$1, T $$2, DialogConnectionAccess $$3) {
        super.updateHeaderAndFooter($$0, $$1, $$2, $$3);
        $$2.exitAction().ifPresentOrElse($$22 -> {
            $$0.addToFooter($$1.createActionButton($$22).build());
        }, () -> {
            $$0.setFooterHeight(5);
        });
    }
}
