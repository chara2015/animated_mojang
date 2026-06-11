package net.labymod.api.util.io;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/SafeImageIO.class */
public final class SafeImageIO {
    public static final int MAX_TEXTURE_PIXELS = 16777216;
    public static final int MAX_ICON_PIXELS = 65536;

    private SafeImageIO() {
    }

    public static BufferedImage read(InputStream input) throws IOException {
        return read(input, MAX_TEXTURE_PIXELS);
    }

    public static BufferedImage read(InputStream input, int maxPixels) throws IOException {
        if (input == null) {
            return null;
        }
        ImageInputStream stream = ImageIO.createImageInputStream(input);
        if (stream != null) {
            try {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    try {
                        reader.setInput(stream, true, true);
                        int width = reader.getWidth(0);
                        int height = reader.getHeight(0);
                        if (width < 1 || height < 1 || ((long) width) * ((long) height) > maxPixels) {
                            throw new IOException("Image dimensions exceed allowed max (" + width + "x" + height + ", limit " + maxPixels + " pixels)");
                        }
                        BufferedImage bufferedImage = reader.read(0);
                        reader.dispose();
                        if (stream != null) {
                            stream.close();
                        }
                        return bufferedImage;
                    } catch (Throwable th) {
                        reader.dispose();
                        throw th;
                    }
                }
                if (stream != null) {
                    stream.close();
                }
                return null;
            } catch (Throwable th2) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
        if (stream != null) {
            stream.close();
        }
        return null;
    }
}
