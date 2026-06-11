package net.labymod.v1_21_3.client.gui;

import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gui/EditBoxAccessor.class */
public interface EditBoxAccessor {
    void setValueConsumer(Consumer<String> consumer);

    boolean isEditable();

    xv getHint();
}
