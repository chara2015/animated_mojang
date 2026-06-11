package net.labymod.core.client.os.linux.clipboard;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.io.LabyExecutors;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/linux/clipboard/GTKLib.class */
public interface GTKLib extends Library {
    public static final GTKLib INSTANCE = load();
    public static final int GDK_COLORSPACE_RGB = 0;

    void gtk_init(int i, String[] strArr);

    void gtk_main();

    void gtk_main_quit();

    Pointer gdk_display_get_default();

    Pointer gtk_clipboard_get_default(Pointer pointer);

    void gtk_clipboard_set_text(Pointer pointer, String str, int i);

    String gtk_clipboard_wait_for_text(Pointer pointer);

    Pointer g_bytes_new(byte[] bArr, int i);

    int g_bytes_get_size(Pointer pointer);

    Pointer g_bytes_get_data(Pointer pointer, Pointer pointer2);

    void g_bytes_unref(Pointer pointer);

    Pointer gdk_pixbuf_new_from_bytes(Pointer pointer, int i, boolean z, int i2, int i3, int i4, int i5);

    boolean gdk_pixbuf_get_has_alpha(Pointer pointer);

    int gdk_pixbuf_get_width(Pointer pointer);

    int gdk_pixbuf_get_height(Pointer pointer);

    Pointer gdk_pixbuf_read_pixel_bytes(Pointer pointer);

    void gtk_clipboard_set_image(Pointer pointer, Pointer pointer2);

    Pointer gtk_clipboard_wait_for_image(Pointer pointer);

    boolean gdk_display_supports_clipboard_persistence(Pointer pointer);

    void gtk_clipboard_store(Pointer pointer);

    void gdk_pixbuf_unref(Pointer pointer);

    static GTKLib load() {
        GTKLib lib = (GTKLib) Native.load("libgtk-3", GTKLib.class);
        lib.gtk_init(0, null);
        Objects.requireNonNull(lib);
        LabyExecutors.executeBackgroundTask(lib::gtk_main);
        return lib;
    }

    static Pointer toPixelBuf(GameImage image) {
        GTKLib lib = INSTANCE;
        int width = image.getWidth();
        int height = image.getHeight();
        boolean hasAlpha = image.getImage().getColorModel().hasAlpha();
        byte[] data = new byte[width * height * (hasAlpha ? 4 : 3)];
        int i = 0;
        ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getARGB(x, y);
                byte red = (byte) colorFormat.red(argb);
                byte green = (byte) colorFormat.green(argb);
                byte blue = (byte) colorFormat.blue(argb);
                byte alpha = (byte) colorFormat.alpha(argb);
                int i2 = i;
                int i3 = i + 1;
                data[i2] = red;
                int i4 = i3 + 1;
                data[i3] = green;
                i = i4 + 1;
                data[i4] = blue;
                if (hasAlpha) {
                    i++;
                    data[i] = alpha;
                }
            }
        }
        Pointer gBytes = lib.g_bytes_new(data, data.length);
        Pointer pixelBuf = lib.gdk_pixbuf_new_from_bytes(gBytes, 0, hasAlpha, 8, width, height, width * (hasAlpha ? 4 : 3));
        lib.g_bytes_unref(gBytes);
        return pixelBuf;
    }

    static GameImage fromPixelBuf(Pointer pixelBuf) {
        int i;
        GTKLib lib = INSTANCE;
        int width = lib.gdk_pixbuf_get_width(pixelBuf);
        int height = lib.gdk_pixbuf_get_height(pixelBuf);
        boolean hasAlpha = lib.gdk_pixbuf_get_has_alpha(pixelBuf);
        Pointer gBytes = lib.gdk_pixbuf_read_pixel_bytes(pixelBuf);
        int size = lib.g_bytes_get_size(gBytes);
        Pointer memory = lib.g_bytes_get_data(gBytes, null);
        byte[] data = memory.getByteArray(0L, size);
        GameImage image = Laby.references().gameImageProvider().createImage(width, height);
        int i2 = 0;
        ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i3 = i2;
                int i4 = i2 + 1;
                int r = data[i3] & 255;
                int i5 = i4 + 1;
                int g = data[i4] & 255;
                i2 = i5 + 1;
                int b = data[i5] & 255;
                if (hasAlpha) {
                    i2++;
                    i = data[i2] & 255;
                } else {
                    i = 255;
                }
                int a = i;
                image.setARGB(x, y, colorFormat.pack(r, g, b, a));
            }
        }
        lib.gdk_pixbuf_unref(pixelBuf);
        return image;
    }
}
