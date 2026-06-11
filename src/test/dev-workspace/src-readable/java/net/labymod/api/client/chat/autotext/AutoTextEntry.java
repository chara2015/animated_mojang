package net.labymod.api.client.chat.autotext;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.MultiKeybindWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/autotext/AutoTextEntry.class */
public class AutoTextEntry extends Config {

    @TextFieldWidget.TextFieldSetting(maxLength = 32)
    private final ConfigProperty<String> displayName;

    @TextFieldWidget.TextFieldSetting
    private final ConfigProperty<String> message;

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> displayInInteractionMenu;

    @MultiKeybindWidget.MultiKeyBindSetting
    @SettingRequires(value = "displayInInteractionMenu", invert = true)
    private final ConfigProperty<Key[]> requiredKeys;

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> sendImmediately;
    private AutoTextServerConfig serverConfig;

    public AutoTextEntry() {
        this.displayName = new ConfigProperty<>("");
        this.message = new ConfigProperty<>("");
        this.displayInInteractionMenu = new ConfigProperty<>(false);
        this.requiredKeys = new ConfigProperty<>(new Key[0]);
        this.sendImmediately = new ConfigProperty<>(true);
        this.serverConfig = new AutoTextServerConfig();
    }

    public AutoTextEntry(String displayName, String message, boolean displayInInteractionMenu, boolean sendImmediately, AutoTextServerConfig serverConfig, Key[] requiredKeys) {
        this.displayName = new ConfigProperty<>("");
        this.message = new ConfigProperty<>("");
        this.displayInInteractionMenu = new ConfigProperty<>(false);
        this.requiredKeys = new ConfigProperty<>(new Key[0]);
        this.sendImmediately = new ConfigProperty<>(true);
        this.displayName.set(displayName);
        this.message.set(message);
        this.displayInInteractionMenu.set(Boolean.valueOf(displayInInteractionMenu));
        this.sendImmediately.set(Boolean.valueOf(sendImmediately));
        this.requiredKeys.set(requiredKeys);
        this.serverConfig = serverConfig;
    }

    public ConfigProperty<String> displayName() {
        return this.displayName;
    }

    public ConfigProperty<String> message() {
        return this.message;
    }

    public ConfigProperty<Boolean> displayInInteractionMenu() {
        return this.displayInInteractionMenu;
    }

    public ConfigProperty<Boolean> sendImmediately() {
        return this.sendImmediately;
    }

    public ConfigProperty<Key[]> requiredKeys() {
        return this.requiredKeys;
    }

    public AutoTextServerConfig serverConfig() {
        return this.serverConfig;
    }

    public void setServerConfig(AutoTextServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public ServerAddress serverAddress() {
        if (this.serverConfig.enabled().get().booleanValue()) {
            return ServerAddress.parse(this.serverConfig.address().get());
        }
        return null;
    }

    public boolean isActiveOnCurrentServer() {
        ServerAddress requiredAddress = serverAddress();
        if (requiredAddress == null) {
            return true;
        }
        ServerData currentAddress = Laby.labyAPI().serverController().getCurrentServerData();
        if (currentAddress == null) {
            return false;
        }
        return requiredAddress.equals(currentAddress.address());
    }

    public void setServerAddress(String serverAddress) {
        this.serverConfig = new AutoTextServerConfig(true, serverAddress);
    }

    public boolean isConfigured() {
        return (!this.displayName.get().isEmpty() && this.displayInInteractionMenu.get().booleanValue()) || this.requiredKeys.get().length > 0;
    }

    public boolean requiresKey(Key key) {
        Key[] keys = this.requiredKeys.get();
        for (Key requiredKey : keys) {
            if (key == requiredKey) {
                return true;
            }
        }
        return false;
    }

    public boolean isEveryKeyPressed() {
        Key[] keys = this.requiredKeys.get();
        if (keys.length == 0) {
            return false;
        }
        Minecraft minecraft = Laby.labyAPI().minecraft();
        for (Key requiredKey : keys) {
            if (!minecraft.isKeyPressed(requiredKey)) {
                return false;
            }
        }
        return true;
    }

    public AutoTextEntry copy() {
        return new AutoTextEntry(this.displayName.get(), this.message.get(), this.displayInInteractionMenu.get().booleanValue(), this.sendImmediately.get().booleanValue(), this.serverConfig.copy(), this.requiredKeys.get());
    }
}
