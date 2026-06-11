package net.labymod.core.client.gui.screen.activity.activities.multiplayer.connect;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.storage.loader.external.model.ExternalMicrosoftAccount;
import net.labymod.accountmanager.storage.loader.microsoft.model.LauncherAccount;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.network.server.RejoinService;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.AutoReconnectConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenUpdateVanillaWidgetEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/connect/DisconnectedOverlay.class */
@AutoActivity
@Link("activity/overlay/disconnected-overlay.lss")
public class DisconnectedOverlay extends AbstractLayerActivity {
    private static final long MAX_REFRESH_DELAY = 120000;
    private static final ModifyReason MODIFY_REASON = ModifyReason.of(DisconnectedOverlay.class, "rejoinButton");
    private static long lastRefresh = 0;
    private final RejoinService rejoinService;
    private final ScreenService screenService;
    private int delay;
    private Task secondTask;
    private final FlexibleContentWidget contentWidget;
    private final ComponentWidget textWidget;
    private final ButtonWidget rejoinButton;

    public DisconnectedOverlay(ScreenInstance parentScreen) {
        super(parentScreen);
        this.rejoinService = Laby.references().rejoinService();
        this.screenService = Laby.references().screenService();
        boolean accountRefresh = canFixExpiredAccount();
        AutoReconnectConfig config = LabyConfigProvider.INSTANCE.get().multiplayer().autoReconnect();
        if (!config.enabled().get().booleanValue() && !accountRefresh) {
            this.rejoinButton = null;
            this.contentWidget = null;
            this.textWidget = null;
            return;
        }
        this.delay = config.delay().get().intValue();
        this.rejoinButton = ButtonWidget.component(Component.translatable("labymod.activity.multiplayer.server.connect.rejoin.button", new Component[0]));
        this.rejoinButton.addId("rejoin-button");
        this.rejoinButton.setPressable(this::triggerRejoin);
        this.contentWidget = new FlexibleContentWidget();
        this.contentWidget.addId("disconnected-overlay-content");
        this.textWidget = ComponentWidget.empty();
        this.textWidget.addId("disconnected-overlay-text");
        if (!config.reconnectAutomatically().get().booleanValue() && !accountRefresh) {
            return;
        }
        if (!accountRefresh) {
            this.textWidget.setVisible(false);
            this.secondTask = Task.builder(this::onSecond).repeat(1L, TimeUnit.SECONDS).build();
            this.secondTask.executeOnRenderThread();
        } else {
            this.rejoinButton.setEnabled(false);
            this.contentWidget.addId("disconnected-overlay-content-with-background");
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (this.rejoinButton == null) {
            return;
        }
        this.contentWidget.setVariable("--content-width", (Number) 200);
        this.contentWidget.addContent(this.textWidget);
        this.contentWidget.addContent(this.rejoinButton);
        ((Document) this.document).addChild(this.contentWidget);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    public boolean shouldHandleEscape() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        stopTask();
    }

    @Subscribe
    public void onScreenUpdateVanillaWidget(@NotNull ScreenUpdateVanillaWidgetEvent event) {
        if (this.rejoinButton == null) {
            return;
        }
        AbstractWidget<?> widget = event.getWidget();
        if (!widget.hasId("gui-tomenu-widget") && !widget.hasId("0-widget")) {
            return;
        }
        float buttonX = widget.bounds().getX(BoundsType.OUTER);
        float buttonMaxY = widget.bounds().getMaxY(BoundsType.OUTER);
        this.contentWidget.bounds().setOuterPosition(buttonX, buttonMaxY + 4.0f, MODIFY_REASON);
        this.contentWidget.setVariable("--content-width", Float.valueOf(widget.bounds().getWidth(BoundsType.OUTER)));
    }

    private void onSecond() {
        if (this.delay <= 0) {
            triggerRejoin();
        } else {
            updateButton();
            this.delay--;
        }
    }

    private void updateButton() {
        TextColor btnColor = NamedTextColor.WHITE;
        if (this.delay <= 3) {
            btnColor = NamedTextColor.RED;
        } else if (this.delay <= 10) {
            btnColor = NamedTextColor.YELLOW;
        }
        this.rejoinButton.updateComponent(Component.translatable("labymod.activity.multiplayer.server.connect.rejoin.button", new Component[0]).append(Component.text(" (")).append(Component.text(Integer.valueOf(this.delay)).color(btnColor)).append(Component.text(")")));
        this.rejoinButton.reInitialize();
    }

    private void triggerRejoin() {
        stopTask();
        ScreenInstance screen = this.screenService.createScreen(NamedScreen.MULTIPLAYER);
        this.rejoinService.rejoin(screen);
    }

    private void stopTask() {
        if (this.secondTask == null) {
            return;
        }
        this.secondTask.cancel();
        this.secondTask = null;
    }

    private boolean canFixExpiredAccount() {
        Component lastKickReason = this.rejoinService.getLastKickReason();
        if (!(lastKickReason instanceof TranslatableComponent)) {
            return false;
        }
        TranslatableComponent translatableComponent = (TranslatableComponent) lastKickReason;
        String key = translatableComponent.getKey();
        if (key == null || !key.equals("disconnect.loginFailedInfo")) {
            return false;
        }
        LOGGER.info("Failed to connect to server: Account is expired.", new Object[0]);
        if (System.currentTimeMillis() - lastRefresh < MAX_REFRESH_DELAY) {
            LOGGER.info("Skipping refreshing accounts. Last refresh was less than {} seconds ago.", 120L);
            return false;
        }
        AsyncAccountManager accountManager = Laby.labyAPI().getAccountManager();
        UUID uuid = Laby.labyAPI().getUniqueId();
        Account activeAccount = null;
        Account[] accounts = accountManager.getAccounts();
        int length = accounts.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Account account = accounts[i];
            if (!account.getUUID().equals(uuid)) {
                i++;
            } else {
                activeAccount = account;
                break;
            }
        }
        if (activeAccount == null) {
            LOGGER.warn("Failed to find active account. Skipping refreshing account.", new Object[0]);
            return false;
        }
        if (!activeAccount.isPremium()) {
            LOGGER.warn("Active account is not premium. Skipping refreshing account.", new Object[0]);
            return false;
        }
        LOGGER.info("Refreshing account {}, current state: {}", activeAccount.getUsername(), activeAccount.getSessionState().name());
        triggerAccountRefresh(activeAccount, accountManager);
        return true;
    }

