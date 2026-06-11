package net.labymod.core.client.os.windows.clipboard;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/clipboard/WinUser.class */
public interface WinUser extends StdCallLibrary {
    public static final int CF_BITMAP = 2;
    public static final int CF_DIB = 8;
    public static final WinUser INSTANCE = Native.load("user32", WinUser.class);

    boolean OpenClipboard(int i);

    boolean EmptyClipboard();

    boolean CloseClipboard();

    boolean SetClipboardData(int i, WinNT.HANDLE handle);

    WinDef.HDC GetDC(WinDef.HWND hwnd);

    boolean IsClipboardFormatAvailable(int i);

    WinNT.HANDLE GetClipboardData(int i);
}
