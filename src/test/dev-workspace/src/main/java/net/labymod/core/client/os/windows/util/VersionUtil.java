package net.labymod.core.client.os.windows.util;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Version;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import net.labymod.core.client.os.windows.util.version.VersionInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/util/VersionUtil.class */
public final class VersionUtil {
    private static final Version VERSION = Version.INSTANCE;

    private VersionUtil() {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
    public static QueryResult query(Memory pBlock, String subBlock) throws Win32Exception {
        PointerByReference lpBuffer = new PointerByReference();
        IntByReference puLength = new IntByReference();
        if (!VERSION.VerQueryValue(pBlock, subBlock, lpBuffer, puLength)) {
            throw new Win32Exception(Native.getLastError());
        }
        return new QueryResult(lpBuffer, puLength);
    }

    public static VersionInfo getModuleVersionInfo(Path file) {
        return getModuleVersionInfo(file.toString());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
    public static VersionInfo getModuleVersionInfo(String fileName) throws Win32Exception {
        IntByReference handle = new IntByReference();
        int size = VERSION.GetFileVersionInfoSize(fileName, handle);
        if (size == 0) {
            throw new Win32Exception(Native.getLastError());
        }
        VersionInfo versionInfo = VersionInfo.allocate(size);
        if (!VERSION.GetFileVersionInfo(fileName, handle.getValue(), size, versionInfo.getMemory())) {
            versionInfo.close();
            throw new Win32Exception(Native.getLastError());
        }
        return versionInfo;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/util/VersionUtil$QueryResult.class */
    public static final class QueryResult extends Record {
        private final PointerByReference lpBuffer;
        private final IntByReference puLength;

        public QueryResult(PointerByReference lpBuffer, IntByReference puLength) {
            this.lpBuffer = lpBuffer;
            this.puLength = puLength;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, QueryResult.class), QueryResult.class, "lpBuffer;puLength", "FIELD:Lnet/labymod/core/client/os/windows/util/VersionUtil$QueryResult;->lpBuffer:Lcom/sun/jna/ptr/PointerByReference;", "FIELD:Lnet/labymod/core/client/os/windows/util/VersionUtil$QueryResult;->puLength:Lcom/sun/jna/ptr/IntByReference;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, QueryResult.class), QueryResult.class, "lpBuffer;puLength", "FIELD:Lnet/labymod/core/client/os/windows/util/VersionUtil$QueryResult;->lpBuffer:Lcom/sun/jna/ptr/PointerByReference;", "FIELD:Lnet/labymod/core/client/os/windows/util/VersionUtil$QueryResult;->puLength:Lcom/sun/jna/ptr/IntByReference;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, QueryResult.class, Object.class), QueryResult.class, "lpBuffer;puLength", "FIELD:Lnet/labymod/core/client/os/windows/util/VersionUtil$QueryResult;->lpBuffer:Lcom/sun/jna/ptr/PointerByReference;", "FIELD:Lnet/labymod/core/client/os/windows/util/VersionUtil$QueryResult;->puLength:Lcom/sun/jna/ptr/IntByReference;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public PointerByReference lpBuffer() {
            return this.lpBuffer;
        }

        public IntByReference puLength() {
            return this.puLength;
        }

        public int length() {
            return puLength().getValue();
        }
    }
}
