package net.labymod.v26_2_snapshot_8.client.gui;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gui/EditBoxAccessor.class */
public interface EditBoxAccessor {
    void setValueConsumer(Consumer<String> consumer);

    boolean isEditable();

    Component getHint();
}
