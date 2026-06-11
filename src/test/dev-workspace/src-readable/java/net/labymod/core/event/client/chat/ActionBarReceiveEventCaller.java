package net.labymod.core.event.client.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/chat/ActionBarReceiveEventCaller.class */
public final class ActionBarReceiveEventCaller {
    public static ActionBarReceiveEvent callPre(Component component, boolean animatedMessage) {
        return call(Phase.PRE, component, animatedMessage);
    }

    public static void callPost(Component component, boolean animatedMessage) {
        call(Phase.POST, component, animatedMessage);
    }

    private static ActionBarReceiveEvent call(Phase phase, Component component, boolean animatedMessage) {
        return (ActionBarReceiveEvent) Laby.fireEvent(new ActionBarReceiveEvent(phase, component, animatedMessage));
    }
}
