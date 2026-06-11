package net.labymod.core.client.resources.texture.loader;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.core.uri.loader.LinkAttachmentLoader;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/ProxiedHttpTextureLoader.class */
public class ProxiedHttpTextureLoader implements TextureLoader {
    private final TextureRepository textureRepository;

    public ProxiedHttpTextureLoader(TextureRepository textureRepository) {
        this.textureRepository = textureRepository;
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public boolean canLoad(URI uri) {
        return uri.getScheme().equals("labyproxy") || uri.getScheme().equals("labyproxys");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public void loadTexture(URI uri, @Nullable ResourceLocation location, CompletableTextureImage target) throws IOException {
        String strReplaceFirst;
        switch (uri.getScheme()) {
            case "labyproxy":
                strReplaceFirst = uri.toString().replaceFirst("labyproxy://", "http://");
                break;
            case "labyproxys":
                strReplaceFirst = uri.toString().replaceFirst("labyproxys://", "https://");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + uri.getScheme());
        }
        String httpUri = strReplaceFirst;
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            throw new IOException("Texture could not be downloaded: Not connected to LabyConnect");
        }
        TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.JWT, session.self().getUniqueId());
        if (token == null || token.isExpired()) {
            throw new IOException("Texture could not be downloaded: No JWT token found");
        }
        Response<T> responseExecuteSync = ((GsonRequest) ((GsonRequest) Request.ofGson(LinkAttachmentLoader.LinkPreviewResponse.class).url("https://link-preview.laby.net/request-preview?url=" + URLEncoder.encode(httpUri, StandardCharsets.UTF_8), new Object[0])).authorization("Bearer", token.getToken())).executeSync();
        if (responseExecuteSync.getStatusCode() != 200) {
            throw new IOException("Texture could not be downloaded: Response code " + responseExecuteSync.getStatusCode());
        }
        if (!"image".equals(((LinkAttachmentLoader.LinkPreviewResponse) responseExecuteSync.get()).getType())) {
            target.stopLoadingOnError();
        } else {
            this.textureRepository.executeTextureLoader(((LinkAttachmentLoader.LinkPreviewResponse) responseExecuteSync.get()).getImageUrl(), target);
        }
    }
}
