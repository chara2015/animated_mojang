package net.labymod.v1_8_9.client.gui;

import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/GuiTextFieldAccessor.class */
public interface GuiTextFieldAccessor {
    void setWidth(int i);

    void setHeight(int i);

    int getHeight();

    boolean isEnabled();

    void setTextConsumer(Consumer<String> consumer);
}
