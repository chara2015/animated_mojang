package net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect;

import java.util.concurrent.TimeUnit;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ProgressableProgressBarWidget;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.DirectConnectServerInfoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/directconnect/DirectConnectActivity.class */
@Links({@Link("activity/multiplayer/direct-connect.lss"), @Link("activity/multiplayer/server-info.lss"), @Link("activity/multiplayer/server-list.lss")})
@AutoActivity
public class DirectConnectActivity extends SimpleActivity {
    private static final ServerAddress EMPTY_SERVER_ADDRESS = new ServerAddress("", 0, true);
    private static final String TRANSLATION_KEY_PREFIX = "labymod.activity.directConnect.";
    private final MultiplayerActivity multiplayerActivity;
    private final DirectConnectServerInfoWidget serverInfoWidget;
    private final Task task;
    private TextFieldWidget addressWidget;
    private DivWidget serverInfoWrapper;
    private ButtonWidget saveButton;
    private ButtonWidget joinButton;
    private long lastCharacterTyped = 0;
    private String lastSuccessfulAddress = this.labyAPI.minecraft().options().getLastKnownServer();
    private final StorageServerData serverData = StorageServerData.of((String) null, this.lastSuccessfulAddress);
    private final LabyNetServerInfoCache<StorageServerData> cache = new LabyNetServerInfoCache<>(this.serverData, null);

