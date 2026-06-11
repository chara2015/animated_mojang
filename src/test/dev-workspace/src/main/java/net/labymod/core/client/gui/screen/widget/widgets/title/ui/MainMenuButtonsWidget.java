package net.labymod.core.client.gui.screen.widget.widgets.title.ui;

import java.util.Objects;
import net.labymod.api.BuildData;
import net.labymod.api.Textures;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.network.server.JoinServerRequest;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.advertisement.Advertisement;
import net.labymod.core.main.advertisement.model.FooterButton;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/ui/MainMenuButtonsWidget.class */
@AutoWidget
public class MainMenuButtonsWidget extends AbstractWidget<Widget> {
    private static final Logging LOGGER = Logging.getLogger();
    public static final boolean IS_ACCESSIBILITY_VERSION = MinecraftVersions.V19w13a.orNewer();
    private static final boolean NEW_TITLE_SCREEN_LAYOUT = MinecraftVersions.V26_2_snapshot_7.orNewer();
    private static final String QUIT_BUTTON_RIGHT_VARIABLE = "--quit-button-right";
    private static final String QUIT_BUTTON_TOP_VARIABLE = "--quit-button-top";
    private static final String OPTIONS_BUTTON_LEFT_VARIABLE = "--options-button-left";
    private static final String OPTIONS_BUTTON_TOP_VARIABLE = "--options-button-top";
    private static final String LANGUAGE_BUTTON_LEFT_VARIABLE = "--language-button-left";
    private static final String LANGUAGE_BUTTON_TOP_VARIABLE = "--language-button-top";
    private static final String ACCESSIBILITY_BUTTON_RIGHT_VARIABLE = "--accessibility-button-right";
    private static final String ACCESSIBILITY_BUTTON_TOP_VARIABLE = "--accessibility-button-top";
    private static final String FRIENDS_BUTTON_LEFT_VARIABLE = "--friends-button-left";
    private static final String FRIENDS_BUTTON_TOP_VARIABLE = "--friends-button-top";

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        FooterButton footerButton;
        String address;
        super.initialize(parent);
        if (NEW_TITLE_SCREEN_LAYOUT) {
            setVariable(QUIT_BUTTON_RIGHT_VARIABLE, (Number) 24);
            setVariable(QUIT_BUTTON_TOP_VARIABLE, (Number) 96);
            setVariable(OPTIONS_BUTTON_LEFT_VARIABLE, (Number) 24);
            setVariable(OPTIONS_BUTTON_TOP_VARIABLE, (Number) 96);
            setVariable(LANGUAGE_BUTTON_LEFT_VARIABLE, "calc(50% - 10)");
            setVariable(LANGUAGE_BUTTON_TOP_VARIABLE, (Number) 72);
            setVariable(ACCESSIBILITY_BUTTON_RIGHT_VARIABLE, "calc(50% - 34)");
            setVariable(ACCESSIBILITY_BUTTON_TOP_VARIABLE, (Number) 72);
            setVariable(FRIENDS_BUTTON_LEFT_VARIABLE, "calc(50% - 34)");
            setVariable(FRIENDS_BUTTON_TOP_VARIABLE, (Number) 72);
        } else {
            setVariable(QUIT_BUTTON_TOP_VARIABLE, (Number) 84);
            setVariable(QUIT_BUTTON_RIGHT_VARIABLE, (Number) 24);
            setVariable(OPTIONS_BUTTON_TOP_VARIABLE, (Number) 84);
            setVariable(OPTIONS_BUTTON_LEFT_VARIABLE, (Number) 24);
            setVariable(LANGUAGE_BUTTON_LEFT_VARIABLE, (Number) 0);
            setVariable(LANGUAGE_BUTTON_TOP_VARIABLE, (Number) 84);
            setVariable(ACCESSIBILITY_BUTTON_RIGHT_VARIABLE, (Number) 0);
            setVariable(ACCESSIBILITY_BUTTON_TOP_VARIABLE, (Number) 84);
        }
        Minecraft minecraft = this.labyAPI.minecraft();
        ButtonWidget singleplayerWidget = ButtonWidget.i18nMinecraft("menu.singleplayer", () -> {
            display(NamedScreen.SINGLEPLAYER);
        });
        singleplayerWidget.addId("singleplayer", "large-button");
        addChild(singleplayerWidget);
        ButtonWidget multiplayerWidget = ButtonWidget.i18nMinecraft("menu.multiplayer", () -> {
            display(NamedScreen.MULTIPLAYER);
        });
        multiplayerWidget.addId("multiplayer", "large-button");
        addChild(multiplayerWidget);
        ButtonWidget screenshotsWidget = ButtonWidget.i18n("labymod.activity.menu.button.screenshots", () -> {
            display(ScreenshotBrowserActivity.INSTANCE);
        });
        screenshotsWidget.addId("screenshots", "small-button");
        addChild(screenshotsWidget);
        ButtonWidget realmsWidget = ButtonWidget.i18nMinecraft("menu.online", () -> {
            display(NamedScreen.REALMS);
        });
        realmsWidget.addId("realms", "small-button");
        realmsWidget.setEnabled(BuildData.hasRealms());
        addChild(realmsWidget);
        ButtonWidget optionsWidget = ButtonWidget.i18nMinecraft("menu.options", () -> {
            display(NamedScreen.OPTIONS);
        });
        optionsWidget.addId("options", "small-button");
        addChild(optionsWidget);
        Objects.requireNonNull(minecraft);
        ButtonWidget quitWidget = ButtonWidget.i18nMinecraft("menu.quit", minecraft::shutdownGame);
        quitWidget.addId("quit", "small-button");
        addChild(quitWidget);
        if (this.labyAPI.config().appearance().titleScreen().quickPlay().get().booleanValue() && (address = this.labyAPI.minecraft().options().getLastKnownServer()) != null && !address.isEmpty()) {
            ButtonWidget quickPlayWidget = ButtonWidget.icon(Textures.SpriteCommon.FORWARD_BUTTON);
            quickPlayWidget.addId("quick-play", "icon-button");
            quickPlayWidget.setHoverComponent(Component.translatable("labymod.activity.menu.button.quickPlay", new Component[0]).arguments(Component.text(address)));
            quickPlayWidget.setPressable(() -> {
                JoinServerRequest joinServerRequest = JoinServerRequest.builder().serverData(builder -> {
                    builder.name(address).address(address).lan(false);
                }).build();
                this.labyAPI.serverController().joinServer(joinServerRequest);
            });
            addChild(quickPlayWidget);
        }
        ButtonWidget languageWidget = ButtonWidget.icon(Textures.SpriteMinecraftIcons.LANGUAGE, () -> {
            display(NamedScreen.LANGUAGE_SELECTION);
        });
        languageWidget.addId("language", "icon-button");
        addChild(languageWidget);
        if (IS_ACCESSIBILITY_VERSION) {
            ButtonWidget accessibilityWidget = ButtonWidget.icon(Textures.SpriteMinecraftIcons.ACCESSIBILITY, () -> {
                display(NamedScreen.ACCESSIBILITY_SETTINGS);
            });
            accessibilityWidget.addId("accessibility", "icon-button");
            addChild(accessibilityWidget);
        }
        if (NEW_TITLE_SCREEN_LAYOUT) {
            ButtonWidget friendsWidget = ButtonWidget.icon(Textures.SpriteMinecraftIcons.FRIENDS, () -> {
                this.labyAPI.minecraft().confirmFriendsListEnabled(() -> {
                    display(NamedScreen.FRIENDS);
                });
            });
            friendsWidget.addId("friends", "icon-button");
            addChild(friendsWidget);
        }
        try {
            Advertisement advertisement = LabyMod.references().advertisementService().getAdvertisement();
            if (advertisement != null && (footerButton = advertisement.footerButton()) != null && footerButton.visible()) {
                DivWidget advertisementContainer = new DivWidget();
                advertisementContainer.addId("advertisement-container");
                advertisementContainer.setVariable("--advertisement-button-width", Integer.valueOf(footerButton.buttonWidth()));
                ButtonWidget advertisementButton = ButtonWidget.component(Component.text(footerButton.name()), footerButton.getIcon());
                advertisementButton.setVariable("--advertisement-button-bg-color", footerButton.color());
                advertisementButton.setVariable("--advertisement-button-border-color", footerButton.borderColor());
                advertisementButton.setVariable("--advertisement-button-hover-bg-color", footerButton.colorHover());
                advertisementButton.addId("advertisement-button");
                advertisementButton.setPressable(() -> {
                    this.labyAPI.minecraft().chatExecutor().openUrlOrJoinServer(footerButton.url());
                });
                advertisementContainer.addChild(advertisementButton);
                if (footerButton.isNew()) {
                    IconWidget newIcon = new IconWidget(Textures.SpriteCommon.NEW);
                    newIcon.addId("advertisement-button-new-icon");
                    advertisementContainer.addChild(newIcon);
                }
                addChild(advertisementContainer);
            }
        } catch (Throwable t) {
            LabySentry.capture(t);
            LOGGER.error("An error occurred while trying to load the advertisement footer button", t);
        }
    }

    private void display(NamedScreen namedScreen) {
        this.labyAPI.minecraft().minecraftWindow().displayScreen(namedScreen);
    }

    private void display(Activity activity) {
        this.labyAPI.minecraft().minecraftWindow().displayScreen(activity);
    }
}
