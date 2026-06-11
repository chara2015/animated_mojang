package net.labymod.core.client.gui.navigation.elements;

import java.util.Objects;
import java.util.UUID;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.accountmanager.storage.StorageType;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.UUIDHelper;
import net.labymod.core.client.gui.screen.activity.activities.account.AccountManagerActivity;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.DefaultAccountEntry;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.ManageEntry;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.renderer.AccountEntryRenderer;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/elements/AccountNavigationElement.class */
public class AccountNavigationElement extends ScreenBaseNavigationElement<DropdownWidget<?>> {
    public AccountNavigationElement() {
        super(new AccountManagerActivity());
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public String getWidgetId() {
        return "account";
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Icon getIcon() {
        return null;
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Component getDisplayName() {
        return null;
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement, net.labymod.api.client.gui.navigation.NavigationElement
    public DropdownWidget<?> createWidget(NavigationWrapper wrapper) {
        SessionAccessor sessionAccessor = Laby.labyAPI().minecraft().sessionAccessor();
        Session session = sessionAccessor.getSession();
        String username = session == null ? "Account" : session.getUsername();
        UUID uniqueId = session == null ? UUIDHelper.createUniqueId(username) : session.getUniqueId();
        String accessToken = session == null ? "" : session.getAccessToken();
        DropdownWidget<AccountEntry> dropdownWidget = new DropdownWidget<>();
        dropdownWidget.addId("account-dropdown");
        dropdownWidget.setEntryRenderer(new AccountEntryRenderer());
        dropdownWidget.setSelected(DefaultAccountEntry.of(uniqueId, username, accessToken));
        dropdownWidget.setChangeListener(entry -> {
            Objects.requireNonNull(wrapper);
            entry.interact(wrapper::reload);
        });
        AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        for (Account account : accountManager.getAccounts()) {
            if (isDisplayableAccount(account)) {
                dropdownWidget.add(DefaultAccountEntry.of(account));
            }
        }
        dropdownWidget.add(new ManageEntry());
        return dropdownWidget;
    }

    @Subscribe(64)
    public void onSessionUpdate(SessionUpdateEvent event) {
        ThreadSafe.executeOnRenderThread(this::updateWidget);
    }

    public static boolean isDisplayableAccount(Account account) {
        return account.isMicrosoft() ? (account.getStorageType() != StorageType.EXTERNAL || account.getSessionState() == AccountSessionState.EXPIRED || account.getSessionState() == AccountSessionState.UNKNOWN || account.getSessionState() == AccountSessionState.OFFLINE) ? false : true : account.getSessionState() == AccountSessionState.OFFLINE;
    }
}
