package net.labymod.core.uri.loader;

import java.net.URI;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.uri.loader.AttachmentLoader;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.link.LinkAttachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/uri/loader/LinkAttachmentLoader.class */
public class LinkAttachmentLoader implements AttachmentLoader<LinkAttachment> {
    private final ExecutorService executorService = LabyExecutors.newSingleThreadExecutor("LinkAttachment#%d");

    @Override // net.labymod.api.uri.loader.AttachmentLoader
    public LinkAttachment create(URI uri) {
        LinkAttachment attachment = new LinkAttachment(uri);
        this.executorService.execute(() -> {
            loadAttachment(attachment);
        });
        return attachment;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void loadAttachment(LinkAttachment attachment) {
        TokenStorage.Token token;
        LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        LabyConnectSession session = labyConnect.getSession();
        if (session == null || (token = session.tokenStorage().getToken(TokenStorage.Purpose.JWT, session.self().getUniqueId())) == null || token.isExpired()) {
            return;
        }
        URI uri = attachment.getURI();
        try {
            String url = uri.toURL().toString();
            Response<T> responseExecuteSync = ((GsonRequest) ((GsonRequest) Request.ofGson(LinkPreviewResponse.class).url("https://link-preview.laby.net/request-preview?url=" + URLEncoder.encode(url, "UTF-8"), new Object[0])).authorization("Bearer", token.getToken())).executeSync();
            if (responseExecuteSync.getStatusCode() < 400) {
                LinkPreviewResponse preview = (LinkPreviewResponse) responseExecuteSync.get();
                boolean update = false;
                if (preview.getTitle() != null) {
                    attachment.setTitle(Component.text(preview.getTitle()));
                    update = true;
                }
                if (preview.getDescription() != null) {
                    attachment.setDescription(Component.text(preview.getDescription()));
                    update = true;
                }
                if (preview.getImageUrl() != null) {
                    attachment.setIcon(Icon.url(preview.getImageUrl()));
                    update = true;
                }
                if (update) {
                    attachment.update();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/uri/loader/LinkAttachmentLoader$LinkPreviewResponse.class */
    public static class LinkPreviewResponse {
        private String imageUrl;
        private String title;
        private String description;
        private String size;
        private String type;
        private String themeColor;

        public String getImageUrl() {
            return this.imageUrl;
        }

        public String getTitle() {
            return this.title;
        }

        public String getDescription() {
            return this.description;
        }

        public String getSize() {
            return this.size;
        }

        public String getType() {
            return this.type;
        }

        public String getThemeColor() {
            return this.themeColor;
        }
    }
}
