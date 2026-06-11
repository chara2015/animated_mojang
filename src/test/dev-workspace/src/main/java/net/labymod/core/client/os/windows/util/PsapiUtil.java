package net.labymod.core.client.os.windows.util;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinNT;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/util/PsapiUtil.class */
public final class PsapiUtil {
    private static final int MAX_PATH = 32767;
    private static final Psapi PSAPI = Psapi.INSTANCE;

    private PsapiUtil() {
    }

    public static String getModuleFileName(WinNT.HANDLE hProcess, WinNT.HANDLE hModule) {
        Memory memory = new Memory(32767L);
        try {
            int length = PSAPI.GetModuleFileNameEx(hProcess, hModule, memory, MAX_PATH);
            if (length == 0) {
                throw new Win32Exception(Native.getLastError());
            }
            String string = Native.toString(memory.getCharArray(0L, length));
            memory.close();
            return string;
        } catch (Throwable th) {
            try {
                memory.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
