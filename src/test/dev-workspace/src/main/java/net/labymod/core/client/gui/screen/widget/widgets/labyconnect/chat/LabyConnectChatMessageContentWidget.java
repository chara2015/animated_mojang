package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat;

import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/chat/LabyConnectChatMessageContentWidget.class */
@AutoWidget
public class LabyConnectChatMessageContentWidget extends VerticalListWidget<Widget> {
    private final TextChatMessage message;
    private final ComponentWidget componentMessageWidget;

    public LabyConnectChatMessageContentWidget(TextChatMessage message) {
        this.message = message;
        this.componentMessageWidget = ComponentWidget.component(PlainTextComponentSerializer.plainUrl().deserialize(message.getMessage()));
        this.componentMessageWidget.addId("component-message", "tile");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (!this.message.getMessage().isEmpty()) {
            addChild(this.componentMessageWidget);
        }
        for (Attachment attachment : this.message.getAttachments()) {
            AttachmentWidget<?> widget = attachment.createWidget();
            widget.addId("attachment", "tile");
            addChild(widget);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        for (T widget : this.children) {
            if (widget instanceof AttachmentWidget) {
                AttachmentWidget<?> attachmentWidget = (AttachmentWidget) widget;
                int widgetVersion = attachmentWidget.getVersion();
                if (widgetVersion != attachmentWidget.attachment().getVersion()) {
                    attachmentWidget.reInitialize();
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        float width = this.componentMessageWidget.getContentWidth(BoundsType.OUTER);
        for (Attachment attachment : this.message.getAttachments()) {
            width = Math.max(width, attachment.getWidth());
        }
        return width + bounds().getHorizontalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        float height = 0.0f;
        if (!this.message.getMessage().isEmpty()) {
            height = 0.0f + this.componentMessageWidget.getContentHeight(BoundsType.OUTER);
        }
        for (Attachment attachment : this.message.getAttachments()) {
            height += attachment.getHeight();
        }
        float spaceBetweenEntries = spaceBetweenEntries().get().floatValue();
        if (spaceBetweenEntries > 0.0f && this.children.size() > 1) {
            height += spaceBetweenEntries * (this.children.size() - 1);
        }
        return height + bounds().getVerticalOffset(type);
    }
}
