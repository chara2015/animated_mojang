package net.labymod.api.configuration.settings.creator.hotkey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.labymod.api.util.CollectionHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/hotkey/HotkeyHolder.class */
public final class HotkeyHolder {
    private static HotkeyHolder instance;
    private final List<Hotkey> keybindings = new ArrayList();

    private HotkeyHolder() {
    }

    public static HotkeyHolder getInstance() {
        if (instance == null) {
            instance = new HotkeyHolder();
        }
        return instance;
    }

    public void registerHotkey(Hotkey keybinding) {
        this.keybindings.add(keybinding);
    }

    public Collection<Hotkey> getHotkeys() {
        return Collections.unmodifiableCollection(CollectionHelper.filter(this.keybindings, hotkey -> {
            return hotkey.visibility().getAsBoolean();
        }));
    }
}
