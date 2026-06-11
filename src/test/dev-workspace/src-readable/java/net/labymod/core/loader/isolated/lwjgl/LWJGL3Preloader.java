package net.labymod.core.loader.isolated.lwjgl;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import net.labymod.core.util.logging.DefaultLoggingFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/lwjgl/LWJGL3Preloader.class */
public class LWJGL3Preloader implements IsolatedLibraryFinishHandler {
    private static final String GROUP = "org.lwjgl";
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger("LWJGL3 Preloader");
    private static final String[] CLASSES = {".opengl.GL", ".system.MemoryStack", ".system.macosx.MacOSXLibraryBundle", ".glfw.GLFWCursorPosCallback", ".glfw.GLFWKeyCallback", ".glfw.GLFWCharModsCallback", ".glfw.GLFWNativeNSGL", ".glfw.GLFWMouseButtonCallback", ".glfw.GLFWNativeWGL", ".glfw.GLFWMonitorCallback", ".glfw.GLFWErrorCallback", ".glfw.GLFWCursorEnterCallback", ".glfw.package-info", ".glfw.GLFWNativeWin32", ".glfw.GLFWWindowRefreshCallback", ".glfw.GLFW", ".glfw.GLFWWindowContentScaleCallback", ".glfw.GLFWVulkan", ".glfw.GLFWNativeGLX", ".glfw.GLFWGammaRamp", ".glfw.GLFWWindowIconifyCallback", ".glfw.GLFWScrollCallback", ".glfw.GLFWNativeEGL", ".glfw.EventLoop", ".glfw.GLFWNativeCocoa", ".glfw.GLFWWindowSizeCallback", ".glfw.GLFWWindowPosCallback", ".glfw.GLFWWindowMaximizeCallback", ".glfw.GLFWFramebufferSizeCallback", ".glfw.GLFWImage", ".glfw.GLFWJoystickCallback", ".glfw.GLFWWindowCloseCallback", ".glfw.GLFWDropCallback", ".glfw.GLFWWindowFocusCallback", ".glfw.GLFWVidMode", ".glfw.Callbacks", ".glfw.GLFWCharCallback", ".glfw.GLFWNativeWayland", ".glfw.GLFWNativeX11", ".glfw.GLFWGamepadState"};

    @Override // net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler
    public void onAccept(IsolatedLibrary library) {
    }

    @Override // net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler
    public void onFinish() {
        PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
        for (Path file : IsolatedClassLoaders.LWJGL_CLASS_LOADER.getFiles()) {
            platformClassloader.addPath(file);
        }
        ClassLoader classLoader = platformClassloader.getPlatformClassloader();
        for (String cls : CLASSES) {
            String finalClass = "org.lwjgl" + cls;
            try {
                classLoader.loadClass(finalClass);
                LOGGER.info("Preloaded " + finalClass, new Object[0]);
            } catch (ClassNotFoundException e) {
                LOGGER.error(dumpClassPath(platformClassloader), new Object[0]);
                throw new RuntimeException("Failed to preload " + finalClass);
            }
        }
    }

    private String dumpClassPath(PlatformClassloader platformClassloader) {
        StringBuilder builder = new StringBuilder();
        builder.append("ClassPath:").append(System.lineSeparator());
        for (URL url : platformClassloader.getClasspath()) {
            try {
                Path path = Paths.get(url.toURI());
                builder.append("\t").append("- ");
                if (Files.isDirectory(path, new LinkOption[0])) {
                    builder.append(path).append("[DIRECTORY]");
                } else {
                    long bytes = getFileSize(path);
                    builder.append(path).append("(File Size: ").append(convertBytes(bytes));
                    if (IOUtil.isCorrupted(path)) {
                        builder.append(" , File is corrupted");
                    }
                    builder.append(")");
                }
            } catch (Exception e) {
                builder.append("\t").append("- ").append(url);
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String convertBytes(long bytes) {
        if (bytes <= 1024) {
            return bytes + "B";
        }
        if (bytes <= 1048576) {
            return (bytes / 1024) + "KB";
        }
        return (bytes / 1048576) + "MB";
    }

    private long getFileSize(Path file) {
        try {
            return Files.size(file);
        } catch (Throwable th) {
            return -1L;
        }
    }
}
