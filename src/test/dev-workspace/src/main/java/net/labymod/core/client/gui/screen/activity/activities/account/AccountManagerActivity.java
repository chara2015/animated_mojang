package net.labymod.core.client.gui.screen.activity.activities.account;

import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.client.gui.screen.widget.widgets.account.AccountWidget;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.quicklauncher.QuickLauncher;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/account/AccountManagerActivity.class */
@Link("activity/account/account-manager.lss")
@AutoActivity
public class AccountManagerActivity extends SimpleActivity {
    private static final ListSession<AccountWidget> ACCOUNT_LIST_SESSION = new ListSession<>((a, b) -> {
        return a.account().getUUID().equals(b.account().getUUID()) && b.account().getAccessToken().equals(b.account().getAccessToken());
    });
    private VerticalListWidget<AccountWidget> listWidget;
    private ButtonWidget addButton;
    private ButtonWidget switchButton;
    private ButtonWidget deleteButton;
    private ButtonWidget shortcutButton;
    private ButtonWidget refreshButton;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget headerWidget = new DivWidget();
        headerWidget.addId("header");
        ((Document) this.document).addChild(headerWidget);
        DivWidget containerWidget = new DivWidget();
        containerWidget.addId("account-container");
        this.listWidget = new VerticalListWidget<>(ACCOUNT_LIST_SESSION);
        this.listWidget.addId("account-list");
        this.listWidget.setSelectCallback(value -> {
            updateButtonStates();
        });
        this.listWidget.setDoubleClickCallback(value2 -> {
            switchAccount();
            displayPreviousScreen();
        });
        AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        for (Account account : accountManager.getAccounts()) {
            if (account instanceof ExternalAccount) {
                AccountWidget accountWidget = new AccountWidget(account);
                accountWidget.addId("account");
                accountWidget.createContextMenu().with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.switch", new Component[0])).clickHandler(entry -> {
                    switchAccount(account);
                    return true;
                }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.delete", new Component[0])).clickHandler(entry2 -> {
                    removeAccount(account);
                    return true;
                }).build());
                this.listWidget.addChild(accountWidget);
            }
        }
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) this.listWidget);
        scrollWidget.addId("account-list-scroll");
        containerWidget.addChild(scrollWidget);
        ((Document) this.document).addChild(containerWidget);
        DivWidget footerWidget = new DivWidget();
        footerWidget.addId("footer");
        HorizontalListWidget actionListWidget = new HorizontalListWidget();
        actionListWidget.addId("action-buttons");
        this.addButton = ButtonWidget.i18n("labymod.ui.button.add");
        this.addButton.addId("add-account");
        this.addButton.setPressable(this::addAccount);
        actionListWidget.addEntry(this.addButton);
        this.switchButton = ButtonWidget.i18n("labymod.ui.button.switch");
        this.switchButton.addId("switch-account");
        this.switchButton.setPressable(this::switchAccount);
        actionListWidget.addEntry(this.switchButton);
        this.deleteButton = ButtonWidget.i18n("labymod.ui.button.delete");
        this.deleteButton.addId("delete-account");
        this.deleteButton.setPressable(this::removeAccount);
        actionListWidget.addEntry(this.deleteButton);
        this.shortcutButton = ButtonWidget.icon(Textures.SpriteCommon.EXPORT);
        this.shortcutButton.addId("shortcut-account");
        this.shortcutButton.setPressable(this::createShortcut);
        this.refreshButton = ButtonWidget.icon(Textures.SpriteCommon.REFRESH);
        this.refreshButton.setHoverComponent(Component.translatable("labymod.activity.accountManager.refresh.description", new Component[0]));
        this.refreshButton.addId("refresh-sessions");
        this.refreshButton.setPressable(() -> {
            AsyncAccountManager accountManager2 = LabyMod.getInstance().getAccountManager();
            this.refreshButton.setEnabled(false);
            accountManager2.refreshExternalSessionsAsync(account2 -> {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                }
            }, new Runnable[]{() -> {
                this.refreshButton.setEnabled(true);
                ThreadSafe.executeOnRenderThread(this::reload);
            }});
        });
        actionListWidget.addEntry(this.refreshButton);
        footerWidget.addChild(actionListWidget);
        ((Document) this.document).addChild(footerWidget);
        updateButtonStates();
    }

    private void updateButtonStates() {
        String str;
        TranslatableComponent translatableComponentTranslatable;
        AccountWidget selectedEntry = this.listWidget.listSession().getSelectedEntry();
        QuickLauncher launcher = LabyMod.references().quickLauncher();
        this.addButton.setEnabled(true);
        this.switchButton.setEnabled(selectedEntry != null);
        this.shortcutButton.setEnabled(selectedEntry != null && launcher.iSupported());
        this.deleteButton.setEnabled(selectedEntry != null);
        ButtonWidget buttonWidget = this.shortcutButton;
        if (selectedEntry == null) {
            translatableComponentTranslatable = null;
        } else {
            if (launcher.iSupported()) {
                str = "labymod.activity.accountManager.export.description";
            } else {
                str = "labymod.activity.accountManager.export.notSupported";
            }
            translatableComponentTranslatable = Component.translatable(str, new Component[0]);
        }
        buttonWidget.setHoverComponent(translatableComponentTranslatable);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        return super.keyPressed(key, type);
    }

    private void addAccount() {
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new MicrosoftLoginActivity(newAccount -> {
            reload();
        }));
    }

    private void switchAccount() {
        AccountWidget entry = this.listWidget.listSession().getSelectedEntry();
        if (entry == null) {
            return;
        }
        switchAccount(entry.account());
    }

    private void switchAccount(Account account) {
        ((DefaultAbstractSessionAccessor) Laby.labyAPI().minecraft().sessionAccessor()).updateSession(account);
        reload();
    }

    private void createShortcut() {
        AccountWidget entry = this.listWidget.listSession().getSelectedEntry();
        if (entry == null) {
            return;
        }
        QuickLauncher launcher = LabyMod.references().quickLauncher();
        if (!launcher.iSupported()) {
            return;
        }
        launcher.createAsync(entry.account());
        this.shortcutButton.setEnabled(false);
    }

    private void removeAccount() {
        AccountWidget entry = this.listWidget.listSession().getSelectedEntry();
        if (entry == null) {
            return;
        }
        removeAccount(entry.account());
    }

    private void removeAccount(Account account) {
        AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        accountManager.getStorage().removeAccount((ExternalAccount) account);
        accountManager.saveAsync(new Runnable[0]);
        reload();
    }
}
