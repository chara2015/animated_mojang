package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SearchTag;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/DefaultHotkeysConfig.class */
public class DefaultHotkeysConfig extends Config implements HotkeysConfig {

    @SpriteSlot(page = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> emoteWheelKey = new ConfigProperty<>(MouseButton.X);

    @SpriteSlot(x = 0, y = 6, page = 1)
    @IntroducedIn("4.2.0")
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> sprayWheelKey = new ConfigProperty<>(Key.G);

    @SpriteSlot(x = 1, page = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> interactionMenuKey = new ConfigProperty<>(MouseButton.MIDDLE);

    @SpriteSlot(x = 4, y = 2, page = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> widgetEditorKey = new ConfigProperty<>(Key.R_SHIFT);

    @SpriteSlot(x = 3, y = 1, page = 1)
    @KeybindWidget.KeyBindSetting
    @IntroducedIn("4.1.4")
    private final ConfigProperty<Key> toggleHitBox = new ConfigProperty<>(Key.NONE);

    @SpriteSlot(x = 2, y = 5, page = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> friendsKey = new ConfigProperty<>(Key.NONE);

    @SpriteSlot(x = 5, y = 2, page = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    @IntroducedIn("4.1.0")
    private final ConfigProperty<Key> markerKey = new ConfigProperty<>(Key.NONE);

    @VersionCompatibility("1.8.9<1.12.2")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 4, page = 1)
    @SettingSection("advanced")
    private final ConfigProperty<Boolean> rawMouseInput = new ConfigProperty<>(true);

    @VersionCompatibility("1.8.9")
    @SearchTag({"focus", "movement", "modern", "keyboard", "keybinding"})
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1, y = 5)
    @IntroducedIn("4.1.11")
    private final ConfigProperty<Boolean> modernKeybinding = new ConfigProperty<>(true);

    @VersionCompatibility("1.12<*")
    @SpriteSlot(x = 5, y = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> disableNarratorHotkey = new ConfigProperty<>(false);

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Key> emoteWheelKey() {
        return this.emoteWheelKey;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Key> interactionMenuKey() {
        return this.interactionMenuKey;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Key> sprayWheelKey() {
        return this.sprayWheelKey;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Key> toggleHitBox() {
        return this.toggleHitBox;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Key> widgetEditorKey() {
        return this.widgetEditorKey;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Key> friendsKey() {
        return this.friendsKey;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Key> markerKey() {
        return this.markerKey;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Boolean> modernKeybinding() {
        return this.modernKeybinding;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Boolean> rawMouseInput() {
        return this.rawMouseInput;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.HotkeysConfig
    public ConfigProperty<Boolean> disableNarratorHotkey() {
        return this.disableNarratorHotkey;
    }
}
