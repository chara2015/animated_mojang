package net.labymod.api.client.gui.screen.widget.action;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/action/Pressable.class */
@FunctionalInterface
public interface Pressable {
    public static final Pressable NOOP = () -> {
    };

    void press();
}
