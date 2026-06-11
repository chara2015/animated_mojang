package net.labymod.core.client.os.windows.credentials;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/credentials/Advapi32_Credentials.class */
public interface Advapi32_Credentials extends StdCallLibrary {
    public static final Advapi32_Credentials INSTANCE = Native.load("advapi32", Advapi32_Credentials.class);

    boolean CredEnumerateW(String str, int i, IntByReference intByReference, PointerByReference pointerByReference);

    boolean CredWriteW(Credential credential, int i);
}
