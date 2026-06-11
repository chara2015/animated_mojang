package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/HotkeysConfig.class */
public interface HotkeysConfig extends ConfigAccessor {
    ConfigProperty<Key> emoteWheelKey();

    ConfigProperty<Key> interactionMenuKey();

    ConfigProperty<Key> sprayWheelKey();

    ConfigProperty<Key> toggleHitBox();

    ConfigProperty<Key> widgetEditorKey();

    ConfigProperty<Key> friendsKey();

    ConfigProperty<Key> markerKey();

    ConfigProperty<Boolean> modernKeybinding();

    ConfigProperty<Boolean> rawMouseInput();

    ConfigProperty<Boolean> disableNarratorHotkey();
}
