package net.labymod.core.client.os.windows.clipboard;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/clipboard/WinGDI32.class */
public interface WinGDI32 extends StdCallLibrary {
    public static final WinGDI32 INSTANCE = Native.load("gdi32", WinGDI32.class);

    boolean SetDIBitsToDevice(WinDef.HDC hdc, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr, WinGDI.BITMAPINFO bitmapinfo, int i9);

    int GetBitmapBits(WinNT.HANDLE handle, int i, byte[] bArr);

    static WinNT.HANDLE toBitMap(GameImage image) {
        WinDef.HDC screen = WinUser.INSTANCE.GetDC(null);
        if (screen == null) {
            throw new RuntimeException("Error GetDC: " + Native.getLastError());
        }
        GDI32 gdi32 = GDI32.INSTANCE;
        WinDef.HDC backBuffer = gdi32.CreateCompatibleDC(screen);
        if (backBuffer == null) {
            throw new RuntimeException("Error CreateCompatibleDC: " + Native.getLastError());
        }
        WinDef.HBITMAP bitMap = gdi32.CreateCompatibleBitmap(screen, image.getWidth(), image.getHeight());
        if (bitMap == null) {
            throw new RuntimeException("Error CreateCompatibleBitmap: " + Native.getLastError());
        }
        if (gdi32.SelectObject(backBuffer, bitMap) == null) {
            throw new RuntimeException("Error SelectObject: " + Native.getLastError());
        }
        WinGDI.BITMAPINFO info = new WinGDI.BITMAPINFO();
        info.bmiHeader.biWidth = image.getWidth();
        info.bmiHeader.biHeight = -image.getHeight();
        info.bmiHeader.biPlanes = (short) 1;
        info.bmiHeader.biBitCount = (short) 32;
        info.bmiHeader.biCompression = 0;
        ColorFormat colorFormat = ColorFormat.ARGB32;
        byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getARGB(x, y);
                byte red = (byte) colorFormat.red(color);
                byte green = (byte) colorFormat.green(color);
                byte blue = (byte) colorFormat.blue(color);
                byte alpha = (byte) colorFormat.alpha(color);
                int index = ((y * image.getWidth()) + x) * 4;
                buffer[index] = blue;
                buffer[index + 1] = green;
                buffer[index + 2] = red;
                buffer[index + 3] = alpha;
            }
        }
        boolean result = INSTANCE.SetDIBitsToDevice(backBuffer, 0, 0, image.getWidth(), image.getHeight(), 0, 0, 0, image.getHeight(), buffer, info, 0);
        if (!result) {
            throw new RuntimeException("Error SetDIBitsToDevice: " + Native.getLastError());
        }
        gdi32.DeleteDC(backBuffer);
        gdi32.DeleteDC(screen);
        return bitMap;
    }

    static GameImage fromBitMap(WinNT.HANDLE data) {
        WinDef.HDC screen = WinUser.INSTANCE.GetDC(null);
        if (screen == null) {
            throw new RuntimeException("Error GetDC: " + Native.getLastError());
        }
        GDI32 gdi32 = GDI32.INSTANCE;
        WinDef.HDC backBuffer = gdi32.CreateCompatibleDC(screen);
        if (backBuffer == null) {
            throw new RuntimeException("Error CreateCompatibleDC: " + Native.getLastError());
        }
        WinGDI.BITMAPINFO info = new WinGDI.BITMAPINFO();
        WinNT.HANDLE hbitmap = new WinDef.HBITMAP(data.getPointer());
        int status = GDI32.INSTANCE.GetDIBits(backBuffer, hbitmap, 0, 0, (Pointer) null, info, 0);
        if (status == 0) {
            throw new RuntimeException("Error GetDIBits: " + Native.getLastError());
        }
        GameImage image = Laby.references().gameImageProvider().createImage(info.bmiHeader.biWidth, info.bmiHeader.biHeight);
        byte[] buffer = new byte[info.bmiHeader.biWidth * info.bmiHeader.biHeight * 4];
        int length = INSTANCE.GetBitmapBits(hbitmap, buffer.length, buffer);
        if (length <= 0) {
            throw new RuntimeException("Error GetBitmapBits: " + Native.getLastError());
        }
        ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < info.bmiHeader.biHeight; y++) {
            for (int x = 0; x < info.bmiHeader.biWidth; x++) {
                int index = ((y * info.bmiHeader.biWidth) + x) * 4;
                byte red = buffer[index + 2];
                byte green = buffer[index + 1];
                byte blue = buffer[index];
                byte alpha = buffer[index + 3];
                int color = colorFormat.pack((int) red, (int) green, (int) blue, (int) alpha);
                image.setARGB(x, y, color);
            }
        }
        gdi32.DeleteDC(backBuffer);
        gdi32.DeleteDC(screen);
        return image;
    }
}
