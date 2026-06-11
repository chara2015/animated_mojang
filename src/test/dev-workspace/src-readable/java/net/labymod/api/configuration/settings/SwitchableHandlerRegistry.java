package net.labymod.api.configuration.settings;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/SwitchableHandlerRegistry.class */
@Referenceable
public interface SwitchableHandlerRegistry {
    void registerHandler(SwitchableHandler switchableHandler);

    SwitchableHandler getHandler(Class<? extends SwitchableHandler> cls);
}
