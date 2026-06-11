package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.glfw.ImGuiImplGlfw;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.event.labymod.debug.ImGuiInitializeEvent;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gfx.imgui.window.ControlImGuiWindow;
import net.labymod.core.util.classpath.ClasspathUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/ImGuiPipeline.class */
public class ImGuiPipeline {
    private static final String DEFAULT_IMGUI_INI = "assets/labymod/data/default_imgui.ini";
    private static final Logging LOGGER = Logging.getLogger();
    private static ImGuiPipeline instance;
    private long windowPointer;
    private ImGuiWindow controlWindow;
    private final ImGuiImplGlfw glfw = new ImGuiImplGlfw();
    private final ImGuiGlPipeline glPipeline = new ImGuiGlPipeline();
    private boolean initialized = false;

    private ImGuiPipeline() {
        DebugRegistry.DEBUG_WINDOWS.addListener(value -> {
            if (!value) {
                render();
                renderEmptyFrame();
            }
        });
    }

    public static ImGuiPipeline getInstance() {
        if (instance == null) {
            instance = new ImGuiPipeline();
        }
        return instance;
    }

    public void initialize(long windowPointer) {
        initializeImGui(windowPointer);
        this.windowPointer = windowPointer;
        if (!this.initialized) {
            return;
        }
        Laby.fireEvent(new ImGuiInitializeEvent(Laby.references().controlEntryRegistry()));
        this.controlWindow = new ControlImGuiWindow();
    }

    public void renderFrame() {
        if (!this.initialized || !isDebugWindowsEnabled()) {
            return;
        }
        render();
    }

    private void render() {
        if (!this.initialized) {
            return;
        }
        GLCapabilities capabilities = GL.getCapabilities();
        if (capabilities.glPushMatrix != 0) {
            GL11.glPushMatrix();
        }
        this.glfw.newFrame();
        ImGui.newFrame();
        process();
        ImGui.render();
        endFrame();
        if (capabilities.glPopMatrix != 0) {
            GL11.glPopMatrix();
        }
    }

    private void process() {
        if (isDebugWindowsEnabled()) {
            this.controlWindow.render();
        }
    }

    @Deprecated(forRemoval = true, since = "4.2.34")
    private void renderEmptyFrame() {
        this.glfw.newFrame();
        ImGui.newFrame();
        ImGui.render();
        endFrame();
    }

    private boolean isDebugWindowsEnabled() {
        return DebugRegistry.DEBUG_WINDOWS.isEnabled();
    }

    private void endFrame() {
        this.glPipeline.renderData(ImGui.getDrawData());
        if (ImGui.getIO().hasConfigFlags(1024)) {
            long currentContext = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(currentContext);
        }
    }

    private void initializeImGui(long windowPointer) {
        try {
            ImGui.createContext();
            this.initialized = true;
            ImGuiIO io = ImGui.getIO();
            io.addConfigFlags(17472);
            io.setConfigViewportsNoTaskBarIcon(true);
            io.setConfigViewportsNoAutoMerge(true);
            io.setConfigMacOSXBehaviors(OperatingSystem.isOSX());
            initializeIniFile(io);
            prepareFontAtlas(io);
            if (io.hasConfigFlags(1024)) {
                ImGuiStyle style = ImGui.getStyle();
                style.setWindowRounding(0.0f);
                style.setColor(2, ImGui.getColorU32(2, 1.0f));
            }
            ImGui.setAssertCallback(new LabyImAssertCallback());
            this.glfw.init(windowPointer, true);
            initGl();
        } catch (UnsatisfiedLinkError error) {
            LOGGER.warn("Failed to initialize ImGui", error);
        }
    }

    private void initializeIniFile(ImGuiIO io) {
        Path userImGuiPath = Constants.Files.LABYMOD_DIRECTORY.resolve("imgui.ini");
        if (!Files.exists(userImGuiPath, new LinkOption[0])) {
            try {
                InputStream stream = ClasspathUtil.getResourceAsInputStream("labymod", DEFAULT_IMGUI_INI);
                try {
                    byte[] data = IOUtil.readBytes(stream);
                    Files.write(userImGuiPath, data, new OpenOption[0]);
                    if (stream != null) {
                        stream.close();
                    }
                } finally {
                }
            } catch (IOException exception) {
                LOGGER.warn("Could not load the default imgui config", exception);
            }
        }
        io.setIniFilename("labymod-neo/imgui.ini");
    }

    private void initGl() {
        String glslVersion = null;
        if (OperatingSystem.isOSX()) {
            if (PlatformEnvironment.isNoShader()) {
                glslVersion = "#version 110";
            } else {
                glslVersion = "#version 150";
            }
        }
        this.glPipeline.init(glslVersion);
    }

    private void prepareFontAtlas(ImGuiIO io) {
        ImFontAtlas fonts = io.getFonts();
        fonts.addFontDefault();
    }
}
