package net.labymod.v1_12_2.client.gui.screen;

import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/VersionedFunctionalConfirmScreen.class */
public class VersionedFunctionalConfirmScreen extends blk {
    private final int buttonId;
    private final Consumer<Boolean> consumer;

    public VersionedFunctionalConfirmScreen(int buttonId, Consumer<Boolean> consumer) {
        this.buttonId = buttonId;
        this.consumer = consumer;
    }

    public void a(boolean open, int buttonId) {
        if (buttonId == this.buttonId) {
            this.consumer.accept(Boolean.valueOf(open));
        }
    }
}
