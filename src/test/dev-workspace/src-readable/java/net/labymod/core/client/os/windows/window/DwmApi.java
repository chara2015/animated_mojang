package net.labymod.core.client.os.windows.window;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/window/DwmApi.class */
public interface DwmApi extends StdCallLibrary {
    public static final DwmApi INSTANCE = Native.load("dwmapi", DwmApi.class, W32APIOptions.DEFAULT_OPTIONS);
    public static final WinDef.DWORD DWMWA_USE_IMMERSIVE_DARK_MODE = new WinDef.DWORD(20);
    public static final WinDef.DWORD DWMWA_WINDOW_CORNER_PREFERENCE = new WinDef.DWORD(33);
    public static final WinDef.DWORD DWMWA_BORDER_COLOR = new WinDef.DWORD(34);
    public static final WinDef.DWORD DWMWA_CAPTION_COLOR = new WinDef.DWORD(35);
    public static final WinDef.DWORD DWMWA_TEXT_COLOR = new WinDef.DWORD(36);
    public static final WinDef.DWORD DWMWA_SYSTEM_BACKDROP_TYPE = new WinDef.DWORD(38);
    public static final int DWMWA_COLOR_NONE = -2;
    public static final int DWMWA_COLOR_DEFAULT = -1;

    int DwmSetWindowAttribute(WinDef.HWND hwnd, WinDef.DWORD dword, WinDef.LPVOID lpvoid, WinDef.DWORD dword2);
}
