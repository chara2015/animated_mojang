package net.labymod.core.event.labymod.account;

import net.labymod.accountmanager.storage.account.Account;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/labymod/account/AccountEvent.class */
public class AccountEvent implements Event {
    private final Account account;

    public AccountEvent(Account account) {
        this.account = account;
    }

    public Account account() {
        return this.account;
    }
}
