package net.labymod.api.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.scaler.ImageScaler;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/GameImage.class */
public interface GameImage {
    public static final GameImageProvider IMAGE_PROVIDER = Laby.references().gameImageProvider();

    int getWidth();

    int getHeight();

    @Deprecated(forRemoval = true, since = "4.2.6")
    int getRGBA(int i, int i2);

    @Deprecated(forRemoval = true, since = "4.2.6")
    void setRGBA(int i, int i2, int i3);

    void setARGB(int i, int i2, int i3);

    int getARGB(int i, int i2);

    GameImage getSubImage(int i, int i2, int i3, int i4);

    void write(String str, Path path) throws IOException;

    void write(String str, OutputStream outputStream) throws IOException;

    GameImage resize(int i, int i2, int i3, int i4);

    BufferedImage getImage();

    void close();

    default void setNoAlpha(int x, int y) {
        setARGB(x, y, ColorFormat.ARGB32.pack(getARGB(x, y), 255));
    }

    default void setNoAlpha(int x, int y, int width, int height) {
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                setNoAlpha(x + dx, y + dy);
            }
        }
    }

    default void blendPixel(int x, int y, int overlayARGB) {
        setARGB(x, y, ColorUtil.blendColors(getARGB(x, y), overlayARGB));
    }

    default void drawImage(GameImage image, int dx, int dy, int sx, int sy, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = image.getARGB(x + sx, y + sy);
                setARGB(x + dx, y + dy, argb);
            }
        }
    }

    default void copyFrom(GameImage texture) {
        drawImage(texture, 0, 0, 0, 0, texture.getWidth(), texture.getHeight());
    }

    default void fillRect(int x, int y, int width, int height, int rgba) {
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                setARGB(x + dx, y + dy, rgba);
            }
        }
    }

    default void copyRect(int fromX, int fromY, int offsetX, int offsetY, int width, int height, boolean mirrorX, boolean mirrorY) {
        copyRect(this, fromX, fromY, fromX + offsetX, fromY + offsetY, width, height, mirrorX, mirrorY);
    }

    default void copyRect(GameImage image, int fromX, int fromY, int toX, int toY, int width, int height, boolean mirrorX, boolean mirrorY) {
        for (int yRead = 0; yRead < height; yRead++) {
            for (int xRead = 0; xRead < width; xRead++) {
                int xWrite = mirrorX ? (width - 1) - xRead : xRead;
                int yWrite = mirrorY ? (height - 1) - yRead : yRead;
                int argb = getARGB(fromX + xRead, fromY + yRead);
                image.setARGB(toX + xWrite, toY + yWrite, argb);
            }
        }
    }

    default void drawImageOverlay(GameImage topImage, int dx, int dy, int sx, int sy, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int top = topImage.getARGB(x + sx, y + sy);
                blendPixel(x + dx, y + dy, top);
            }
        }
    }

    default void flipHorizontally(int x, int y, int width, int height) {
        int right = (x + width) - 1;
        for (int dx = 0; dx < width / 2; dx++) {
            for (int dy = 0; dy < height; dy++) {
                int l = x + dx;
                int r = right - dx;
                if (l != r) {
                    int lr = getARGB(l, y + dy);
                    int rr = getARGB(r, y + dy);
                    setARGB(l, y + dy, rr);
                    setARGB(r, y + dy, lr);
                }
            }
        }
    }

    default void swap(int x1, int y1, int x2, int y2, int width, int height) {
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                int rgba1 = getARGB(x1 + dx, y1 + dy);
                int rgba2 = getARGB(x2 + dx, y2 + dy);
                setARGB(x1 + dx, y1 + dy, rgba2);
                setARGB(x2 + dx, y2 + dy, rgba1);
            }
        }
    }

    default GameImage scale(int width, int height) {
        return scale(ImageScaler.NEAREST_NEIGHBOR, width, height);
    }

    default GameImage scale(@NotNull ImageScaler scaler, int newWidth, int newHeight) {
        return scaler.scale(this, newWidth, newHeight);
    }

    default boolean isFreed() {
        return false;
    }

    default void uploadTextureAt(ResourceLocation resourceLocation) {
        final SimpleTexture texture = SimpleTexture.simple(resourceLocation, this);
        texture.bindTo(new TextureRepository.TextureRegistrationCallback(this) { // from class: net.labymod.api.client.resources.texture.GameImage.1
            @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
            public void onBeforeTextureRegistration() {
                texture.upload();
            }

            @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
            public void onAfterTextureRegistration() {
            }
        });
    }
}
