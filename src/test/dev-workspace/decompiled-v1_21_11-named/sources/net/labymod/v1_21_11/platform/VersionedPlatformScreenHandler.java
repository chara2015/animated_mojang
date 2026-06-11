package net.labymod.v1_21_11.platform;

import com.mojang.realmsclient.RealmsMainScreen;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.DirectJoinServerScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ManageServerScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.ChatOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.MouseSettingsScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.SkinCustomizationScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<Screen> {
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, SelectWorldScreen.class);
        register(NamedScreen.MULTIPLAYER, JoinMultiplayerScreen.class);
        register(NamedScreen.EDIT_SERVER, ManageServerScreen.class);
        register(NamedScreen.MAIN_MENU, TitleScreen.class);
        register(NamedScreen.CHAT_INPUT, ChatScreen.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, InBedChatScreen.class);
        register(NamedScreen.INGAME_MENU, PauseScreen.class);
        register(NamedScreen.INVENTORY, InventoryScreen.class);
        register(NamedScreen.CREATIVE_INVENTORY, CreativeModeInventoryScreen.class);
        register(NamedScreen.CONNECTING, ConnectScreen.class);
        register(NamedScreen.DISCONNECTED, DisconnectedScreen.class);
        register(NamedScreen.CREDITS, CreditsAndAttributionScreen.class);
        register(NamedScreen.REALMS, RealmsMainScreen.class);
        register(NamedScreen.CREATE_WORLD, CreateWorldScreen.class);
        register(NamedScreen.LEVEL_LOADING, LevelLoadingScreen.class);
        register(NamedScreen.PACK_CONFIRM, ClientCommonPacketListenerImpl.PackConfirmScreen.class);
        register(NamedScreen.PROGRESS, ProgressScreen.class);
        register(NamedScreen.GENERIC_MESSAGE, GenericMessageScreen.class);
        register(NamedScreen.OPEN_LAN_WORLD, ShareToLanScreen.class);
        register(NamedScreen.STATISTICS, StatsScreen.class);
        register(NamedScreen.ADVANCEMENTS, AdvancementsScreen.class);
        register(NamedScreen.CONFIRM, ConfirmScreen.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, SocialInteractionsScreen.class);
        register(NamedScreen.EDIT_BOOK, BookEditScreen.class);
        register(NamedScreen.OPTIONS, OptionsScreen.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, SkinCustomizationScreen.class);
        register(NamedScreen.VIDEO_SETTINGS, VideoSettingsScreen.class);
        register(NamedScreen.LANGUAGE_SELECTION, LanguageSelectScreen.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, PackSelectionScreen.class);
        register(NamedScreen.AUDIO_SETTINGS, SoundOptionsScreen.class);
        register(NamedScreen.CONTROL_SETTINGS, ControlsScreen.class);
        register(NamedScreen.CHAT_SETTINGS, ChatOptionsScreen.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, AccessibilityOptionsScreen.class);
        register(NamedScreen.KEYBIND_SETTINGS, KeyBindsScreen.class);
        register(NamedScreen.MOUSE_SETTINGS, MouseSettingsScreen.class);
        registerFactory((GameScreen) NamedScreen.SINGLEPLAYER, () -> {
            return new SelectWorldScreen(new TitleScreen(false));
        });
        registerFactory((GameScreen) NamedScreen.MULTIPLAYER, () -> {
            return new JoinMultiplayerScreen(new TitleScreen(false));
        });
        registerFactory((GameScreen) NamedScreen.OPTIONS, () -> {
            Screen prevScreen = Minecraft.getInstance().screen;
            if (prevScreen instanceof GenericMessageScreen) {
                prevScreen = null;
            }
            return new OptionsScreen(prevScreen, Minecraft.getInstance().options);
        });
        registerFactory((GameScreen) NamedScreen.CHAT_INPUT, () -> {
            return new ChatScreen("", false);
        });
        registerFactory((GameScreen) NamedScreen.INGAME_MENU, () -> {
            return new PauseScreen(true);
        });
        registerFactory((GameScreen) NamedScreen.DIRECT_CONNECT, () -> {
            Screen prevScreen = Minecraft.getInstance().screen;
            ServerData data = new ServerData(I18n.get("selectServer.defaultName", new Object[0]), "", ServerData.Type.OTHER);
            return new DirectJoinServerScreen(prevScreen, join -> {
                if (join) {
                    ConnectScreen.startConnecting(prevScreen, Minecraft.getInstance(), ServerAddress.parseString(data.ip), data, false, (TransferState) null);
                } else {
                    Minecraft.getInstance().setScreen(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, CreditsAndAttributionScreen::new);
        registerFactory(NamedScreen.REALMS, RealmsMainScreen::new);
        registerFactory((GameScreen) NamedScreen.LANGUAGE_SELECTION, () -> {
            return new LanguageSelectScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options, Minecraft.getInstance().getLanguageManager());
        });
        registerFactory((GameScreen) NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new AccessibilityOptionsScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, ShareToLanScreen::new);
    }

    protected void registerFactory(GameScreen screen, Function<Screen, Screen> screenFactory) {
        registerFactory(screen, () -> {
            return (Screen) screenFactory.apply(Minecraft.getInstance().screen);
        });
    }

    public boolean isInventoryScreen(Object screen) {
        return screen instanceof AbstractContainerScreen;
    }
}
