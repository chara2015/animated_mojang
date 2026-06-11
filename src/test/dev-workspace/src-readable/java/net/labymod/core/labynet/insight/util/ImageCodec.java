package net.labymod.core.labynet.insight.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.SafeImageIO;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/util/ImageCodec.class */
public class ImageCodec {
    private final Map<String, String> metaData;
    private SourceImage sourceImage;
    private ImagePostProcessor imagePostProcessor;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/util/ImageCodec$ImagePostProcessor.class */
    public interface ImagePostProcessor {
        BufferedImage process(BufferedImage bufferedImage);
    }

    public ImageCodec(File file) throws IOException {
        this(file.toPath());
        this.sourceImage = new SourceImage(file);
    }

    public ImageCodec(Path path) throws IOException {
        this(IOUtil.newInputStream(path));
        this.sourceImage = new SourceImage(path);
    }

    public ImageCodec(BufferedImage image) throws IOException {
        this.metaData = new HashMap();
        this.sourceImage = new SourceImage(image);
    }

    public ImageCodec(byte[] data) throws IOException {
        this(new ByteArrayInputStream(data));
        this.sourceImage = new SourceImage(data);
    }

    public ImageCodec(InputStream inputStream) throws IOException {
        this.metaData = new HashMap();
        ImageIO.setUseCache(false);
        try {
            ImageInputStream stream = ImageIO.createImageInputStream(inputStream);
            ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("png").next();
            reader.setInput(stream);
            IIOMetadata metadata = reader.getImageMetadata(0);
            IIOMetadataNode root = metadata.getAsTree("javax_imageio_1.0");
            NodeList entries = root.getElementsByTagName("TextEntry");
            for (int i = 0; i < entries.getLength(); i++) {
                IIOMetadataNode node = entries.item(i);
                String keyword = node.getAttribute("keyword");
                String value = node.getAttribute("value");
                this.metaData.put(keyword, value);
            }
        } catch (IIOException e) {
            System.out.println("Failed to read meta data from png file: " + e.getMessage());
        }
        ImageIO.setUseCache(true);
    }

    private IIOMetadataNode compileNode() {
        IIOMetadataNode imageNode = new IIOMetadataNode("javax_imageio_png_1.0");
        IIOMetadataNode textNode = new IIOMetadataNode("tEXt");
        for (Map.Entry<String, String> entry : this.metaData.entrySet()) {
            IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
            textEntry.setAttribute("keyword", entry.getKey());
            textEntry.setAttribute("value", entry.getValue());
            textNode.appendChild(textEntry);
        }
        imageNode.appendChild(textNode);
        return imageNode;
    }

    public void compileTo(OutputStream out) throws IOException {
        ImageWriter writer = (ImageWriter) ImageIO.getImageWritersByFormatName("png").next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(1);
        IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
        if (metadata != null) {
            metadata.mergeTree("javax_imageio_png_1.0", compileNode());
        }
        ImageOutputStream stream = ImageIO.createImageOutputStream(out);
        try {
            writer.setOutput(stream);
            writer.write(metadata, new IIOImage(readImage(), (List) null, metadata), writeParam);
            if (stream != null) {
                stream.close();
            }
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public byte[] compileWithMeta() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compileTo(baos);
        return baos.toByteArray();
    }

    public byte[] compile(String format) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(readImage(), format, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void compileToFileWithMeta(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(compileWithMeta());
            fos.close();
        } catch (Throwable th) {
            try {
                fos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void compileToFile(File file, String format) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(compile(format));
            fos.close();
        } catch (Throwable th) {
            try {
                fos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public BufferedImage readImage() throws IOException {
        BufferedImage image = this.sourceImage.get();
        return this.imagePostProcessor == null ? image : this.imagePostProcessor.process(image);
    }

    public void setImagePostProcessor(ImagePostProcessor imagePostProcessor) {
        this.imagePostProcessor = imagePostProcessor;
    }

    public void set(String key, String value) {
        this.metaData.put(key, value);
    }

    public void remove(String key) {
        this.metaData.remove(key);
    }

    public boolean has(String key) {
        return this.metaData.containsKey(key);
    }

    public String get(String key) {
        return this.metaData.get(key);
    }

    public Map<String, String> map() {
        return this.metaData;
    }

    public static String getAvailableFormat(String... formats) {
        for (String format : formats) {
            if (ImageIO.getImageWritersByFormatName(format).hasNext()) {
                try {
                    BufferedImage testImage = new BufferedImage(1, 1, 2);
                    ImageIO.write(testImage, format, new ByteArrayOutputStream());
                    return format;
                } catch (Throwable th) {
                }
            }
        }
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/util/ImageCodec$SourceImage.class */
    private static class SourceImage {
        private final Object object;

        public SourceImage(Object object) {
            this.object = object;
        }

        public BufferedImage get() throws IOException {
            InputStream stream;
            if (this.object instanceof Path) {
                stream = IOUtil.newInputStream((Path) this.object);
                try {
                    BufferedImage bufferedImage = SafeImageIO.read(stream);
                    if (stream != null) {
                        stream.close();
                    }
                    return bufferedImage;
                } finally {
                }
            }
            if (this.object instanceof File) {
                stream = IOUtil.newInputStream(((File) this.object).toPath());
                try {
                    BufferedImage bufferedImage2 = SafeImageIO.read(stream);
                    if (stream != null) {
                        stream.close();
                    }
                    return bufferedImage2;
                } finally {
                }
            }
            if (this.object instanceof byte[]) {
                return SafeImageIO.read(new ByteArrayInputStream((byte[]) this.object));
            }
            if (this.object instanceof InputStream) {
                return SafeImageIO.read((InputStream) this.object);
            }
            if (this.object instanceof BufferedImage) {
                return (BufferedImage) this.object;
            }
            return null;
        }
    }
}
