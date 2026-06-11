package net.labymod.api.event.client.render;

import java.util.Locale;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.thirdparty.LabySentry;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/StackCheckingEvent.class */
public abstract class StackCheckingEvent implements Event {

    @ApiStatus.Internal
    private int stackIndex;

    public abstract Stack stack();

    @Override // net.labymod.api.event.Event
    @ApiStatus.Internal
    public void preInvoke(SubscribeMethod method) {
        this.stackIndex = stack().index();
    }

    @Override // net.labymod.api.event.Event
    @ApiStatus.Internal
    public void postInvoke(SubscribeMethod method) {
        if (stack().index() == this.stackIndex) {
            return;
        }
        InstalledAddonInfo addon = method.getAddon();
        String namespace = addon == null ? "labymod" : addon.getNamespace();
        String listenerName = method.getListener() == null ? "unknown" : method.getListener().getClass().getName();
        String methodName = method.getMethod() == null ? "unknown" : method.getMethod().getName();
        String type = stack().index() < this.stackIndex ? "Popped" : "Pushed";
        IllegalStateException exception = new IllegalStateException(String.format(Locale.ROOT, type + " too many matrices at %s#%s by %s (expected index: %d, actual index: %d). ", listenerName, methodName, namespace, Integer.valueOf(this.stackIndex), Integer.valueOf(stack().index())));
        LabySentry.capture(exception);
        throw exception;
    }
}
