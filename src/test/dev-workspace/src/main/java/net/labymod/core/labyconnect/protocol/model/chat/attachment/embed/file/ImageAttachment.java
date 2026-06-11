package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ImageViewerWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/embed/file/ImageAttachment.class */
public class ImageAttachment extends FileAttachment {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 100;
    private Icon image;

    public ImageAttachment(URI uri, String type, UUID identifier) {
        super(uri, type, identifier);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment, net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Icon getIcon() {
        if (this.image != null) {
            return this.image;
        }
        byte[] fileData = getFileData();
        if (fileData == null) {
            return Textures.SpriteCommon.QUESTION_MARK;
        }
        try {
            GameImage image = Laby.references().gameImageProvider().getImage(new ByteArrayInputStream(fileData));
            ResourceLocation resourceLocation = Laby.references().resources().resourceLocationFactory().createLabyMod("labyconnect_file/" + String.valueOf(this.identifier));
            image.uploadTextureAt(resourceLocation);
            Icon icon = Icon.texture(resourceLocation);
            icon.resolution(image.getWidth(), image.getHeight());
            this.image = icon;
            return icon;
        } catch (Exception e) {
            e.printStackTrace();
            return Textures.SpriteCommon.QUESTION_MARK;
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    public float getWidth() {
        if (this.image == null) {
            return 50.0f;
        }
        float width = 100.0f * this.image.getAspectRatio();
        if (width > 200.0f) {
            return 200.0f;
        }
        return width;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    public float getHeight() {
        if (this.image == null) {
            return 50.0f;
        }
        float width = 100.0f * this.image.getAspectRatio();
        if (width > 200.0f) {
            return 200.0f / this.image.getAspectRatio();
        }
        return 100.0f;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment, net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getTitle() {
        return null;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment, net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getDescription() {
        return null;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment, net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public boolean isClickable() {
        return true;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment, net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment
    public void open() {
        if (this.image == null) {
            return;
        }
        ImageViewerWidget imageViewerWidget = new ImageViewerWidget(this.image);
        imageViewerWidget.displayInOverlay(imageViewerWidget);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment, net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    @NotNull
    public Component toComponent() {
        return Component.text("Image Attachment" + (this.image == null ? "" : " " + this.image.getResolutionWidth() + "x" + this.image.getResolutionHeight()));
    }
}
