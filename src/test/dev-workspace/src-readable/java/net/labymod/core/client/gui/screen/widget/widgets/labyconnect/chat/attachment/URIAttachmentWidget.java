package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.attachment;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/chat/attachment/URIAttachmentWidget.class */
@AutoWidget
public class URIAttachmentWidget extends AttachmentWidget<AbstractURIAttachment> {
    public URIAttachmentWidget(AbstractURIAttachment attachment) {
        super(attachment);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        Component buttonComponent = ((AbstractURIAttachment) this.attachment).getButtonComponent();
        FlexibleContentWidget contentWidget = new FlexibleContentWidget();
        contentWidget.addId("attachment-content");
        if (buttonComponent == null && ((AbstractURIAttachment) this.attachment).isClickable()) {
            AbstractURIAttachment abstractURIAttachment = (AbstractURIAttachment) this.attachment;
            Objects.requireNonNull(abstractURIAttachment);
            contentWidget.setPressable(abstractURIAttachment::open);
            contentWidget.setHoverCursor(CursorTypes.POINTING_HAND, true);
            addId("clickable");
        }
        IconWidget iconWidget = new IconWidget(((AbstractURIAttachment) this.attachment).getIcon());
        iconWidget.addId("attachment-icon");
        contentWidget.addContent(iconWidget);
        FlexibleContentWidget textWidget = new FlexibleContentWidget();
        textWidget.addId("attachment-text");
        Component title = ((AbstractURIAttachment) this.attachment).getTitle();
        if (title != null) {
            ComponentWidget titleWidget = ComponentWidget.component(title);
            titleWidget.addId("attachment-title");
            textWidget.addContent(titleWidget);
        }
        Component description = ((AbstractURIAttachment) this.attachment).getDescription();
        if (description != null) {
            ComponentWidget descriptionWidget = ComponentWidget.component(description);
            descriptionWidget.addId("attachment-description");
            textWidget.addFlexibleContent(descriptionWidget);
        }
        if (textWidget.getChildren().isEmpty()) {
            iconWidget.addId("attachment-preview");
        } else {
            contentWidget.addFlexibleContent(textWidget);
        }
        if (buttonComponent != null) {
            ButtonWidget buttonWidget = ButtonWidget.component(buttonComponent);
            buttonWidget.addId("attachment-button", "accent-button");
            AbstractURIAttachment abstractURIAttachment2 = (AbstractURIAttachment) this.attachment;
            Objects.requireNonNull(abstractURIAttachment2);
            buttonWidget.setPressable(abstractURIAttachment2::open);
            contentWidget.addContent(buttonWidget);
        }
        addChild(contentWidget);
    }
}
