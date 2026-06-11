package net.labymod.core.client.resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.SafeImageIO;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/BufferedGameImage.class */
public class BufferedGameImage implements GameImage {
    private final BufferedImage bufferedImage;

    public BufferedGameImage(InputStream stream) throws IOException {
        this(loadImage(stream));
    }

    public BufferedGameImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getWidth() {
        return this.bufferedImage.getWidth();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getHeight() {
        return this.bufferedImage.getHeight();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getRGBA(int x, int y) {
        return this.bufferedImage.getRGB(x, y);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void setRGBA(int x, int y, int rgba) {
        this.bufferedImage.setRGB(x, y, rgba);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void setARGB(int x, int y, int argb) {
        this.bufferedImage.setRGB(x, y, argb);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getARGB(int x, int y) {
        return this.bufferedImage.getRGB(x, y);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public GameImage getSubImage(int x, int y, int width, int height) {
        int[] rgb = this.bufferedImage.getRGB(x, y, width, height, (int[]) null, 0, width);
        BufferedGameImage bufferedGameImage = new BufferedGameImage(new BufferedImage(width, height, 2));
        int index = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                bufferedGameImage.setARGB(w, h, rgb[index]);
                index++;
            }
        }
        return bufferedGameImage;
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void write(String format, Path path) throws IOException {
        ImageIO.write(createWritableImage(), format, IOUtil.toFile(path));
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void write(String format, OutputStream outputStream) throws IOException {
        ImageIO.write(createWritableImage(), format, outputStream);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public GameImage resize(int x, int y, int width, int height) {
        BufferedImage resizedBufferedImage = new BufferedImage(width, height, getImage().getType());
        Graphics2D graphics = resizedBufferedImage.createGraphics();
        graphics.drawImage(this.bufferedImage, 0, 0, width, height, (ImageObserver) null);
        graphics.dispose();
        return IMAGE_PROVIDER.getImage(resizedBufferedImage);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public BufferedImage getImage() {
        return this.bufferedImage;
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void close() {
    }

    private BufferedImage createWritableImage() {
        int width = getWidth();
        int height = getHeight();
        BufferedImage image = new BufferedImage(width, height, 2);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, this.bufferedImage.getRGB(x, y));
            }
        }
        return image;
    }

    private static BufferedImage loadImage(InputStream stream) throws IOException {
        BufferedImage originalImage = SafeImageIO.read(stream);
        if (originalImage == null) {
            throw new IOException("Failed to decode image");
        }
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), 2);
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, (ImageObserver) null);
        graphics.dispose();
        return newImage;
    }
}
