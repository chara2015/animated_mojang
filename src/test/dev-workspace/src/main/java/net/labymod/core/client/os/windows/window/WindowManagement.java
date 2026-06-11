package net.labymod.core.client.os.windows.window;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.Color;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.os.windows.util.NtDll;
import net.labymod.core.client.os.windows.util.WindowsVersion;
import org.lwjgl.glfw.GLFWNativeWin32;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/window/WindowManagement.class */
public final class WindowManagement {
    private static final long NULL = 0;
    private static final int MINIMUM_BUILD_NUMBER = 22000;
    private static final int MINIMUM_WINDOW_MATERIAL_BUILD_NUMBER = 22621;

    public static boolean isCompatible() {
        if (OperatingSystem.getPlatform() != OperatingSystem.WINDOWS) {
            return false;
        }
        WindowsVersion version = windowsVersion();
        return version.majorVersion() >= 10 && version.buildNumber() >= MINIMUM_BUILD_NUMBER;
    }

    public static boolean isWindowMaterialSupported() {
        if (OperatingSystem.getPlatform() != OperatingSystem.WINDOWS) {
            return false;
        }
        WindowsVersion version = windowsVersion();
        return version.majorVersion() >= 10 && version.buildNumber() >= MINIMUM_WINDOW_MATERIAL_BUILD_NUMBER;
    }

    public static void update(long windowPointer) {
        if (windowPointer == NULL || !isCompatible()) {
            return;
        }
        long win32WindowHandle = GLFWNativeWin32.glfwGetWin32Window(windowPointer);
        WinDef.HWND hwnd = new WinDef.HWND(Pointer.createConstant(win32WindowHandle));
        MicrosoftWindowConfig config = Laby.labyAPI().config().other().window().microsoftWindow();
        setWindowAttribute(hwnd, DwmApi.DWMWA_USE_IMMERSIVE_DARK_MODE, config.immersiveDarkMode());
        if (isWindowMaterialSupported()) {
            setWindowAttribute(hwnd, DwmApi.DWMWA_SYSTEM_BACKDROP_TYPE, config.windowMaterial().get());
        }
        setWindowAttribute(hwnd, DwmApi.DWMWA_WINDOW_CORNER_PREFERENCE, config.cornerCurvatures().get());
        if (config.defaultWindowBorderColor().get().booleanValue()) {
            setWindowAttribute(hwnd, DwmApi.DWMWA_BORDER_COLOR, -1);
        } else if (config.hideWindowBorder().get().booleanValue()) {
            setWindowAttribute(hwnd, DwmApi.DWMWA_BORDER_COLOR, -2);
        } else {
            setWindowColorAttribute(hwnd, DwmApi.DWMWA_BORDER_COLOR, config.windowBorderColor());
        }
        setWindowAttribute(hwnd, DwmApi.DWMWA_CAPTION_COLOR, config.defaultWindowTitleBarColor(), config.windowTitleBarColor());
        setWindowAttribute(hwnd, DwmApi.DWMWA_TEXT_COLOR, config.defaultTitleTextColor(), config.titleTextColor());
    }

    private static void setWindowAttribute(WinDef.HWND hwnd, WinDef.DWORD attribute, ConfigProperty<Boolean> state, ConfigProperty<Color> color) {
        if (state.get().booleanValue()) {
            setWindowAttribute(hwnd, attribute, -1);
        } else {
            setWindowColorAttribute(hwnd, attribute, color);
        }
    }

    private static void setWindowColorAttribute(WinDef.HWND hwnd, WinDef.DWORD attribute, ConfigProperty<Color> value) {
        int colorValue = value.get().get();
        setWindowAttribute(hwnd, attribute, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, colorValue) & 16777215);
    }

    private static void setWindowAttribute(WinDef.HWND hwnd, WinDef.DWORD attribute, MicrosoftWindowConfig.IdProvider provider) {
        setWindowAttribute(hwnd, attribute, provider.getId());
    }

    private static void setWindowAttribute(WinDef.HWND hwnd, WinDef.DWORD attribute, ConfigProperty<Boolean> value) {
        setWindowAttribute(hwnd, attribute, value.get().booleanValue());
    }

    private static void setWindowAttribute(WinDef.HWND hwnd, WinDef.DWORD attribute, boolean value) {
        setWindowAttribute(hwnd, attribute, value ? 1 : 0);
    }

    private static void setWindowAttribute(WinDef.HWND hwnd, WinDef.DWORD attribute, int value) {
        Memory memory = new Memory(Native.POINTER_SIZE);
        try {
            memory.setInt(NULL, value);
            DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, attribute, new WinDef.LPVOID(memory), new WinDef.DWORD(4L));
            memory.close();
        } catch (Throwable th) {
            try {
                memory.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static WindowsVersion windowsVersion() {
        Lazy<WindowsVersion> version = NtDll.WINDOWS_VERSION;
        version.reset();
        return version.get();
    }
}
