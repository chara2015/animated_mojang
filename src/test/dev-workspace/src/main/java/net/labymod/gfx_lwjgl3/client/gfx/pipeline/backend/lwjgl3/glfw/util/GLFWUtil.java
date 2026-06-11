package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.os.OperatingSystemAccessor;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.function.Functional;
import net.labymod.core.util.classpath.ClasspathUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/util/GLFWUtil.class */
public final class GLFWUtil {
    private static final String DEBUG_WINDOW_ICON_PATH = "assets/labymod/textures/debug_window_icon.png";
    private static final IntList ACTION_NUMPAD_SCANCODES = new IntArrayList(new int[]{71, 72, 73, 75, 77, 79, 80, 81, 82, 83});
    private static final Int2IntMap MAPPED_ACTION_KEYS = (Int2IntMap) Functional.of(new Int2IntArrayMap(), map -> {
        map.put(71, 268);
        map.put(72, 265);
        map.put(73, 266);
        map.put(75, 263);
        map.put(77, 262);
        map.put(79, 269);
        map.put(80, 264);
        map.put(81, 267);
        map.put(82, GlConst.GL_ADD);
        map.put(83, 261);
    });

    private GLFWUtil() {
    }

    public static void decoratedWindow(long handle, boolean decorated, Rectangle rectangle) {
        decoratedWindow(handle, decorated, (int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    public static void decoratedWindow(long handle, boolean decorated, int x, int y, int width, int height) {
        GLFW.glfwSetWindowAttrib(handle, 131077, decorated ? 1 : 0);
        GLFW.glfwSetWindowPos(handle, x, y);
        GLFW.glfwSetWindowSize(handle, width, height);
    }

    public static void setDebugWindowIcon(long handle) {
        if (OperatingSystem.isOSX()) {
            return;
        }
        OperatingSystemAccessor operatingSystemAccessor = Laby.labyAPI().operatingSystemAccessor();
        try {
            InputStream stream = ClasspathUtil.getResourceAsInputStream("labymod", DEBUG_WINDOW_ICON_PATH);
            try {
                operatingSystemAccessor.setWindowIcon(handle, stream);
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }

    public static void setIcon(long handle, InputStream stream) throws IOException {
        GameImage image = Laby.references().gameImageProvider().getImage(stream);
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer pixelBuffer = MemoryUtil.memAlloc(width * height * 4);
        ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getARGB(x, y);
                pixelBuffer.put((byte) colorFormat.red(argb));
                pixelBuffer.put((byte) colorFormat.green(argb));
                pixelBuffer.put((byte) colorFormat.blue(argb));
                pixelBuffer.put((byte) colorFormat.alpha(argb));
            }
        }
        pixelBuffer.flip();
        GLFWImage.Buffer buffer = GLFWImage.malloc(1);
        try {
            buffer.width(width);
            buffer.height(height);
            buffer.pixels(pixelBuffer);
            GLFW.glfwSetWindowIcon(handle, buffer);
            if (buffer != null) {
                buffer.close();
            }
            MemoryUtil.memFree(pixelBuffer);
        } catch (Throwable th) {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static boolean isBorderlessWindowed() {
        return Laby.labyAPI().config().other().window().borderlessWindow().get().booleanValue();
    }

    public static void addBorderlessWindowedChangeListener(BooleanConsumer stateConsumer) {
        Laby.labyAPI().config().other().window().borderlessWindow().addChangeListener((type, oldValue, newValue) -> {
            stateConsumer.accept(newValue);
        });
    }

    public static int getNumpadActionKey(int key, int scancode, int mods) {
        boolean numLockDisabled = (mods & (-208901)) == 0;
        if (numLockDisabled && ACTION_NUMPAD_SCANCODES.contains(scancode)) {
            return MAPPED_ACTION_KEYS.get(scancode);
        }
        return key;
    }

    public static void applyDefaultWindowHints() {
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(131088, 1);
    }

    public static void preferX11OverWayland() {
        if (GLFW.glfwPlatformSupported(393219) && GLFW.glfwPlatformSupported(393220)) {
            GLFW.glfwInitHint(327683, 393220);
        }
    }
}