    public DirectConnectActivity(MultiplayerActivity multiplayerActivity) {
        this.multiplayerActivity = multiplayerActivity;
        this.cache.setProtectIp(false);
        this.serverInfoWidget = new DirectConnectServerInfoWidget(this.serverData, this.cache);
        this.cache.setCallback(cache -> {
            update();
        });
        this.cache.update();
        this.task = Task.builder(() -> {
            if (this.lastCharacterTyped == 0 || TimeUtil.getMillis() - this.lastCharacterTyped < 500) {
                return;
            }
            this.lastCharacterTyped = 0L;
            if (!this.lastSuccessfulAddress.equals(this.serverData.address().toString())) {
                this.cache.update();
            }
        }).repeat(500L, TimeUnit.MILLISECONDS).build();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ComponentWidget title = ComponentWidget.i18n(getTranslationKey("title"));
        title.addId("title", "center-x");
        ((Document) this.document).addChild(title);
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("container", "center-x");
        FlexibleContentWidget inputWrapper = new FlexibleContentWidget();
        inputWrapper.addId("input-wrapper", "center-x", "wrapper");
        ComponentWidget inputTitle = ComponentWidget.i18n(getTranslationKey("serverAddress.name"));
        inputTitle.addId("input-title");
        inputWrapper.addContent(inputTitle);
        FlexibleContentWidget inputFieldWrapper = new FlexibleContentWidget();
        inputFieldWrapper.addId("input-field-wrapper", "wrapper");
        String address = this.addressWidget != null ? this.addressWidget.getText() : this.lastSuccessfulAddress;
        this.addressWidget = new TextFieldWidget();
        this.addressWidget.addId("input-field");
        this.addressWidget.maximalLength(128);
        this.addressWidget.setText(address);
        this.addressWidget.updateListener(this::applyAddress);
        this.addressWidget.submitHandler(text -> {
            this.serverData.copy().connect();
        });
        this.addressWidget.placeholder(Component.translatable(getTranslationKey("serverAddress.placeholder"), new Component[0]));
        inputFieldWrapper.addFlexibleContent(this.addressWidget);
        this.saveButton = ButtonWidget.icon(Textures.SpriteCommon.EXPORT);
        this.saveButton.addId("save-button");
        this.saveButton.setHoverComponent(Component.translatable(getTranslationKey("save.description"), new Component[0]));
        this.saveButton.setPressable(() -> {
            ServerAddress serverAddress = this.cache.serverAddress();
            this.labyAPI.serverController().serverList().add(StorageServerData.of(serverAddress.toString(), serverAddress));
            this.saveButton.setEnabled(canSave());
        });
        this.saveButton.setEnabled(!this.addressWidget.getText().trim().isEmpty() && canSave());
        inputFieldWrapper.addContent(this.saveButton);
        inputWrapper.addContent(inputFieldWrapper);
        flexibleContentWidget.addContent(inputWrapper);
        this.serverInfoWrapper = new DivWidget();
        this.serverInfoWrapper.addId("direct-connect-server-info-wrapper");
        this.serverInfoWrapper.addChild(new ProgressableProgressBarWidget(() -> {
            if (this.addressWidget.getText().trim().isEmpty()) {
                return 0.0f;
            }
            return this.serverInfoWidget.getProgress();
        }).addId("progress-bar"));
        this.serverInfoWrapper.addChild(this.serverInfoWidget);
        flexibleContentWidget.addContent(this.serverInfoWrapper);
        FlexibleContentWidget buttonWrapper = new FlexibleContentWidget();
        buttonWrapper.addId("button-wrapper", "center-x", "wrapper");
        this.joinButton = ButtonWidget.i18n("labymod.activity.multiplayer.joinServer");
        this.joinButton.addId("action-button");
        this.joinButton.setPressable(() -> {
            this.serverData.copy().connect();
        });
        this.joinButton.setEnabled(!this.addressWidget.getText().trim().isEmpty());
        buttonWrapper.addContent(this.joinButton);
        ButtonWidget cancelButton = ButtonWidget.i18n("labymod.ui.button.cancel");
        cancelButton.addId("action-button");
        cancelButton.setPressable(() -> {
            this.labyAPI.minecraft().minecraftWindow().displayScreen(this.multiplayerActivity);
        });
        buttonWrapper.addContent(cancelButton);
        flexibleContentWidget.addContent(buttonWrapper);
        ((Document) this.document).addChild(flexibleContentWidget);
        if (this.lastSuccessfulAddress.isEmpty() && !address.isEmpty()) {
            this.cache.update();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        String address = this.addressWidget == null ? this.lastSuccessfulAddress : this.addressWidget.getText();
        MinecraftOptions options = this.labyAPI.minecraft().options();
        if (!address.equals(options.getLastKnownServer())) {
            options.setLastKnownServer(address);
            options.save();
        }
        if (this.task.isRunning()) {
            this.task.cancel();
            this.lastCharacterTyped = 0L;
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
    }

    private void applyAddress(String address) {
        if (address.equals(this.serverData.address().toString())) {
            return;
        }
        this.lastCharacterTyped = TimeUtil.getMillis();
        if (!this.task.isRunning()) {
            this.task.execute();
        }
        String address2 = address.trim();
        if (address2.isEmpty()) {
            this.lastSuccessfulAddress = "";
            this.cache.setAddress("");
            this.saveButton.setEnabled(false);
            this.joinButton.setEnabled(false);
            return;
        }
        this.cache.setAddress(address2);
        this.serverInfoWidget.setProgressable(false);
        this.saveButton.setEnabled(canSave());
        this.joinButton.setEnabled(true);
    }

    private boolean canSave() {
        ServerAddress address = this.cache.serverAddress();
        return (address == null || this.labyAPI.serverController().serverList().has(address)) ? false : true;
    }

    private String getTranslationKey(String key) {
        return "labymod.activity.directConnect." + key;
    }

    private void update() {
        ServerInfo serverInfo = this.cache.serverInfo();
        if (!this.serverData.address().equals(serverInfo.address())) {
            return;
        }
        if (this.serverInfoWidget.opacity().get().floatValue() != 0.0f) {
            this.serverInfoWidget.updateServerInfo(serverInfo);
        }
        if (serverInfo.getStatus() != ServerInfo.Status.LOADING) {
            this.lastSuccessfulAddress = this.serverData.address().toString();
            this.serverInfoWidget.setProgressable(true);
        }
    }
}
