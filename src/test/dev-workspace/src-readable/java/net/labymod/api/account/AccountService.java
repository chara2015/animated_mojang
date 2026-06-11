package net.labymod.api.account;

import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/account/AccountService.class */
@Referenceable
public interface AccountService {
    @Deprecated(forRemoval = true, since = "4.2.42")
    @NotNull
    List<Account> getAccounts();
}
