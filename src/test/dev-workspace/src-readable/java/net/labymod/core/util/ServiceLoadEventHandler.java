package net.labymod.core.util;

import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.Event;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.event.labymod.SubscribeMethodRegisterEvent;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.core.addon.DefaultAddonService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/ServiceLoadEventHandler.class */
@Singleton
public class ServiceLoadEventHandler {
    @Inject
    public ServiceLoadEventHandler() {
        loadAllServices(Laby::fireEvent);
    }

    @Subscribe
    public void loadAllServices(SubscribeMethodRegisterEvent event) {
        SubscribeMethod method = event.method();
        if (method.getEventType() != ServiceLoadEvent.class) {
            return;
        }
        loadAllServices(e -> {
            event.registry().invokeMethod(method, e);
        });
    }

    private void loadAllServices(Consumer<Event> consumer) {
        consumer.accept(new ServiceLoadEvent(super.getClass().getClassLoader(), ServiceLoadEvent.State.LISTENER_REGISTERED));
        for (LoadedAddon addon : DefaultAddonService.getInstance().getLoadedAddons()) {
            consumer.accept(new ServiceLoadEvent(addon.getClassLoader(), ServiceLoadEvent.State.LISTENER_REGISTERED));
        }
    }
}
