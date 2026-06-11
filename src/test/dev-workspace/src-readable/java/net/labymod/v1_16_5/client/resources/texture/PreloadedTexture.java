package net.labymod.v1_16_5.client.resources.texture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import net.labymod.api.util.io.IOUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/resources/texture/PreloadedTexture.class */
public class PreloadedTexture extends ejy {
    private static final String ASSETS_PATH = "/assets/%s/%s";

    public PreloadedTexture(vk location) {
        super(location);
    }

    protected a b(ach resourceManager) {
        a textureImage = super.b(resourceManager);
        try {
            textureImage.c();
            return textureImage;
        } catch (IOException cause) {
            String resourcePath = String.format(Locale.ROOT, ASSETS_PATH, this.d.b(), this.d.a());
            InputStream resourceStream = PreloadedTexture.class.getResourceAsStream(resourcePath);
            if (resourceStream == null) {
                return new a(new FileNotFoundException(resourcePath));
            }
            ell metadataSection = new ell(true, true);
            try {
                a image = new a(metadataSection, det.a(resourceStream));
                IOUtil.closeSilent(resourceStream);
                return image;
            } catch (IOException exception) {
                exception.initCause(cause);
                IOUtil.closeSilent(resourceStream);
                return new a(exception);
            }
        }
    }
}
