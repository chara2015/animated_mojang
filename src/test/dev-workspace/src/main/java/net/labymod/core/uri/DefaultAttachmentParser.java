package net.labymod.core.uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.api.models.Implements;
import net.labymod.api.uri.AttachmentParser;
import net.labymod.api.uri.URIParser;
import net.labymod.api.uri.loader.AttachmentLoader;
import net.labymod.core.uri.loader.FileAttachmentLoader;
import net.labymod.core.uri.loader.LanAttachmentLoader;
import net.labymod.core.uri.loader.LanServerAttachmentLoader;
import net.labymod.core.uri.loader.LinkAttachmentLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/uri/DefaultAttachmentParser.class */
@Singleton
@Implements(AttachmentParser.class)
public class DefaultAttachmentParser implements AttachmentParser {
    private final Map<String, AttachmentLoader<?>> loaders = new HashMap();

    @Inject
    public DefaultAttachmentParser() {
        registerLoader("http", new LinkAttachmentLoader());
        registerLoader("https", new LinkAttachmentLoader());
        registerLoader("lan", new LanAttachmentLoader());
        registerLoader("file", new FileAttachmentLoader());
        registerLoader("lanserver", new LanServerAttachmentLoader());
    }

    @Override // net.labymod.api.uri.AttachmentParser
    public List<URIAttachment> parse(String text) {
        URIAttachment attachment;
        List<URIAttachment> attachments = new ArrayList<>();
        for (URI uri : URIParser.parse(text)) {
            AttachmentLoader<?> loader = getLoader(uri);
            if (loader != null && (attachment = loader.create(uri)) != null) {
                attachments.add(attachment);
            }
        }
        return attachments;
    }

    @Override // net.labymod.api.uri.AttachmentParser
    public AttachmentLoader<?> getLoader(URI uri) {
        String scheme = uri.getScheme();
        if (scheme == null) {
            return null;
        }
        return this.loaders.get(scheme);
    }

    @Override // net.labymod.api.uri.AttachmentParser
    public void registerLoader(String scheme, AttachmentLoader<?> loader) {
        this.loaders.put(scheme, loader);
    }
}
