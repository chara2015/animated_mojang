package net.labymod.v1_16_5.client.resources.texture;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/resources/texture/NativeGameImage.class */
public class NativeGameImage implements GameImage {
    private final det image;

    public NativeGameImage(det image) {
        this.image = image;
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getWidth() {
        return this.image.a();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getHeight() {
        return this.image.b();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getRGBA(int x, int y) {
        return this.image.a(x, y);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void setRGBA(int x, int y, int rgba) {
        this.image.a(x, y, rgba);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void setARGB(int x, int y, int argb) {
        int abgr = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, argb);
        this.image.a(x, y, abgr);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getARGB(int x, int y) {
        return ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, this.image.a(x, y));
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public GameImage getSubImage(int x, int y, int width, int height) {
        det destination = new det(width, height, true);
        NativeImageUtils.fillSubImage(this.image, destination, x, y, width, height);
        return new NativeGameImage(destination);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void write(String format, Path path) throws IOException {
        this.image.a(path);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void write(String format, OutputStream outputStream) throws IOException {
        this.image.writeToStream(outputStream);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public GameImage resize(int x, int y, int width, int height) {
        BufferedImage bufferedImage = getImage();
        BufferedImage newImage = new BufferedImage(width, height, 2);
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, width, height, (ImageObserver) null);
        graphics.dispose();
        return IMAGE_PROVIDER.getImage(newImage);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public BufferedImage getImage() {
        return NativeImageUtils.asBufferedImage(this.image);
    }

    public det getNativeImage() {
        return this.image;
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public boolean isFreed() {
        return this.image.isFreed();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void close() {
        this.image.close();
    }
}
