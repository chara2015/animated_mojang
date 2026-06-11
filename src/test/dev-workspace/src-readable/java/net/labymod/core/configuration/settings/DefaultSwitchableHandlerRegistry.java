package net.labymod.core.configuration.settings;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import net.labymod.api.configuration.settings.SwitchableHandler;
import net.labymod.api.configuration.settings.SwitchableHandlerRegistry;
import net.labymod.api.configuration.settings.switchable.BooleanSwitchableHandler;
import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/settings/DefaultSwitchableHandlerRegistry.class */
@Singleton
@Implements(SwitchableHandlerRegistry.class)
public class DefaultSwitchableHandlerRegistry implements SwitchableHandlerRegistry {
    private final Map<Class<? extends SwitchableHandler>, SwitchableHandler> handlerMap = new HashMap();

    public DefaultSwitchableHandlerRegistry() {
        registerHandler(new BooleanSwitchableHandler());
        registerHandler(new StringSwitchableHandler());
    }

    @Override // net.labymod.api.configuration.settings.SwitchableHandlerRegistry
    public void registerHandler(SwitchableHandler switchableHandler) {
        this.handlerMap.put((Class<? extends SwitchableHandler>) switchableHandler.getClass(), switchableHandler);
    }

    @Override // net.labymod.api.configuration.settings.SwitchableHandlerRegistry
    public SwitchableHandler getHandler(Class<? extends SwitchableHandler> handlerClass) {
        return this.handlerMap.get(handlerClass);
    }
}
