package net.labymod.core.event.client.component;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.client.component.ComponentSerializeEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/component/ComponentSerializeEventCaller.class */
public final class ComponentSerializeEventCaller {
    private ComponentSerializeEventCaller() {
    }

    public static ComponentSerializeEvent call(Component component) {
        return (ComponentSerializeEvent) Laby.fireEvent(new ComponentSerializeEvent(component));
    }
}
