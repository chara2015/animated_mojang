package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Buffers;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.input.PreeditCallbackRegistrar;
import net.labymod.core.client.os.module.ModuleScanner;
import net.labymod.core.client.os.module.ModuleScannerUtil;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.Monitor;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.MonitorPredicate;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.MonitorRegistry;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.VideoMode;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.CallbackUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.DisplayModeConsumer;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.ImGuiPipeline;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/opengl/Display.class */
public final class Display {
    private static long windowHandle;
    private static String title;
    private static boolean resizeable;
    private static int x;
    private static int y;
    private static int width;
    private static int height;
    private static int framebufferWidth;
    private static int framebufferHeight;
    private static boolean fullscreen;
    private static boolean windowResized;

    @Nullable
    private static ByteBuffer[] cachedIcons;
    private static GLFWErrorCallback errorCallback;
    private static MonitorRegistry monitorRegistry;
    private static BorderlessWindow borderlessWindow;
    private static boolean actuallyFullscreen;
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger("GLFW Window");
    private static final FullscreenWindowController FULLSCREEN_WINDOW_CONTROLLER = new DisplayFullscreenWindowController();
    private static boolean focused = true;
    private static DisplayMode displayMode = new DisplayMode(640, 480, 24, 60);
    private static DisplayMode initialMode = new DisplayMode(displayMode);
    private static DisplayMode desktopDisplayMode = new DisplayMode(displayMode);
    private static List<String> initErrors = new ArrayList();
    private static long currentMonitor = 0;
    private static boolean monitorChangeDirty = false;
    private static boolean fullscreenChanged = false;
    private static MutableRectangle previousWindowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    private static MutableRectangle windowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);

    private Display() {
    }

    public static long getWindowHandle() {
        return windowHandle;
    }

    public static DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
        long primaryMonitorPtr = GLFW.glfwGetPrimaryMonitor();
        if (primaryMonitorPtr == 0) {
            return new DisplayMode[0];
        }
        GLFWVidMode.Buffer buffer = GLFW.glfwGetVideoModes(primaryMonitorPtr);
        if (buffer == null) {
            throw new IllegalStateException("No video mode was found");
        }
        List<DisplayMode> displayModes = new ArrayList<>();
        for (int index = buffer.limit() - 1; index >= 0; index--) {
            buffer.position(index);
            displayModes.add(new DisplayMode(buffer.width(), buffer.height(), buffer.redBits() + buffer.blueBits() + buffer.greenBits(), buffer.refreshRate()));
        }
        return (DisplayMode[]) displayModes.toArray(new DisplayMode[0]);
    }

    public static DisplayMode getDisplayMode() {
        return displayMode;
    }

    public static void setDisplayMode(DisplayMode displayMode2) {
        displayMode = displayMode2;
    }

    public static void sync(int fps) {
        Sync.sync(fps);
    }

    public static void destroy() {
        Mouse.destroy();
        Keyboard.destroy();
        if (monitorRegistry != null) {
            monitorRegistry.dispose();
        }
        Callbacks.glfwFreeCallbacks(windowHandle);
        CallbackUtil.free(errorCallback);
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }

    public static String getTitle() {
        return title;
    }

    public static DisplayMode getDesktopDisplayMode() {
        return desktopDisplayMode;
    }

    private static DisplayMode getDisplayMode(long monitor) {
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
        if (vidMode == null) {
            return initialMode;
        }
        return new DisplayMode(vidMode.width(), vidMode.height(), vidMode.redBits() + vidMode.greenBits() + vidMode.blueBits(), vidMode.refreshRate());
    }

    private static long findBestMonitor() {
        if (windowHandle == 0) {
            return 0L;
        }
        Monitor monitor = monitorRegistry.findBestMonitor(windowHandle, MonitorPredicate.DISPLAY_MONITOR_FILTER);
        return monitor == null ? GLFW.glfwGetPrimaryMonitor() : monitor.getMonitor();
    }

    public static boolean isFullscreen() {
        return fullscreen;
    }

    public static void setFullscreen(boolean fullscreen2) throws LWJGLException {
        if (fullscreenChanged) {
            return;
        }
        fullscreenChanged = true;
        fullscreen = fullscreen2;
        if (GLFWUtil.isBorderlessWindowed()) {
            fullscreen = false;
            borderlessWindow.toggleFullscreen();
        }
    }

    public static void setTitle(String newTitle) {
        if (newTitle == null) {
            newTitle = "";
        }
        title = newTitle;
        if (isCreated()) {
            GLFW.glfwSetWindowTitle(windowHandle, title);
        }
    }

    public static boolean isCloseRequested() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public static boolean isActive() {
        return focused;
    }

    public static void update() {
        ImGuiPipeline.getInstance().renderFrame();
        GLFW.glfwPollEvents();
        if (Mouse.isCreated()) {
            Mouse.poll();
        }
        if (Keyboard.isCreated()) {
            Keyboard.poll();
        }
        GLFW.glfwSwapBuffers(windowHandle);
        ModuleScannerUtil.scanModules(ModuleScanner.State.SWAP_BUFFERS);
        if (fullscreen != actuallyFullscreen) {
            actuallyFullscreen = fullscreen;
            updateFullscreen();
        }
        if (borderlessWindow != null) {
            borderlessWindow.poll();
        }
        checkMonitorChange();
        fullscreenChanged = false;
    }

    public static DisplayMode getPrimaryDisplayMode() {
        long l = GLFW.glfwGetPrimaryMonitor();
        if (l == 0) {
            return null;
        }
        return getDisplayMode(l);
    }

    private static void updateFullscreen() {
        if (!isCreated()) {
            return;
        }
        long monitor = findBestMonitor();
        if (!fullscreen) {
            GLFW.glfwSetWindowMonitor(windowHandle, 0L, (int) windowedRectangle.getX(), (int) windowedRectangle.getY(), displayMode.getWidth(), displayMode.getHeight(), -1);
        } else if (monitor == 0) {
            fullscreen = false;
            return;
        } else {
            windowedRectangle.setPosition(getX(), getY());
            GLFW.glfwSetWindowMonitor(windowHandle, monitor, 0, 0, displayMode.getWidth(), displayMode.getHeight(), displayMode.getFrequency());
        }
        setMonitorChangeDirty();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.lwjgl.LWJGLException */
    public static void create() throws LWJGLException {
        create(new PixelFormat());
    }

    public static void create(PixelFormat pixel_format) throws LWJGLException {
        errorCallback = GLFW.glfwSetErrorCallback(Display::setError);
        GLFWUtil.preferX11OverWayland();
        if (!GLFW.glfwInit()) {
            throw createException("Unable to initialize GLFW");
        }
        GLFWUtil.applyDefaultWindowHints();
        GLFW.glfwWindowHint(131076, 0);
        GLFW.glfwWindowHint(139265, 196609);
        GLFW.glfwWindowHint(139275, 221185);
        GLFW.glfwWindowHint(139266, 2);
        GLFW.glfwWindowHint(139267, 0);
        GLFW.glfwWindowHint(139272, 0);
        setResizable(resizeable);
        DisplayMode displayMode2 = getDisplayMode();
        desktopDisplayMode.setWidth(displayMode2.getWidth());
        desktopDisplayMode.setHeight(displayMode2.getHeight());
        desktopDisplayMode.setFrequency(displayMode2.getFrequency());
        desktopDisplayMode.setBitsPerPixel(displayMode2.getBitsPerPixel());
        String title2 = getTitle();
        windowHandle = GLFW.glfwCreateWindow(Math.max(displayMode2.getWidth(), 1), Math.max(displayMode2.getHeight(), 1), title2 == null ? "Game" : title2, 0L, 0L);
        if (windowHandle == 0) {
            GLFW.glfwTerminate();
            throw createException("Could not create window!");
        }
        monitorRegistry = new MonitorRegistry();
        borderlessWindow = new BorderlessWindow(windowHandle, () -> {
            return windowedRectangle;
        }, () -> {
            return previousWindowedRectangle;
        }, handle -> {
            BorderlessWindow.VideoMode videoMode;
            Monitor monitor = monitorRegistry.findBestMonitor(handle, MonitorPredicate.DISPLAY_MONITOR_FILTER);
            if (monitor == null) {
                return null;
            }
            VideoMode videoMode2 = monitor.currentMode();
            int x2 = monitor.getX();
            int y2 = monitor.getY();
            if (videoMode2 == null) {
                videoMode = null;
            } else {
                videoMode = new BorderlessWindow.VideoMode(videoMode2.width(), videoMode2.height());
            }
            return new BorderlessWindow.Monitor(x2, y2, videoMode);
        }, FULLSCREEN_WINDOW_CONTROLLER);
        width = displayMode2.getWidth();
        height = displayMode2.getHeight();
        onWindowResize(windowHandle, width, height);
        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        GLFW.glfwSetWindowFocusCallback(windowHandle, Display::onWindowFocus);
        GLFW.glfwSetWindowSizeCallback(windowHandle, Display::onWindowResize);
        GLFW.glfwSetFramebufferSizeCallback(windowHandle, Display::onFramebufferResize);
        GLFW.glfwSetWindowPosCallback(windowHandle, Display::onWindowMove);
        GLFW.glfwSetDropCallback(windowHandle, Display::onDrop);
        Mouse.create();
        Keyboard.create();
        PreeditCallbackRegistrar.register(windowHandle);
        centerWindow();
        GLFW.glfwShowWindow(windowHandle);
        ImGuiPipeline.getInstance().initialize(windowHandle);
        update();
        initFramebufferSize();
        if (cachedIcons != null) {
            setIcon(cachedIcons);
        }
        DisplayModeConsumer.consume(getDesktopDisplayMode());
        injectLabyMod();
    }

    private static void onDrop(long window, int count, long names) {
        List<Path> files = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            String droppedFileName = GLFWDropCallback.getName(names, index);
            try {
                files.add(Paths.get(droppedFileName, new String[0]));
            } catch (InvalidPathException exception) {
                LOGGER.error("Failed to parse path: {}", droppedFileName, exception);
            }
        }
        if (files.isEmpty()) {
            return;
        }
        for (Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            ThreadSafe.executeOnRenderThread(() -> {
                openActivity.fileDropped(Laby.labyAPI().minecraft().mouse(), files);
            });
        }
    }

    private static void checkMonitorChange() {
        if (!isCreated() || !monitorChangeDirty) {
            return;
        }
        monitorChangeDirty = false;
        long monitor = (fullscreen || (borderlessWindow != null && borderlessWindow.isFullscreen())) ? GLFW.glfwGetWindowMonitor(getWindowHandle()) : findBestMonitor();
        if (monitor != 0 && monitor != currentMonitor) {
            currentMonitor = monitor;
            DisplayMode newDisplayMode = getDisplayMode(monitor);
            desktopDisplayMode.setWidth(newDisplayMode.getWidth());
            desktopDisplayMode.setHeight(newDisplayMode.getHeight());
            desktopDisplayMode.setFrequency(newDisplayMode.getFrequency());
            desktopDisplayMode.setBitsPerPixel(newDisplayMode.getBitsPerPixel());
        }
    }

    private static IllegalStateException createException(String reason) {
        StringBuilder builder = new StringBuilder();
        builder.append(reason).append("\n\n");
        if (!initErrors.isEmpty()) {
            if (initErrors.size() > 1) {
                builder.append("Reasons:").append("\n");
            } else {
                builder.append("Reason:").append("\n");
            }
            for (String initError : initErrors) {
                builder.append(initError);
            }
            initErrors.clear();
        }
        builder.append("\n");
        throw new IllegalStateException(builder.toString());
    }

    private static void setError(int error, long description) {
        String errorMessage = "0x" + Integer.toHexString(error) + ": " + MemoryUtil.memUTF8(description);
        initErrors.add(errorMessage);
        LOGGER.error("GLFW error during init: {}", errorMessage);
    }

    private static void onWindowFocus(long handle, boolean focused2) {
        focused = focused2;
    }

    private static void injectLabyMod() {
        LabyConfig config = Laby.labyAPI().config();
        ConfigProperty<Boolean> rawMouseInput = config.hotkeys().rawMouseInput();
        Mouse.setRawMouseInput(rawMouseInput.get().booleanValue());
        rawMouseInput.addChangeListener((type, oldValue, newValue) -> {
            Mouse.setRawMouseInput(newValue.booleanValue());
        });
    }

    private static void initFramebufferSize() {
        int[] widthBuffer = new int[1];
        int[] heightBuffer = new int[1];
        long windowHandle2 = windowHandle;
        GLFW.glfwGetFramebufferSize(windowHandle2, widthBuffer, heightBuffer);
        int width2 = widthBuffer[0];
        int height2 = heightBuffer[0];
        onFramebufferResize(windowHandle2, ((long) width2) > 0 ? width2 : 1, ((long) height2) > 0 ? height2 : 1);
    }

    private static void onWindowMove(long window, int x2, int y2) {
        x = x2;
        y = y2;
        if (!fullscreen && !borderlessWindow.isFullscreen()) {
            windowedRectangle.setPosition(x2, y2);
        }
        setMonitorChangeDirty();
    }

    private static void onFramebufferResize(long window, int width2, int height2) {
        windowResized = true;
        framebufferWidth = width2 <= 0 ? 1 : width2;
        framebufferHeight = height2 <= 0 ? 1 : height2;
        setMonitorChangeDirty();
    }

    private static void centerWindow() {
        MemoryStack stack = MemoryStack.stackPush();
        try {
            IntBuffer widthPointer = stack.mallocInt(1);
            IntBuffer heightPointer = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(windowHandle, widthPointer, heightPointer);
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(windowHandle, (vidmode.width() - widthPointer.get(0)) / 2, (vidmode.height() - heightPointer.get(0)) / 2);
            if (stack != null) {
                stack.close();
            }
        } catch (Throwable th) {
            if (stack != null) {
                try {
                    stack.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void onWindowResize(long window, int newWidth, int newHeight) {
        windowResized = true;
        width = newWidth;
        height = newHeight;
        if (!fullscreen && !borderlessWindow.isFullscreen()) {
            windowedRectangle.setSize(newWidth, newHeight);
        }
        setMonitorChangeDirty();
    }

    public static boolean isCreated() {
        return windowHandle != 0;
    }

    public static void setVSyncEnabled(boolean sync) {
        GLFW.glfwSwapInterval(sync ? 1 : 0);
    }

    public static void setLocation(int new_x, int new_y) {
        x = new_x;
        y = new_y;
        if (isCreated() && !isFullscreen()) {
            reshape();
        }
    }

    private static void reshape() {
        DisplayMode mode = getDisplayMode();
        GLFW.glfwSetWindowPos(windowHandle, x, y);
        GLFW.glfwSetWindowSize(windowHandle, mode.getWidth(), mode.getHeight());
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getFramebufferWidth() {
        return framebufferWidth;
    }

    public static int getFramebufferHeight() {
        return framebufferHeight;
    }

    public static int setIcon(ByteBuffer[] icons) {
        if (cachedIcons == null) {
            cachedIcons = new ByteBuffer[icons.length];
            for (int i = 0; i < icons.length; i++) {
                cachedIcons[i] = Buffers.cloneBuffer(icons[i], BufferUtils::createByteBuffer);
            }
        }
        if (isCreated()) {
            int size = cachedIcons.length;
            List<ByteBuffer> buffers = new ArrayList<>(size);
            try {
                MemoryStack stack = MemoryStack.stackPush();
                try {
                    GLFWImage.Buffer imageBuffer = GLFWImage.malloc(size, stack);
                    for (int index = 0; index < size; index++) {
                        ByteBuffer cachedBuffer = cachedIcons[index];
                        if (cachedBuffer != null) {
                            buffers.add(fillGLFWImage(imageBuffer, index, cachedBuffer));
                        }
                    }
                    GLFW.glfwSetWindowIcon(windowHandle, imageBuffer);
                    if (stack != null) {
                        stack.close();
                    }
                    return 1;
                } finally {
                }
            } finally {
                buffers.forEach(MemoryUtil::memFree);
            }
        }
        return 0;
    }

    private static ByteBuffer fillGLFWImage(GLFWImage.Buffer imageBuffer, int index, ByteBuffer cachedBuffer) {
        ByteBuffer buffer = MemoryUtil.memAlloc(cachedBuffer.capacity());
        buffer.put(cachedBuffer);
        buffer.flip();
        int imageDimension = (int) Math.sqrt(buffer.limit() / 4);
        imageBuffer.position(index);
        imageBuffer.width(imageDimension);
        imageBuffer.height(imageDimension);
        imageBuffer.pixels(buffer);
        return buffer;
    }

    public static void setResizable(boolean resizable) {
        resizeable = resizable;
        if (isCreated()) {
            GLFW.glfwWindowHint(131075, resizable ? 1 : 0);
        }
    }

    public static boolean wasResized() {
        boolean _windowResized = windowResized;
        windowResized = false;
        return _windowResized;
    }

    public static void maximize() {
        GLFW.glfwWindowHint(131075, 1);
        GLFW.glfwMaximizeWindow(windowHandle);
    }

    private static void setMonitorChangeDirty() {
        monitorChangeDirty = true;
    }
}
