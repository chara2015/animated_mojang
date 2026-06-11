package net.labymod.core.main.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Singleton;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.accountmanager.authentication.microsoft.azure.Azure;
import net.labymod.accountmanager.authentication.microsoft.oauth.OAuthServer;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import net.labymod.accountmanager.storage.loader.external.ExternalAccountStorage;
import net.labymod.api.Laby;
import net.labymod.api.account.Account;
import net.labymod.api.account.AccountService;
import net.labymod.api.client.session.Session;
import net.labymod.api.models.Implements;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.core.event.labymod.account.AccountRefreshEvent;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/account/AccountManagerController.class */
@Singleton
@Implements(AccountService.class)
public class AccountManagerController implements AccountService {
    private static final Logging LOGGER = Logging.getLogger();
    private static final String CLIENT_ID = "27843883-6e3b-42cb-9e51-4f55a700601e";
    private static final String SCOPE = "XboxLive.signin%20offline_access";
    public static final Azure AZURE = new Azure(CLIENT_ID, OAuthServer.REDIRECT_URL, SCOPE);
    private AsyncAccountManager accountManager;
    private final Task updateTask = Task.builder(() -> {
        try {
            onUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }).delay(1, TimeUnit.HOURS).repeat(1, TimeUnit.HOURS).build();
    private final DefaultAbstractSessionAccessor<?> sessionAccessor = (DefaultAbstractSessionAccessor) LabyMod.references().sessionAccessor();

    public void initialize(AsyncAccountManager accountManager) {
        this.accountManager = accountManager;
        LOGGER.info("Loading accounts...", new Object[0]);
        this.accountManager.loadAsync(AZURE, new Runnable[]{() -> {
            LOGGER.info("Loaded all accounts. Refreshing external sessions...", new Object[0]);
            this.accountManager.refreshExternalSessions();
            LOGGER.info("External sessions refreshed.", new Object[0]);
            try {
                this.accountManager.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
            schedule();
        }});
    }

    private void schedule() {
        if (this.updateTask != null) {
            this.updateTask.execute();
        }
    }

    private void onUpdate() throws Exception {
        Session activeSession = this.sessionAccessor.getSession();
        ExternalAccountStorage storage = this.accountManager.getStorage();
        if (storage.hasFileChanged()) {
            storage.reload();
            storage.refreshAccounts(account -> {
            });
        }
        AtomicBoolean changed = new AtomicBoolean(false);
        storage.refreshJustExpiredAccounts(account2 -> {
            if (account2.getSessionState() != AccountSessionState.VALID) {
                return;
            }
            boolean isCurrentSession = activeSession != null && account2.getUUID().equals(activeSession.getUniqueId());
            if (isCurrentSession) {
                this.sessionAccessor.updateSession(account2);
            }
            Laby.fireEvent(new AccountRefreshEvent(account2));
            changed.set(true);
        });
        if (changed.get()) {
            this.accountManager.save();
        }
    }

    @Override // net.labymod.api.account.AccountService
    @Deprecated(forRemoval = true, since = "4.2.42")
    @NotNull
    public List<Account> getAccounts() {
        if (this.accountManager == null) {
            return Collections.emptyList();
        }
        List<Account> accounts = new ArrayList<>();
        for (net.labymod.accountmanager.storage.account.Account account : this.accountManager.getAccounts()) {
            accounts.add(new DefaultAccount(account.getUsername()));
        }
        return accounts;
    }
}
