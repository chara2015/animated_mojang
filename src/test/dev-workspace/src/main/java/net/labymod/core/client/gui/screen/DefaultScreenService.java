package net.labymod.core.client.gui.screen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenFactory;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenName;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/DefaultScreenService.class */
@Singleton
@Implements(ScreenService.class)
public class DefaultScreenService implements ScreenService {
    private Function<Object, Boolean> inventoryCondition = screen -> {
        throw new UnsupportedOperationException();
    };
    private final Map<String, ScreenName> screens = new HashMap();
    private final Map<String, ScreenFactory> factories = new HashMap();

    @Inject
    public DefaultScreenService() {
    }

    @Override // net.labymod.api.client.gui.screen.ScreenService
    public void register(String name, ScreenName screenName) {
        this.screens.putIfAbsent(name, screenName);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenService
    public void registerFactory(String name, ScreenFactory factory) {
        this.factories.put(name, factory);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenService
    public ScreenInstance createScreen(String name) {
        ScreenFactory factory = this.factories.get(name);
        if (factory != null) {
            return factory.create();
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenService
    public String getScreenNameByClass(Class<?> screenClass) {
        for (Map.Entry<String, ScreenName> entry : this.screens.entrySet()) {
            if (entry.getValue().getIdentifier().equals(screenClass)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenService
    public boolean isInventory(Object screen) {
        return this.inventoryCondition.apply(screen).booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.ScreenService
    public void setInventoryCondition(Function<Object, Boolean> inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }
}
