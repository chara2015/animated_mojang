package net.labymod.core.client.gui.screen.activity.activities.menu;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.event.client.gui.screen.IngameMenuInitializeEvent;
import net.labymod.core.client.gui.screen.activity.activities.ingame.event.LootBoxActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LiveLabyNetServerInfoWidget;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/menu/IngameMenuOverlay.class */
@Links({@Link(value = "activity/multiplayer/server-list.lss", priority = -64), @Link("activity/overlay/menu/ingame-menu.lss")})
@AutoActivity
public class IngameMenuOverlay extends AbstractLayerActivity {
    private static final LabyNetServerInfoCache<StorageServerData> SERVER_INFO_CACHE = new LabyNetServerInfoCache<>(StorageServerData.of("", ""), null);

    public IngameMenuOverlay(ScreenInstance parentScreen) {
        super(parentScreen);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        ServerData serverData;
        StorageServerData storageServerData;
        super.initialize(parent);
        DivWidget container = new DivWidget();
        container.addId("container");
        MultiplayerConfig.ServerInfoPosition position = this.labyAPI.config().multiplayer().showCurrentServerInfo().get();
        if (position != MultiplayerConfig.ServerInfoPosition.DISABLED && (serverData = this.labyAPI.serverController().getCurrentServerData()) != null) {
            if (serverData instanceof StorageServerData) {
                storageServerData = (StorageServerData) serverData;
            } else {
                storageServerData = StorageServerData.of(serverData.getName(), serverData.address());
            }
            SERVER_INFO_CACHE.apply(storageServerData);
            LiveLabyNetServerInfoWidget widget = new LiveLabyNetServerInfoWidget(storageServerData, SERVER_INFO_CACHE);
            widget.setDefaultCallback();
            widget.setQuickJoinEnabled(false);
            widget.setFriendHeadsEnabled(false);
            SERVER_INFO_CACHE.update();
            widget.addId("serverinfo-preview");
            container.addChild(widget);
            if (position == MultiplayerConfig.ServerInfoPosition.ABOVE_BUTTONS) {
                widget.addId("above");
            } else {
                widget.addId("below");
            }
        }
        ((Document) this.document).addChild(container);
        IngameMenuInitializeEvent event = (IngameMenuInitializeEvent) Laby.fireEvent(new IngameMenuInitializeEvent());
        HorizontalListWidget buttonContainerLeft = new HorizontalListWidget();
        buttonContainerLeft.addId("button-container-left", "button-container");
        ButtonWidget reportBugButton = new ButtonWidget();
        reportBugButton.addId("report-bug-button");
        reportBugButton.icon().set(Textures.SpriteCommon.BUG);
        reportBugButton.updateComponent(Component.translatable("labymod.activity.settings.reportBug", new Component[0]));
        reportBugButton.setPressable(() -> {
            Minecraft minecraft = this.labyAPI.minecraft();
            minecraft.chatExecutor().openUrl("https://www.labymod.net/ideas#category=clientbugs");
        });
        buttonContainerLeft.addEntry(reportBugButton);
        ButtonWidget screenshotWidget = ButtonWidget.i18n("labymod.activity.menu.button.screenshots");
        screenshotWidget.addId("screenshot-button");
        screenshotWidget.icon().set(Textures.SpriteCommon.PICTURE);
        screenshotWidget.setPressable(() -> {
            this.labyAPI.minecraft().minecraftWindow().displayScreen(ScreenshotBrowserActivity.INSTANCE);
        });
        buttonContainerLeft.addEntry(screenshotWidget);
        addButtons(IngameMenuInitializeEvent.PauseMenuButtonPosition.LEFT, event, buttonContainerLeft);
        ((Document) this.document).addChild(buttonContainerLeft);
        HorizontalListWidget buttonContainerRight = new HorizontalListWidget();
        buttonContainerRight.addId("button-container-right", "button-container");
        if (LabyMod.references().lootBoxService().isFeatureAvailable() && Laby.labyAPI().config().ingame().lootBoxes().get().booleanValue()) {
            ButtonWidget eventButton = new ButtonWidget();
            eventButton.addId("loot-box-button");
            eventButton.icon().set(Textures.SpriteCommon.LOOTBOX);
            eventButton.updateComponent(Component.translatable(LootBoxActivity.i18nKey("lootbox"), new Component[0]));
            eventButton.setPressable(() -> {
                Minecraft minecraft = this.labyAPI.minecraft();
                minecraft.minecraftWindow().displayScreen(LootBoxActivity.overview());
            });
            buttonContainerRight.addEntry(eventButton);
        }
        addButtons(IngameMenuInitializeEvent.PauseMenuButtonPosition.RIGHT, event, buttonContainerRight);
        ((Document) this.document).addChild(buttonContainerRight);
    }

    private void addButtons(IngameMenuInitializeEvent.PauseMenuButtonPosition position, IngameMenuInitializeEvent event, HorizontalListWidget widget) {
        event.forEachButton(position, button -> {
            ButtonWidget buttonWidget = ButtonWidget.component(button.text(), button.getIcon());
            Runnable runnable = button.onClick();
            buttonWidget.setEnabled(runnable != null);
            if (runnable != null) {
                Objects.requireNonNull(runnable);
                buttonWidget.setPressable(runnable::run);
            }
            widget.addEntry(buttonWidget);
        });
    }
}
