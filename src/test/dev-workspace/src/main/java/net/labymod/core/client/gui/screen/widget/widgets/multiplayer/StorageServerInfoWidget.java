package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.client.gui.navigation.elements.AccountNavigationElement;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/StorageServerInfoWidget.class */
@AutoWidget
public class StorageServerInfoWidget extends LabyNetServerInfoWidget<StorageServerData> {
    public static final String CUSTOM_BACKGROUND_KEY = "CustomBackgroundImage";
    private final Consumer<StorageServerInfoWidget> delete;
    private final Runnable save;

    public StorageServerInfoWidget(@NotNull StorageServerData serverData, @NotNull LabyNetServerInfoCache<StorageServerData> cache, @NotNull Consumer<StorageServerInfoWidget> delete, @NotNull Runnable save) {
        super(serverData, cache);
        Objects.requireNonNull(delete, "Delete consumer cannot be null!");
        Objects.requireNonNull(save, "Save runnable cannot be null!");
        this.delete = delete;
        this.save = save;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected IconWidget serverIconWidget(boolean loading) {
        return super.serverIconWidget(loading);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected void startInitialize(Parent parent) {
        StorageServerData serverData = (StorageServerData) serverData();
        if (serverInfo().getStatus() == ServerInfo.Status.LOADING || !serverData.hasMetadata()) {
            super.startInitialize(parent);
            return;
        }
        String customBackgroundData = serverData.metadata().get(CUSTOM_BACKGROUND_KEY);
        if (customBackgroundData == null) {
            super.startInitialize(parent);
            return;
        }
        Icon icon = Icon.url(customBackgroundData);
        IconWidget backgroundWidget = new IconWidget(icon);
        backgroundWidget.addId("server-background");
        addChild(backgroundWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        createContextMenuLazy(this::initializeContextMenu);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initializeContextMenu(ContextMenu contextMenu) {
        ContextMenuEntry deleteContextMenuEntry = ContextMenuEntry.builder().icon(Textures.SpriteCommon.TRASH).text(Component.translatable("labymod.ui.button.delete", new Component[0])).clickHandler(entry -> {
            this.delete.accept(this);
            return true;
        }).build();
        contextMenu.addEntry(deleteContextMenuEntry);
        if (SERVER_LIST_SETTINGS.get().richServerList().get().booleanValue() && canCustomize()) {
            StorageServerData serverData = (StorageServerData) serverData();
            Account[] accounts = LabyMod.getInstance().getAccountManager().getAccounts();
            if (accounts.length != 0) {
                ContextMenuEntry accountContextMenuEntry = ContextMenuEntry.builder().icon(Textures.SpriteCommon.MULTIPLAYER).text(Component.translatable("labymod.activity.multiplayer.private.account.title", new Component[0])).hoverComponent(Component.translatable("labymod.activity.multiplayer.private.account.description", new Component[0])).disabled(() -> {
                    int validAccounts = 0;
                    for (Account account : accounts) {
                        if (AccountNavigationElement.isDisplayableAccount(account)) {
                            validAccounts++;
                        }
                    }
                    return validAccounts == 0;
                }).subMenu(() -> {
                    ContextMenu subMenu = new ContextMenu();
                    UUID boundAccountUuid = serverData.getBoundUniqueId();
                    for (Account account : accounts) {
                        if ((account instanceof ExternalAccount) && AccountNavigationElement.isDisplayableAccount(account)) {
                            boolean selected = account.getUUID().equals(boundAccountUuid);
                            subMenu.addEntry(ContextMenuEntry.builder().text(Component.text(account.getUsername())).icon(Icon.head(account.getUUID())).subIcon(() -> {
                                if (selected) {
                                    return Textures.SpriteCommon.GREEN_CHECKED;
                                }
                                return null;
                            }).clickHandler(entry2 -> {
                                Map<String, String> metadata = serverData.metadata();
                                if (selected) {
                                    metadata.remove(StorageServerData.BOUND_ACCOUNT_KEY);
                                } else {
                                    metadata.put(StorageServerData.BOUND_ACCOUNT_KEY, account.getUUID().toString());
                                }
                                this.save.run();
                                return true;
                            }).build());
                        }
                    }
                    return subMenu;
                }).build();
                contextMenu.addEntry(accountContextMenuEntry);
                boolean backgroundSet = serverData.hasMetadata() && serverData.metadata().containsKey(CUSTOM_BACKGROUND_KEY);
                boolean lastScreenshotAvailable = serverData.getLastScreenshotFile() != null;
                ContextMenuEntry.Builder builder = ContextMenuEntry.builder();
                Locale locale = Locale.ROOT;
                Object[] objArr = new Object[1];
                objArr[0] = backgroundSet ? "reset" : "set";
                ContextMenuEntry.Builder builderText = builder.text(Component.translatable(String.format(locale, "labymod.activity.multiplayer.private.background.%s.title", objArr), new Component[0]));
                Locale locale2 = Locale.ROOT;
                Object[] objArr2 = new Object[1];
                objArr2[0] = backgroundSet ? "reset" : lastScreenshotAvailable ? "set" : "unavailable";
                ContextMenuEntry backgroundContextMenuEntry = builderText.hoverComponent(Component.translatable(String.format(locale2, "labymod.activity.multiplayer.private.background.%s.description", objArr2), new Component[0])).icon(Textures.SpriteCommon.PICTURE).disabled((backgroundSet || lastScreenshotAvailable) ? false : true).clickHandler(entry2 -> {
                    updateCustomBackground(serverData, backgroundSet, lastScreenshotAvailable);
                    return true;
                }).build();
                contextMenu.addEntry(backgroundContextMenuEntry);
            }
            if (this.userStatsUrl != null) {
                ContextMenuEntry userStatsEntry = ContextMenuEntry.builder().clickHandler(entry3 -> {
                    OperatingSystem.getPlatform().openUrl(this.userStatsUrl.replace("{userName}", this.labyAPI.getName()).replace("{uuid}", this.labyAPI.getUniqueId().toString()) + (this.userStatsUrl.contains("?") ? "&" : "?") + "utm_source=labymod&utm_medium=client&utm_campaign=serverlist");
                    return true;
                }).icon(Textures.SpriteCommon.WORKBENCH).text(Component.translatable("labymod.activity.multiplayer.private.user_stats.title", new Component[0])).build();
                contextMenu.addEntry(userStatsEntry);
            }
        }
    }

    protected boolean canCustomize() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void reInitialize() {
        setContextMenu(null);
        super.reInitialize();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    public void connect(String command) {
        SessionAccessor sessionAccessor;
        Session session;
        UUID boundUniqueId = ((StorageServerData) serverData()).getBoundUniqueId();
        if (boundUniqueId == null) {
            super.connect(command);
            return;
        }
        Account boundAccount = null;
        LabyMod labyMod = LabyMod.getInstance();
        Account[] accounts = labyMod.getAccountManager().getAccounts();
        int length = accounts.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Account account = accounts[i];
            if (!AccountNavigationElement.isDisplayableAccount(account) || !account.getUUID().equals(boundUniqueId)) {
                i++;
            } else {
                boundAccount = account;
                break;
            }
        }
        if (boundAccount != null && ((session = (sessionAccessor = labyMod.minecraft().sessionAccessor()).getSession()) == null || !session.getUniqueId().equals(boundAccount.getUUID()) || !Objects.equals(session.getAccessToken(), boundAccount.getAccessToken()))) {
            ((DefaultAbstractSessionAccessor) sessionAccessor).updateSession(boundAccount);
        }
        super.connect(command);
    }

    private void updateCustomBackground(StorageServerData serverData, boolean backgroundSet, boolean lastScreenshotAvailable) {
        File file;
        if (backgroundSet) {
            serverData.metadata().remove(CUSTOM_BACKGROUND_KEY);
            this.save.run();
            reInitialize();
        } else if (lastScreenshotAvailable && (file = serverData.getLastScreenshotFile()) != null) {
            Task.builder(() -> {
                try {
                    InputStream inputStream = IOUtil.newInputStream(file.toPath());
                    try {
                        GameImage screenshot = Laby.references().gameImageProvider().getImage(inputStream);
                        GameImage backgroundImage = makeBackgroundImage(screenshot);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        try {
                            backgroundImage.write("PNG", stream);
                            String encoded = "data:image/png;base64," + Base64.getEncoder().encodeToString(stream.toByteArray());
                            serverData.metadata().put(CUSTOM_BACKGROUND_KEY, encoded);
                            stream.close();
                            this.save.run();
                            this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (Throwable t$) {
                            try {
                                stream.close();
                            } catch (Throwable x2) {
                                t$.addSuppressed(x2);
                            }
                            throw t$;
                        }
                    } finally {
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }).build().execute();
        }
    }

    private GameImage makeBackgroundImage(GameImage screenshot) {
        GameImage backgroundImage = Laby.references().gameImageProvider().createImage(Math.min(640, screenshot.getWidth()), Math.min(360, screenshot.getHeight()));
        int sx = (screenshot.getWidth() / 2) - (backgroundImage.getWidth() / 2);
        int sy = (screenshot.getHeight() / 2) - (backgroundImage.getHeight() / 2);
        backgroundImage.drawImage(screenshot, 0, 0, sx, sy, backgroundImage.getWidth(), backgroundImage.getHeight());
        return backgroundImage;
    }
}
