package net.labymod.core.client.gui.screen.activity.activities.account;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.UUID;
import java.util.function.Consumer;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.accountmanager.authentication.AsyncAccountAuthentication;
import net.labymod.accountmanager.authentication.microsoft.oauth.OAuthServer;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import net.labymod.accountmanager.storage.loader.external.ExternalAccountStorage;
import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.util.TextFormat;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.account.AccountManagerController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/account/MicrosoftLoginActivity.class */
@Link("activity/account/microsoft-login.lss")
@AutoActivity
public class MicrosoftLoginActivity extends SimpleActivity {
    protected final Consumer<Account> callback;
    private Component lastStatusMessage = Component.translatable("labymod.activity.accountManager.login.state.open", Style.style(NamedTextColor.GREEN));
    private OAuthServer server;

    public MicrosoftLoginActivity(Consumer<Account> callback) {
        this.callback = callback;
        try {
            this.server = new OAuthServer(AccountManagerController.AZURE);
            restrictOAuthServerToLoopback(this.server);
            this.labyAPI.minecraft().chatExecutor().openUrl(this.server.getUrl().toString());
            this.server.listenForCodeAsync(code -> {
                if (code == null) {
                    this.updateStatusMessage(Component.translatable("labymod.activity.accountManager.login.state.denied", Style.style(NamedTextColor.YELLOW)));
                } else {
                    this.authUsingCode(code);
                }
            });
        } catch (Exception exception) {
            updateStatusMessage(Component.text(exception.getMessage(), NamedTextColor.RED));
            LOGGER.warn("Error while listening for the microsoft account code", new Object[0]);
            exception.printStackTrace();
        }
    }

    private static void restrictOAuthServerToLoopback(OAuthServer server) throws Exception {
        Field field = OAuthServer.class.getDeclaredField("serverSocket");
        field.setAccessible(true);
        ServerSocket wildcard = (ServerSocket) field.get(server);
        if (wildcard != null && !wildcard.isClosed()) {
            wildcard.close();
        }
        ServerSocket loopback = new ServerSocket();
        loopback.setReuseAddress(true);
        loopback.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8086), 50);
        field.set(server, loopback);
    }

    private void updateStatusMessage(Component message) {
        this.lastStatusMessage = message;
        this.labyAPI.minecraft().executeOnRenderThread(this::reload);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VerticalListWidget<Widget> container = new VerticalListWidget<>();
        container.addId("microsoft-login-container");
        HorizontalListWidget logo = new HorizontalListWidget();
        logo.addId("microsoft-logo");
        IconWidget iconWidget = new IconWidget(Textures.SpriteAccountManager.MICROSOFT_LOGO);
        iconWidget.addId("microsoft-icon");
        logo.addEntry(iconWidget);
        ComponentWidget nameWidget = ComponentWidget.component(Component.text("Microsoft"));
        nameWidget.addId("microsoft-text");
        logo.addEntry(nameWidget);
        container.addChild(logo);
        ComponentWidget statusWidget = ComponentWidget.component(this.lastStatusMessage);
        statusWidget.addId("microsoft-status");
        container.addChild(statusWidget);
        DivWidget buttonWrapper = new DivWidget();
        buttonWrapper.addId("button-wrapper");
        ButtonWidget copyButton = ButtonWidget.icon(Textures.SpriteCommon.COPY, () -> {
            if (this.server != null) {
                try {
                    this.labyAPI.minecraft().setClipboard(this.server.getUrl().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        copyButton.addId("button-copy");
        copyButton.setHoverComponent(Component.translatable("labymod.activity.accountManager.login.copy", new Component[0]));
        buttonWrapper.addChild(copyButton);
        ButtonWidget buttonCancel = ButtonWidget.i18n("labymod.ui.button.cancel", this::displayPreviousScreen);
        buttonCancel.addId("button-cancel");
        buttonWrapper.addChild(buttonCancel);
        ButtonWidget offlineButton = ButtonWidget.icon(Textures.SpriteCommon.DISCONNECT, () -> {
            TextFieldWidget textFieldWidget = new TextFieldWidget();
            textFieldWidget.maximalLength(19);
            PopupWidget widget = PopupWidget.builder().title(Component.translatable("labymod.activity.accountManager.login.offline.title", new Component[0])).text(Component.translatable("labymod.activity.accountManager.login.offline.prompt", new Component[0])).confirmComponent(Component.translatable("labymod.ui.button.add", new Component[0])).widgetSupplier(() -> {
                textFieldWidget.placeholder(Component.translatable("labymod.ui.textfield.username", new Component[0]));
                return textFieldWidget;
            }).confirmCallback(() -> {
                String username = textFieldWidget.getText();
                submitUsername(username);
            }).build();
            widget.displayInOverlay();
        });
        offlineButton.addId("button-offline");
        offlineButton.setHoverComponent(Component.translatable("labymod.activity.accountManager.login.offline.tooltip", new Component[0]));
        buttonWrapper.addChild(offlineButton);
        container.addChild(buttonWrapper);
        ((Document) this.document).addChild(container);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        if (this.server != null) {
            this.server.close();
        }
        super.onCloseScreen();
    }

    private void authUsingCode(String code) {
        AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        ExternalAccountStorage externalStorage = accountManager.getStorage();
        AsyncAccountAuthentication auth = externalStorage.getAuthentication();
        auth.setStateCallback(state -> {
            String stateId = TextFormat.SNAKE_CASE.toCamelCase(state.name(), true);
            Component component = Component.translatable("labymod.activity.accountManager.login.state." + stateId, new Component[0]);
            updateStatusMessage(component.style(Style.style(NamedTextColor.YELLOW)));
        });
        auth.authenticateMicrosoftAsync(code, account -> {
            try {
                submitAccount(account);
            } catch (Exception e) {
                LOGGER.warn("Error while submitting microsoft account", new Object[0]);
                updateStatusMessage(Component.text(e.getMessage(), NamedTextColor.RED));
            }
        }, e -> {
            LOGGER.warn("Error while authenticating microsoft account", new Object[0]);
            e.printStackTrace();
            updateStatusMessage(Component.text(e.getMessage(), NamedTextColor.RED));
        });
    }

    private void submitUsername(String username) {
        Laby.references().labyNetController().loadUniqueIdByName(username, result -> {
            UUID uniqueId = (UUID) result.getOrDefault(UUID.randomUUID());
            ExternalAccount account = new ExternalAccount(uniqueId, username, "0");
            account.setSessionState(AccountSessionState.OFFLINE);
            submitAccount(account);
        });
    }

    protected void submitAccount(ExternalAccount account) {
        DefaultAbstractSessionAccessor<?> accessor = (DefaultAbstractSessionAccessor) this.labyAPI.minecraft().sessionAccessor();
        AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        accountManager.getStorage().addAccount(account);
        accountManager.saveAsync(new Runnable[0]);
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            accessor.updateSession((Account) account);
            displayPreviousScreen();
            this.callback.accept(account);
        });
    }
}
