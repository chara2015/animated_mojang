package net.labymod.v1_20_1.client.gui;

import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/gui/EditBoxAccessor.class */
public interface EditBoxAccessor {
    void setValueConsumer(Consumer<String> consumer);

    boolean isEditable();

    sw getHint();
}
