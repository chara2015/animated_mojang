package com.mojang.blaze3d.platform;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/GLX.class */
@DontObfuscate
public class GLX {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static String cpuInfo;

    public static int _getRefreshRate(Window $$0) {
        RenderSystem.assertOnRenderThread();
        long $$1 = GLFW.glfwGetWindowMonitor($$0.handle());
        if ($$1 == 0) {
            $$1 = GLFW.glfwGetPrimaryMonitor();
        }
        GLFWVidMode $$2 = $$1 == 0 ? null : GLFW.glfwGetVideoMode($$1);
        if ($$2 == null) {
            return 0;
        }
        return $$2.refreshRate();
    }

    public static String _getLWJGLVersion() {
        return Version.getVersion();
    }

    public static LongSupplier _initGlfw() {
        Window.checkGlfwError(($$0, $$1) -> {
            throw new IllegalStateException(String.format(Locale.ROOT, "GLFW error before init: [0x%X]%s", $$0, $$1));
        });
        List<String> $$02 = Lists.newArrayList();
        GLFWErrorCallback $$12 = GLFW.glfwSetErrorCallback(($$13, $$2) -> {
            String $$3 = $$2 == 0 ? "" : MemoryUtil.memUTF8($$2);
            $$02.add(String.format(Locale.ROOT, "GLFW error during init: [0x%X]%s", Integer.valueOf($$13), $$3));
        });
        if (GLFW.glfwInit()) {
            LongSupplier $$4 = () -> {
                return (long) (GLFW.glfwGetTime() * 1.0E9d);
            };
            for (String $$3 : $$02) {
                LOGGER.error("GLFW error collected during initialization: {}", $$3);
            }
            RenderSystem.setErrorCallback($$12);
            return $$4;
        }
        throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join($$02));
    }

    public static void _setGlfwErrorCallback(GLFWErrorCallbackI $$0) {
        GLFWErrorCallback $$1 = GLFW.glfwSetErrorCallback($$0);
        if ($$1 != null) {
            $$1.free();
        }
    }

    public static boolean _shouldClose(Window $$0) {
        return GLFW.glfwWindowShouldClose($$0.handle());
    }

    public static String _getCpuInfo() {
        if (cpuInfo == null) {
            cpuInfo = "<unknown>";
            try {
                CentralProcessor $$0 = new SystemInfo().getHardware().getProcessor();
                cpuInfo = String.format(Locale.ROOT, "%dx %s", Integer.valueOf($$0.getLogicalProcessorCount()), $$0.getProcessorIdentifier().getName()).replaceAll("\\s+", " ");
            } catch (Throwable th) {
            }
        }
        return cpuInfo;
    }

    public static <T> T make(Supplier<T> $$0) {
        return $$0.get();
    }

    public static <T> T make(T $$0, Consumer<T> $$1) {
        $$1.accept($$0);
        return $$0;
    }
}
