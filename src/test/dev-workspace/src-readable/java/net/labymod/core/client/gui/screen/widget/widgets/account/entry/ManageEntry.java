package net.labymod.core.client.gui.screen.widget.widgets.account.entry;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.core.client.gui.screen.activity.activities.account.AccountManagerActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/account/entry/ManageEntry.class */
public class ManageEntry implements AccountEntry {
    @Override // net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry
    public Component getComponent() {
        return Component.translatable("labymod.activity.accountManager.button.manage", new Component[0]);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry
    public Icon getIcon() {
        return Textures.SpriteCommon.SETTINGS;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry
    public void interact(Runnable callback) {
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new AccountManagerActivity());
    }
}
