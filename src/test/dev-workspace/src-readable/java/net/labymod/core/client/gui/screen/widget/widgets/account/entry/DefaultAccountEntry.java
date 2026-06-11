package net.labymod.core.client.gui.screen.widget.widgets.account.entry;

import java.util.UUID;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/account/entry/DefaultAccountEntry.class */
public class DefaultAccountEntry implements AccountEntry {
    private final Account account;

    private DefaultAccountEntry(Account account) {
        this.account = account;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry
    public Component getComponent() {
        return Component.text(this.account.getUsername());
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry
    public Icon getIcon() {
        return Icon.head(this.account.getUUID());
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry
    public void interact(Runnable callback) {
        SessionAccessor sessionAccessor = Laby.labyAPI().minecraft().sessionAccessor();
        ((DefaultAbstractSessionAccessor) sessionAccessor).updateSession(this.account);
    }

    public Account account() {
        return this.account;
    }

    public static DefaultAccountEntry of(Account account) {
        return new DefaultAccountEntry(account);
    }

    public static DefaultAccountEntry of(UUID uniqueId, String username, String accessToken) {
        return of((Account) new ExternalAccount(uniqueId, username, accessToken));
    }

    public static AccountEntry[] of(Account[] accounts) {
        AccountEntry[] entries = new AccountEntry[accounts.length];
        for (int i = 0; i < accounts.length; i++) {
            entries[i] = of(accounts[i]);
        }
        return entries;
    }
}
