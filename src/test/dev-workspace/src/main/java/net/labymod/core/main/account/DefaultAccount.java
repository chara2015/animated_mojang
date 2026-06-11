package net.labymod.core.main.account;

import net.labymod.api.account.Account;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/account/DefaultAccount.class */
@Deprecated(forRemoval = true, since = "4.2.42")
public class DefaultAccount implements Account {
    private final String username;

    public DefaultAccount(String username) {
        this.username = username;
    }

    @Override // net.labymod.api.account.Account
    public String getUsername() {
        return this.username;
    }
}
