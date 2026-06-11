package net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/labyconnect/chat/attachment/AttachmentWidget.class */
@AutoWidget
public class AttachmentWidget<T extends Attachment> extends SimpleWidget {
    protected final T attachment;
    private int version;

    public AttachmentWidget(T attachment) {
        this.attachment = attachment;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.version = this.attachment.getVersion();
    }

    public int getVersion() {
        return this.version;
    }

    public T attachment() {
        return this.attachment;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return this.attachment.getWidth() + bounds().getHorizontalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return this.attachment.getHeight() + bounds().getVerticalOffset(type);
    }
}
