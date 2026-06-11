package net.minecraft.client.gui.font.providers;

import com.mojang.logging.LogUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.freetype.FT_Vector;
import org.lwjgl.util.freetype.FreeType;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/providers/FreeTypeUtil.class */
public class FreeTypeUtil {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Object LIBRARY_LOCK = new Object();
    private static long library = 0;

    public static long getLibrary() {
        long j;
        synchronized (LIBRARY_LOCK) {
            if (library == 0) {
                MemoryStack $$0 = MemoryStack.stackPush();
                try {
                    PointerBuffer $$1 = $$0.mallocPointer(1);
                    assertError(FreeType.FT_Init_FreeType($$1), "Initializing FreeType library");
                    library = $$1.get();
                    if ($$0 != null) {
                        $$0.close();
                    }
                } finally {
                }
            }
            j = library;
        }
        return j;
    }

    public static void assertError(int $$0, String $$1) {
        if ($$0 != 0) {
            throw new IllegalStateException("FreeType error: " + describeError($$0) + " (" + $$1 + ")");
        }
    }

    public static boolean checkError(int $$0, String $$1) {
        if ($$0 != 0) {
            LOGGER.error("FreeType error: {} ({})", describeError($$0), $$1);
            return true;
        }
        return false;
    }

    private static String describeError(int $$0) {
        String $$1 = FreeType.FT_Error_String($$0);
        if ($$1 != null) {
            return $$1;
        }
        return "Unrecognized error: 0x" + Integer.toHexString($$0);
    }

    public static FT_Vector setVector(FT_Vector $$0, float $$1, float $$2) {
        long $$3 = Math.round($$1 * 64.0f);
        long $$4 = Math.round($$2 * 64.0f);
        return $$0.set($$3, $$4);
    }

    public static float x(FT_Vector $$0) {
        return $$0.x() / 64.0f;
    }

    public static void destroy() {
        synchronized (LIBRARY_LOCK) {
            if (library != 0) {
                FreeType.FT_Done_Library(library);
                library = 0L;
            }
        }
    }
}
