package net.labymod.core.client.gui.screen.key;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.key.HotkeyService;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.models.Implements;
import net.labymod.core.addon.DefaultAddonService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/key/DefaultHotkeyService.class */
@Singleton
@Implements(HotkeyService.class)
public class DefaultHotkeyService implements HotkeyService {
    private final Map<String, Hotkey> hotkeys = new HashMap();

    public DefaultHotkeyService() {
        Laby.labyAPI().eventBus().registerListener(this);
    }

    @Override // net.labymod.api.client.gui.screen.key.HotkeyService
    public void register(String identifier, BooleanSupplier condition, Supplier<Key> keySupplier, Supplier<HotkeyService.Type> typeSupplier, Consumer<Boolean> active) {
        if (this.hotkeys.containsKey(identifier)) {
            throw new IllegalArgumentException("Hotkey with identifier " + identifier + " already exists");
        }
        this.hotkeys.put(identifier, new Hotkey(keySupplier, condition, typeSupplier, active));
    }

    @Override // net.labymod.api.client.gui.screen.key.HotkeyService
    public boolean unregister(String identifier) {
        return this.hotkeys.remove(identifier) != null;
    }

    @Subscribe
    public void onKey(KeyEvent event) {
        if (event.isCancelled() || event.state() == KeyEvent.State.HOLDING || !Laby.labyAPI().minecraft().isMouseLocked()) {
            return;
        }
        Key key = event.key();
        for (Hotkey hotkey : this.hotkeys.values()) {
            Key hotkeyKey = hotkey.keySupplier.get();
            if (key == hotkeyKey) {
                if (handle(hotkey, event.state() == KeyEvent.State.PRESS)) {
                    return;
                }
            }
        }
    }

    @Subscribe
    public void onScreenDisplay(ScreenDisplayEvent event) {
        ScreenInstance screen = event.getScreen();
        ScreenInstance previousScreen = event.getPreviousScreen();
        if (screen == null || previousScreen != null) {
            return;
        }
        for (Hotkey hotkey : this.hotkeys.values()) {
            if (hotkey.active && hotkey.typeSupplier.get() == HotkeyService.Type.HOLD) {
                hotkey.active = false;
                hotkey.activeConsumer.accept(false);
            }
        }
    }

    private boolean handle(Hotkey hotkey, boolean pressed) {
        if (!hotkey.condition.getAsBoolean()) {
            return false;
        }
        if (hotkey.namespace != null && !DefaultAddonService.getInstance().isEnabled(hotkey.namespace)) {
            return false;
        }
        HotkeyService.Type type = hotkey.typeSupplier.get();
        if (type == HotkeyService.Type.TOGGLE) {
            if (pressed) {
                hotkey.active = !hotkey.active;
                hotkey.activeConsumer.accept(Boolean.valueOf(hotkey.active));
                return true;
            }
            return false;
        }
        if (hotkey.active == pressed) {
            return false;
        }
        hotkey.active = pressed;
        hotkey.activeConsumer.accept(Boolean.valueOf(hotkey.active));
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/key/DefaultHotkeyService$Hotkey.class */
    public static class Hotkey {
        private final Supplier<Key> keySupplier;
        private final BooleanSupplier condition;
        private final Supplier<HotkeyService.Type> typeSupplier;
        private final Consumer<Boolean> activeConsumer;
        private final String namespace;
        private boolean active;

        public Hotkey(Supplier<Key> keySupplier, BooleanSupplier condition, Supplier<HotkeyService.Type> typeSupplier, Consumer<Boolean> activeConsumer) {
            this.keySupplier = keySupplier;
            this.condition = condition;
            this.typeSupplier = typeSupplier;
            this.activeConsumer = activeConsumer;
            String namespace = Laby.labyAPI().getNamespace(activeConsumer);
            this.namespace = namespace.equals("labymod") ? null : namespace;
        }
    }
}
