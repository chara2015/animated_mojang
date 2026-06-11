package net.labymod.core.client.gui.screen.widget.widgets.account.entry.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import net.labymod.core.client.gui.screen.widget.widgets.account.AccountPopupEntryWidget;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/account/entry/renderer/AccountEntryRenderer.class */
public class AccountEntryRenderer implements EntryRenderer<AccountEntry> {
    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer
    public float getWidth(AccountEntry entry, float maxWidth) {
        return Laby.labyAPI().renderPipeline().componentRenderer().width(entry.getComponent());
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer
    public float getHeight(AccountEntry entry, float maxWidth) {
        return 15.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer
    @NotNull
    public Widget createEntryWidget(AccountEntry entry) {
        return new AccountPopupEntryWidget(entry);
    }
}
