package net.labymod.v26_2_snapshot_8.client.resources.texture;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import net.labymod.api.client.resources.texture.GameImage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/resources/texture/NativeGameImage.class */
public final class NativeGameImage extends Record implements GameImage {
    private final NativeImage image;

    public NativeGameImage(NativeImage image) {
        this.image = image;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NativeGameImage.class), NativeGameImage.class, "image", "FIELD:Lnet/labymod/v26_2_snapshot_8/client/resources/texture/NativeGameImage;->image:Lcom/mojang/blaze3d/platform/NativeImage;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NativeGameImage.class), NativeGameImage.class, "image", "FIELD:Lnet/labymod/v26_2_snapshot_8/client/resources/texture/NativeGameImage;->image:Lcom/mojang/blaze3d/platform/NativeImage;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NativeGameImage.class, Object.class), NativeGameImage.class, "image", "FIELD:Lnet/labymod/v26_2_snapshot_8/client/resources/texture/NativeGameImage;->image:Lcom/mojang/blaze3d/platform/NativeImage;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public NativeImage image() {
        return this.image;
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getWidth() {
        return this.image.getWidth();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getHeight() {
        return this.image.getHeight();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getRGBA(int x, int y) {
        return this.image.getPixel(x, y);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void setRGBA(int x, int y, int rgba) {
        this.image.setPixel(x, y, rgba);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void setARGB(int x, int y, int argb) {
        this.image.setPixel(x, y, argb);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public int getARGB(int x, int y) {
        return this.image.getPixel(x, y);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public GameImage getSubImage(int x, int y, int width, int height) {
        NativeImage destination = new NativeImage(width, height, true);
        NativeImageUtils.fillSubImage(this.image, destination, x, y, width, height);
        return new NativeGameImage(destination);
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void write(String format, Path path) throws IOException {
        this.image.writeToFile(path);
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

    @Override // net.labymod.api.client.resources.texture.GameImage
    public boolean isFreed() {
        return this.image.isFreed();
    }

    @Override // net.labymod.api.client.resources.texture.GameImage
    public void close() {
        this.image.close();
    }
}
