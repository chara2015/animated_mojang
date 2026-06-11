package net.labymod.core.client.resources.texture.loader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.NoSuchElementException;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.util.io.SafeImageIO;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/HttpTextureLoader.class */
public class HttpTextureLoader implements TextureLoader {
    private final GameImageProvider provider = Laby.references().gameImageProvider();

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public boolean canLoad(URI uri) {
        return uri.getScheme().equals("http") || uri.getScheme().equals("https");
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public void loadTexture(URI uri, @Nullable ResourceLocation location, CompletableTextureImage target) throws IOException {
        String str;
        GameImage gameImage;
        URL webUrl = uri.toURL();
        String url = webUrl.toString();
        int responseCode = -1;
        try {
            Response<WebInputStream> response = Request.ofInputStream().url(url, new Object[0]).executeSync();
            responseCode = response.getStatusCode();
            WebInputStream stream = response.get();
            String skinType = response.getHeaders().get("x-skin-type");
            if (skinType != null && location != null) {
                MinecraftServices.SkinVariant variant = MinecraftServices.SkinVariant.of(skinType);
                String id = variant == null ? MinecraftServices.SkinVariant.CLASSIC.getId() : variant.getId();
                location.metadata().set("variant", id);
            }
            String contentType = response.getHeaders().get("Content-Type");
            InputStream input = new BufferedInputStream(stream.getInputStream());
            input.mark(16);
            byte[] sig = new byte[6];
            int read = input.read(sig, 0, 6);
            input.reset();
            String signature = read >= 6 ? new String(sig, 0, 6, StandardCharsets.US_ASCII) : "";
            boolean headerGif = "GIF87a".equals(signature) || "GIF89a".equals(signature);
            boolean isGif = headerGif || "image/gif".equals(contentType);
            if (isGif) {
                gameImage = DataTextureLoader.loadGifFirstFrame(input, this.provider);
            } else if ("image/png".equals(contentType)) {
                gameImage = loadPngGameImage(input);
            } else {
                gameImage = loadGameImage(input);
            }
            TextureImage textureImage = new TextureImage(gameImage);
            target.executeCompletableListeners(textureImage);
        } catch (NoSuchElementException exception) {
            target.stopLoadingOnError();
            if (responseCode == -1) {
                str = "Texture could not be downloaded";
            } else {
                str = String.format(Locale.ROOT, "Texture download failed because the URL %s returned a response code of %s", webUrl, Integer.valueOf(responseCode));
            }
            throw new IOException(str, exception);
        }
    }

    private GameImage loadPngGameImage(InputStream inputStream) throws IOException {
        return this.provider.getImage(inputStream);
    }

    private GameImage loadGameImage(InputStream inputStream) throws IOException {
        try {
            return this.provider.getImage(SafeImageIO.read(inputStream));
        } catch (Exception e) {
            throw new IOException("Failed to load webp image", e);
        }
    }
}