    private void triggerAccountRefresh(@NotNull Account activeAccount, @NotNull AsyncAccountManager accountManager) {
        lastRefresh = System.currentTimeMillis();
        Task.builder(() -> {
            updateDisplay("labymod.activity.multiplayer.server.connect.rejoin.expiredAccount.refreshing", false, NamedTextColor.GRAY);
            DefaultAbstractSessionAccessor<?> sessionAccessor = (DefaultAbstractSessionAccessor) Laby.references().sessionAccessor();
            try {
                if (activeAccount instanceof ExternalMicrosoftAccount) {
                    ExternalMicrosoftAccount externalAccount = (ExternalMicrosoftAccount) activeAccount;
                    accountManager.getStorage().getAuthentication().forceRefreshMicrosoftSession(externalAccount, (account, previousState, newState) -> {
                        ThreadSafe.executeOnRenderThread(() -> {
                            sessionAccessor.updateSession(activeAccount);
                        });
                        LOGGER.info("Account " + account.getUsername() + " was refreshed. New State: " + newState.name(), new Object[0]);
                        updateDisplay("labymod.activity.multiplayer.server.connect.rejoin.expiredAccount.refreshedSuccessful", true, NamedTextColor.WHITE);
                    });
                } else if (activeAccount instanceof LauncherAccount) {
                    LOGGER.info("Account is launcher account. Refreshing all accounts.", new Object[0]);
                    accountManager.refreshLauncherSessions(account2 -> {
                        ThreadSafe.executeOnRenderThread(() -> {
                            sessionAccessor.updateSession(activeAccount);
                        });
                        LOGGER.info("Account " + account2.getUsername() + " refreshed successfully.", new Object[0]);
                    });
                    updateDisplay("labymod.activity.multiplayer.server.connect.rejoin.expiredAccount.refreshedSuccessful", true, NamedTextColor.WHITE);
                } else {
                    LOGGER.warn("Unknown account type: {}. Skipping refreshing account.", activeAccount.getClass().getName());
                    updateDisplay("labymod.activity.multiplayer.server.connect.rejoin.expiredAccount.refreshedFailed", false, NamedTextColor.RED);
                }
            } catch (Exception e) {
                LOGGER.error("Failed to refresh account " + activeAccount.getUsername(), e);
                updateDisplay("labymod.activity.multiplayer.server.connect.rejoin.expiredAccount.refreshedFailed", false, NamedTextColor.RED);
            }
        }).build().execute();
    }

    private void updateDisplay(@NotNull String translationKey, boolean enableButton, @NotNull TextColor textColor) {
        ThreadSafe.executeOnRenderThread(() -> {
            this.rejoinButton.setEnabled(enableButton);
            this.textWidget.setComponent(Component.translatable(translationKey, new Component[0]).color(textColor));
        });
    }
}
