package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.TracyFrameCapture;
import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/Window.class */
public final class Window implements AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final int BASE_WIDTH = 320;
    public static final int BASE_HEIGHT = 240;
    private final WindowEventHandler eventHandler;
    private final ScreenManager screenManager;
    private final long handle;
    private int windowedX;
    private int windowedY;
    private int windowedWidth;
    private int windowedHeight;
    private Optional<VideoMode> preferredFullscreenVideoMode;
    private boolean fullscreen;
    private boolean actuallyFullscreen;
    private int x;
    private int y;
    private int width;
    private int height;
    private int framebufferWidth;
    private int framebufferHeight;
    private int guiScaledWidth;
    private int guiScaledHeight;
    private int guiScale;
    private boolean dirty;
    private boolean vsync;
    private boolean iconified;
    private boolean minimized;
    private boolean allowCursorChanges;
    private final GLFWErrorCallback defaultErrorCallback = GLFWErrorCallback.create(this::defaultErrorCallback);
    private String errorSection = "";
    private CursorType currentCursor = CursorType.DEFAULT;

    public Window(WindowEventHandler $$0, ScreenManager $$1, DisplayData $$2, String $$3, String $$4) {
        this.screenManager = $$1;
        setBootErrorCallback();
        setErrorSection("Pre startup");
        this.eventHandler = $$0;
        Optional<VideoMode> $$5 = VideoMode.read($$3);
        if ($$5.isPresent()) {
            this.preferredFullscreenVideoMode = $$5;
        } else if ($$2.fullscreenWidth().isPresent() && $$2.fullscreenHeight().isPresent()) {
            this.preferredFullscreenVideoMode = Optional.of(new VideoMode($$2.fullscreenWidth().getAsInt(), $$2.fullscreenHeight().getAsInt(), 8, 8, 8, 60));
        } else {
            this.preferredFullscreenVideoMode = Optional.empty();
        }
        boolean zIsFullscreen = $$2.isFullscreen();
        this.fullscreen = zIsFullscreen;
        this.actuallyFullscreen = zIsFullscreen;
        Monitor $$6 = $$1.getMonitor(GLFW.glfwGetPrimaryMonitor());
        int iMax = Math.max($$2.width(), 1);
        this.width = iMax;
        this.windowedWidth = iMax;
        int iMax2 = Math.max($$2.height(), 1);
        this.height = iMax2;
        this.windowedHeight = iMax2;
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(139265, 196609);
        GLFW.glfwWindowHint(139275, 221185);
        GLFW.glfwWindowHint(139266, 3);
        GLFW.glfwWindowHint(139267, 3);
        GLFW.glfwWindowHint(139272, 204801);
        GLFW.glfwWindowHint(139270, 1);
        this.handle = GLFW.glfwCreateWindow(this.width, this.height, $$4, (!this.fullscreen || $$6 == null) ? 0L : $$6.getMonitor(), 0L);
        if ($$6 != null) {
            VideoMode $$7 = $$6.getPreferredVidMode(this.fullscreen ? this.preferredFullscreenVideoMode : Optional.empty());
            int x = ($$6.getX() + ($$7.getWidth() / 2)) - (this.width / 2);
            this.x = x;
            this.windowedX = x;
            int y = ($$6.getY() + ($$7.getHeight() / 2)) - (this.height / 2);
            this.y = y;
            this.windowedY = y;
        } else {
            int[] $$8 = new int[1];
            int[] $$9 = new int[1];
            GLFW.glfwGetWindowPos(this.handle, $$8, $$9);
            int i = $$8[0];
            this.x = i;
            this.windowedX = i;
            int i2 = $$9[0];
            this.y = i2;
            this.windowedY = i2;
        }
        setMode();
        refreshFramebufferSize();
        GLFW.glfwSetFramebufferSizeCallback(this.handle, this::onFramebufferResize);
        GLFW.glfwSetWindowPosCallback(this.handle, this::onMove);
        GLFW.glfwSetWindowSizeCallback(this.handle, this::onResize);
        GLFW.glfwSetWindowFocusCallback(this.handle, this::onFocus);
        GLFW.glfwSetCursorEnterCallback(this.handle, this::onEnter);
        GLFW.glfwSetWindowIconifyCallback(this.handle, this::onIconify);
    }

    public static String getPlatform() {
        int $$0 = GLFW.glfwGetPlatform();
        switch ($$0) {
            case 0:
                return "<error>";
            case 393217:
                return "win32";
            case 393218:
                return "cocoa";
            case 393219:
                return "wayland";
            case 393220:
                return "x11";
            case 393221:
                return "null";
            default:
                return String.format(Locale.ROOT, "unknown (%08X)", Integer.valueOf($$0));
        }
    }

    public int getRefreshRate() {
        RenderSystem.assertOnRenderThread();
        return GLX._getRefreshRate(this);
    }

    public boolean shouldClose() {
        return GLX._shouldClose(this);
    }

    public static void checkGlfwError(BiConsumer<Integer, String> $$0) {
        MemoryStack $$1 = MemoryStack.stackPush();
        try {
            PointerBuffer $$2 = $$1.mallocPointer(1);
            int $$3 = GLFW.glfwGetError($$2);
            if ($$3 != 0) {
                long $$4 = $$2.get();
                String $$5 = $$4 == 0 ? "" : MemoryUtil.memUTF8($$4);
                $$0.accept(Integer.valueOf($$3), $$5);
            }
            if ($$1 != null) {
                $$1.close();
            }
        } catch (Throwable th) {
            if ($$1 != null) {
                try {
                    $$1.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void setIcon(PackResources $$0, IconSet $$1) throws IOException {
        int $$2 = GLFW.glfwGetPlatform();
        switch ($$2) {
            case 393217:
            case 393220:
                List<IoSupplier<InputStream>> $$3 = $$1.getStandardIcons($$0);
                List<ByteBuffer> $$4 = new ArrayList<>($$3.size());
                try {
                    MemoryStack $$5 = MemoryStack.stackPush();
                    try {
                        GLFWImage.Buffer $$6 = GLFWImage.malloc($$3.size(), $$5);
                        for (int $$7 = 0; $$7 < $$3.size(); $$7++) {
                            NativeImage $$8 = NativeImage.read($$3.get($$7).get());
                            try {
                                ByteBuffer $$9 = MemoryUtil.memAlloc($$8.getWidth() * $$8.getHeight() * 4);
                                $$4.add($$9);
                                $$9.asIntBuffer().put($$8.getPixelsABGR());
                                $$6.position($$7);
                                $$6.width($$8.getWidth());
                                $$6.height($$8.getHeight());
                                $$6.pixels($$9);
                                if ($$8 != null) {
                                    $$8.close();
                                }
                            } catch (Throwable th) {
                                if ($$8 != null) {
                                    try {
                                        $$8.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                    break;
                                }
                                throw th;
                            }
                        }
                        GLFW.glfwSetWindowIcon(this.handle, $$6.position(0));
                        if ($$5 != null) {
                            $$5.close();
                        }
                        return;
                    } finally {
                        if ($$5 != null) {
                            try {
                                break;
                            } catch (Throwable th3) {
                            }
                        }
                    }
                } finally {
                    $$4.forEach((v0) -> {
                        MemoryUtil.memFree(v0);
                    });
                }
            case 393218:
                MacosUtil.loadIcon($$1.getMacIcon($$0));
                return;
            case 393219:
            case 393221:
                return;
            default:
                LOGGER.warn("Not setting icon for unrecognized platform: {}", Integer.valueOf($$2));
                return;
        }
    }

    public void setErrorSection(String $$0) {
        this.errorSection = $$0;
    }

    private void setBootErrorCallback() {
        GLFW.glfwSetErrorCallback(Window::bootCrash);
    }

    private static void bootCrash(int $$0, long $$1) {
        String $$2 = "GLFW error " + $$0 + ": " + MemoryUtil.memUTF8($$1);
        TinyFileDialogs.tinyfd_messageBox(JfrProfiler.ROOT_CATEGORY, $$2 + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false);
        throw new WindowInitFailed($$2);
    }

    public void defaultErrorCallback(int $$0, long $$1) {
        RenderSystem.assertOnRenderThread();
        String $$2 = MemoryUtil.memUTF8($$1);
        LOGGER.error("########## GL ERROR ##########");
        LOGGER.error("@ {}", this.errorSection);
        LOGGER.error("{}: {}", Integer.valueOf($$0), $$2);
    }

    public void setDefaultErrorCallback() {
        GLFWErrorCallback $$0 = GLFW.glfwSetErrorCallback(this.defaultErrorCallback);
        if ($$0 != null) {
            $$0.free();
        }
    }

    public void updateVsync(boolean $$0) {
        RenderSystem.assertOnRenderThread();
        this.vsync = $$0;
        GLFW.glfwSwapInterval($$0 ? 1 : 0);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        RenderSystem.assertOnRenderThread();
        Callbacks.glfwFreeCallbacks(this.handle);
        this.defaultErrorCallback.close();
        GLFW.glfwDestroyWindow(this.handle);
        GLFW.glfwTerminate();
    }

    private void onMove(long $$0, int $$1, int $$2) {
        this.x = $$1;
        this.y = $$2;
    }

    private void onFramebufferResize(long $$0, int $$1, int $$2) {
        if ($$0 != this.handle) {
            return;
        }
        int $$3 = getWidth();
        int $$4 = getHeight();
        if ($$1 == 0 || $$2 == 0) {
            this.minimized = true;
            return;
        }
        this.minimized = false;
        this.framebufferWidth = $$1;
        this.framebufferHeight = $$2;
        if (getWidth() != $$3 || getHeight() != $$4) {
            try {
                this.eventHandler.resizeDisplay();
            } catch (Exception $$5) {
                CrashReport $$6 = CrashReport.forThrowable($$5, "Window resize");
                CrashReportCategory $$7 = $$6.addCategory("Window Dimensions");
                $$7.setDetail("Old", $$3 + "x" + $$4);
                $$7.setDetail("New", $$1 + "x" + $$2);
                throw new ReportedException($$6);
            }
        }
    }

    private void refreshFramebufferSize() {
        int[] $$0 = new int[1];
        int[] $$1 = new int[1];
        GLFW.glfwGetFramebufferSize(this.handle, $$0, $$1);
        this.framebufferWidth = $$0[0] > 0 ? $$0[0] : 1;
        this.framebufferHeight = $$1[0] > 0 ? $$1[0] : 1;
    }

    private void onResize(long $$0, int $$1, int $$2) {
        this.width = $$1;
        this.height = $$2;
    }

    private void onFocus(long $$0, boolean $$1) {
        if ($$0 == this.handle) {
            this.eventHandler.setWindowActive($$1);
        }
    }

    private void onEnter(long $$0, boolean $$1) {
        if ($$1) {
            this.eventHandler.cursorEntered();
        }
    }

    private void onIconify(long $$0, boolean $$1) {
        this.iconified = $$1;
    }

    public void updateDisplay(TracyFrameCapture $$0) {
        RenderSystem.flipFrame(this, $$0);
        if (this.fullscreen != this.actuallyFullscreen) {
            this.actuallyFullscreen = this.fullscreen;
            updateFullscreen(this.vsync, $$0);
        }
    }

    public Optional<VideoMode> getPreferredFullscreenVideoMode() {
        return this.preferredFullscreenVideoMode;
    }

    public void setPreferredFullscreenVideoMode(Optional<VideoMode> $$0) {
        boolean $$1 = !$$0.equals(this.preferredFullscreenVideoMode);
        this.preferredFullscreenVideoMode = $$0;
        if ($$1) {
            this.dirty = true;
        }
    }

    public void changeFullscreenVideoMode() {
        if (this.fullscreen && this.dirty) {
            this.dirty = false;
            setMode();
            this.eventHandler.resizeDisplay();
        }
    }

    private void setMode() {
        boolean $$0 = GLFW.glfwGetWindowMonitor(this.handle) != 0;
        if (this.fullscreen) {
            Monitor $$1 = this.screenManager.findBestMonitor(this);
            if ($$1 == null) {
                LOGGER.warn("Failed to find suitable monitor for fullscreen mode");
                this.fullscreen = false;
                return;
            }
            if (MacosUtil.IS_MACOS) {
                MacosUtil.exitNativeFullscreen(this);
            }
            VideoMode $$2 = $$1.getPreferredVidMode(this.preferredFullscreenVideoMode);
            if (!$$0) {
                this.windowedX = this.x;
                this.windowedY = this.y;
                this.windowedWidth = this.width;
                this.windowedHeight = this.height;
            }
            this.x = 0;
            this.y = 0;
            this.width = $$2.getWidth();
            this.height = $$2.getHeight();
            GLFW.glfwSetWindowMonitor(this.handle, $$1.getMonitor(), this.x, this.y, this.width, this.height, $$2.getRefreshRate());
            if (MacosUtil.IS_MACOS) {
                MacosUtil.clearResizableBit(this);
                return;
            }
            return;
        }
        this.x = this.windowedX;
        this.y = this.windowedY;
        this.width = this.windowedWidth;
        this.height = this.windowedHeight;
        GLFW.glfwSetWindowMonitor(this.handle, 0L, this.x, this.y, this.width, this.height, -1);
    }

    public void toggleFullScreen() {
        this.fullscreen = !this.fullscreen;
    }

    public void setWindowed(int $$0, int $$1) {
        this.windowedWidth = $$0;
        this.windowedHeight = $$1;
        this.fullscreen = false;
        setMode();
    }

    private void updateFullscreen(boolean $$0, TracyFrameCapture $$1) {
        RenderSystem.assertOnRenderThread();
        try {
            setMode();
            this.eventHandler.resizeDisplay();
            updateVsync($$0);
            updateDisplay($$1);
        } catch (Exception $$2) {
            LOGGER.error("Couldn't toggle fullscreen", $$2);
        }
    }

    public int calculateScale(int $$0, boolean $$1) {
        int $$2 = 1;
        while ($$2 != $$0 && $$2 < this.framebufferWidth && $$2 < this.framebufferHeight && this.framebufferWidth / ($$2 + 1) >= 320 && this.framebufferHeight / ($$2 + 1) >= 240) {
            $$2++;
        }
        if ($$1 && $$2 % 2 != 0) {
            $$2++;
        }
        return $$2;
    }

    public void setGuiScale(int $$0) {
        this.guiScale = $$0;
        double $$1 = $$0;
        int $$2 = (int) (((double) this.framebufferWidth) / $$1);
        this.guiScaledWidth = ((double) this.framebufferWidth) / $$1 > ((double) $$2) ? $$2 + 1 : $$2;
        int $$3 = (int) (((double) this.framebufferHeight) / $$1);
        this.guiScaledHeight = ((double) this.framebufferHeight) / $$1 > ((double) $$3) ? $$3 + 1 : $$3;
    }

    public void setTitle(String $$0) {
        GLFW.glfwSetWindowTitle(this.handle, $$0);
    }

    public long handle() {
        return this.handle;
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }

    public boolean isIconified() {
        return this.iconified;
    }

    public int getWidth() {
        return this.framebufferWidth;
    }

    public int getHeight() {
        return this.framebufferHeight;
    }

    public void setWidth(int $$0) {
        this.framebufferWidth = $$0;
    }

    public void setHeight(int $$0) {
        this.framebufferHeight = $$0;
    }

    public int getScreenWidth() {
        return this.width;
    }

    public int getScreenHeight() {
        return this.height;
    }

    public int getGuiScaledWidth() {
        return this.guiScaledWidth;
    }

    public int getGuiScaledHeight() {
        return this.guiScaledHeight;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getGuiScale() {
        return this.guiScale;
    }

    public Monitor findBestMonitor() {
        return this.screenManager.findBestMonitor(this);
    }

    public void updateRawMouseInput(boolean $$0) {
        InputConstants.updateRawMouseInput(this, $$0);
    }

    public void setWindowCloseCallback(Runnable $$0) {
        GLFWWindowCloseCallback $$1 = GLFW.glfwSetWindowCloseCallback(this.handle, $$12 -> {
            $$0.run();
        });
        if ($$1 != null) {
            $$1.free();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/Window$WindowInitFailed.class */
    public static class WindowInitFailed extends SilentInitException {
        WindowInitFailed(String $$0) {
            super($$0);
        }
    }

    public boolean isMinimized() {
        return this.minimized;
    }

    public void setAllowCursorChanges(boolean $$0) {
        this.allowCursorChanges = $$0;
    }

    public void selectCursor(CursorType $$0) {
        CursorType $$1 = this.allowCursorChanges ? $$0 : CursorType.DEFAULT;
        if (this.currentCursor != $$1) {
            this.currentCursor = $$1;
            $$1.select(this);
        }
    }

    public float getAppropriateLineWidth() {
        return Math.max(2.5f, (getWidth() / 1920.0f) * 2.5f);
    }
}
