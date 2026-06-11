package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/Forwarder.class */
public interface Forwarder {
    boolean requiresForwarding(Widget widget, String str);

    void forward(Widget widget, String str, ProcessedObject processedObject) throws Exception;

    Class<?> getType(Widget widget, String str, String str2);

    int getPriority();

    default void forwardArray(Widget widget, String key, ProcessedObject... objects) throws Exception {
    }

    default void forward(Widget widget, String key, ProcessedObject... objects) throws Exception {
        if (objects == null) {
            forward(widget, key, (ProcessedObject) null);
        } else if (objects.length == 1) {
            forward(widget, key, objects[0]);
        } else {
            forwardArray(widget, key, objects);
        }
    }

    default void reset(Widget widget, String key) {
    }
}
