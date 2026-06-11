package net.labymod.api.labyconnect.protocol.model.chat.attachment;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/chat/attachment/Attachment.class */
public interface Attachment {
    @NotNull
    AttachmentWidget<?> createWidget();

    float getWidth();

    float getHeight();

    void update();

    int getVersion();

    @NotNull
    Component toComponent();
}
