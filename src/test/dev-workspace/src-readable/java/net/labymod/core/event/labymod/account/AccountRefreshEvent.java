package net.labymod.core.event.labymod.account;

import net.labymod.accountmanager.storage.account.Account;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/labymod/account/AccountRefreshEvent.class */
public class AccountRefreshEvent extends AccountEvent {
    public AccountRefreshEvent(Account account) {
        super(account);
    }

    public String getNewAccessToken() {
        return account().getAccessToken();
    }
}
