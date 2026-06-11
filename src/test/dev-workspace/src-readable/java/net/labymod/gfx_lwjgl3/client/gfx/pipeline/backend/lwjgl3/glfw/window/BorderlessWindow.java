package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.LongFunction;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.logging.Logging;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow.class */
public final class BorderlessWindow {
    private static final Logging LOGGER = Logging.create((Class<?>) BorderlessWindow.class);
    private final long windowHandle;
    private final LongFunction<Monitor> monitorFindFunction;
    private final MutableRectangle previousWindowedRectangle;
    private final MutableRectangle windowedRectangle;
    private boolean fullscreen;
    private final Queue<Runnable> queue = new ArrayDeque();

    public BorderlessWindow(long windowHandle, Supplier<MutableRectangle> windowedRectangle, Supplier<MutableRectangle> previousWindowedRectangle, LongFunction<Monitor> monitorFindFunction, FullscreenWindowController fullscreenWindowController) {
        this.windowHandle = windowHandle;
        this.windowedRectangle = windowedRectangle.get();
        this.previousWindowedRectangle = previousWindowedRectangle.get();
        this.monitorFindFunction = monitorFindFunction;
        GLFWUtil.addBorderlessWindowedChangeListener(value -> {
            if (fullscreenWindowController.isWindowFullscreen()) {
                this.previousWindowedRectangle.set(this.windowedRectangle);
                this.queue.add(() -> {
                    fullscreenWindowController.setWindowFullscreen(value);
                });
            } else if (isFullscreen()) {
                setFullscreen(false);
                this.queue.add(() -> {
                    fullscreenWindowController.setWindowFullscreen(true);
                });
                this.windowedRectangle.set(this.previousWindowedRectangle);
                this.previousWindowedRectangle.setBounds(0.0f, 0.0f, 0.0f, 0.0f);
            }
        });
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        Rectangle rectangle;
        if (this.fullscreen == fullscreen) {
            return;
        }
        this.fullscreen = fullscreen;
        saveFullscreenOption(fullscreen);
        if (fullscreen) {
            Monitor monitor = this.monitorFindFunction.apply(this.windowHandle);
            if (monitor == null) {
                LOGGER.error("Failed to get a monitor.", new Object[0]);
                this.fullscreen = false;
                return;
            }
            int x = monitor.x();
            int y = monitor.y();
            VideoMode videoMode = monitor.mode();
            if (videoMode == null) {
                LOGGER.error("Failed to get a video mode for the current monitor.", new Object[0]);
                this.fullscreen = false;
                return;
            } else {
                int width = videoMode.width();
                int height = videoMode.height();
                this.queue.add(new WindowTask(this.windowHandle, false, x, y, width, height));
                return;
            }
        }
        if (this.previousWindowedRectangle.hasSize()) {
            rectangle = this.previousWindowedRectangle;
        } else {
            rectangle = this.windowedRectangle;
        }
        Rectangle rectangle2 = rectangle;
        this.queue.add(new WindowTask(this.windowHandle, true, rectangle2));
    }

    public void toggleFullscreen() {
        setFullscreen(!this.fullscreen);
    }

    public void poll() {
        Runnable task;
        if (this.queue.isEmpty() || (task = this.queue.poll()) == null) {
            return;
        }
        task.run();
    }

    private void saveFullscreenOption(boolean fullscreen) {
        try {
            MinecraftOptions options = Laby.labyAPI().minecraft().options();
            options.setFullscreen(fullscreen);
            options.save();
        } catch (Exception e) {
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor.class */
    public static final class Monitor extends Record {
        private final int x;
        private final int y;

        @Nullable
        private final VideoMode mode;

        public Monitor(int x, int y, @Nullable VideoMode mode) {
            this.x = x;
            this.y = y;
            this.mode = mode;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Monitor.class), Monitor.class, "x;y;mode", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->x:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->y:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->mode:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Monitor.class), Monitor.class, "x;y;mode", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->x:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->y:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->mode:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Monitor.class, Object.class), Monitor.class, "x;y;mode", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->x:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->y:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$Monitor;->mode:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public int x() {
            return this.x;
        }

        public int y() {
            return this.y;
        }

        @Nullable
        public VideoMode mode() {
            return this.mode;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode.class */
    public static final class VideoMode extends Record {
        private final int width;
        private final int height;

        public VideoMode(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, VideoMode.class), VideoMode.class, "width;height", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, VideoMode.class), VideoMode.class, "width;height", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, VideoMode.class, Object.class), VideoMode.class, "width;height", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$VideoMode;->height:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public int width() {
            return this.width;
        }

        public int height() {
            return this.height;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask.class */
    public static final class WindowTask extends Record implements Runnable {
        private final long handle;
        private final boolean decorated;
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        public WindowTask(long handle, boolean decorated, int x, int y, int width, int height) {
            this.handle = handle;
            this.decorated = decorated;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WindowTask.class), WindowTask.class, "handle;decorated;x;y;width;height", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->handle:J", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->decorated:Z", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->x:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->y:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WindowTask.class), WindowTask.class, "handle;decorated;x;y;width;height", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->handle:J", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->decorated:Z", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->x:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->y:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WindowTask.class, Object.class), WindowTask.class, "handle;decorated;x;y;width;height", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->handle:J", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->decorated:Z", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->x:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->y:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->width:I", "FIELD:Lnet/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/glfw/window/BorderlessWindow$WindowTask;->height:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public long handle() {
            return this.handle;
        }

        public boolean decorated() {
            return this.decorated;
        }

        public int x() {
            return this.x;
        }

        public int y() {
            return this.y;
        }

        public int width() {
            return this.width;
        }

        public int height() {
            return this.height;
        }

        public WindowTask(long handle, boolean decorated, Rectangle rect) {
            this(handle, decorated, (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        }

        @Override // java.lang.Runnable
        public void run() {
            GLFWUtil.decoratedWindow(this.handle, this.decorated, this.x, this.y, this.width, this.height);
        }
    }
}
