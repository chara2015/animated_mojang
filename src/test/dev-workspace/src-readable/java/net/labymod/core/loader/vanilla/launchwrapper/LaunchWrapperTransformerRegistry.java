package net.labymod.core.loader.vanilla.launchwrapper;

import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.OperatingSystem;
import net.labymod.util.property.SystemProperties;
import net.minecraft.launchwrapper.LaunchClassLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/LaunchWrapperTransformerRegistry.class */
public class LaunchWrapperTransformerRegistry {
    private static final String TRANSFORMER_PACKAGE = "net.labymod.core.loader.vanilla.launchwrapper.transformer.";
    private static final String PATCH_TRANSFORMER_PACKAGE = "net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.";
    private final LaunchClassLoader classLoader;
    private PlatformClassloader platformClassloader;
    private String runningVersion;
    private boolean labyModDevEnvironment;

    public LaunchWrapperTransformerRegistry(LaunchClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void registerEarlyTransformers() {
        this.classLoader.registerTransformer(patchClass("gson.TypeTokenTransformer"));
        this.classLoader.registerTransformer(patchClass("gson.ReflectiveTypeAdapterFactoryTransformer"));
    }

    public void registerPreTransformers() {
        if (this.labyModDevEnvironment) {
            this.classLoader.registerTransformer(transformerClass("GuiScreenV18Transformer"));
        }
        boolean legacyGuava = this.platformClassloader.searchOnClasspath("guava-17.0");
        if (this.labyModDevEnvironment && !legacyGuava && this.runningVersion.equals("1.8.9")) {
            this.classLoader.registerTransformer(patchClass("guava.GuavaJreTransformer"));
        }
        this.classLoader.registerTransformer(transformerClass("GameScreenTransformer"));
        this.classLoader.registerTransformer(patchClass("RenamedFromServiceTransformer"));
        this.classLoader.registerTransformer(patchClass("compatibility.WorldObjectTransformer"));
        this.classLoader.registerTransformer(patchClass("optifine.PlayerConfigurationsTransformer"));
        this.classLoader.registerTransformer(patchClass("java.StringFormatTransformer"));
        this.classLoader.registerTransformer(patchClass("guava.ObjectsTransformer"));
        this.classLoader.registerTransformer(patchClass("guava.IteratorsTransformer"));
        this.classLoader.registerTransformer(patchClass("guava.PreconditionsTransformer"));
        this.classLoader.registerTransformer(patchClass("ice4j.SocketTransformer"));
        this.classLoader.registerTransformer(patchClass("lwjgl.GLFWTransformer"));
    }

    public void registerPostTransformers() {
        boolean debugOpenGLCalls = SystemProperties.OPENGL_CALL.get().booleanValue();
        if (debugOpenGLCalls) {
            this.classLoader.registerTransformer(patchClass("lwjgl.VertexArrayObjectTrackerTransformer"));
        }
        if (debugOpenGLCalls) {
            this.classLoader.registerTransformer(patchClass("lwjgl.TextureDebuggerTransformer"));
        }
        if (this.runningVersion.equals("1.8.9") || this.runningVersion.equals("1.12.2")) {
            this.classLoader.registerTransformer(patchClass("lwjgl.LWJGLContextTransformer"));
            this.classLoader.registerTransformer(patchClass("lwjgl.GLCapabilitiesExtensionsTransformer"));
        }
        if (OperatingSystem.isOSX() && (SystemProperties.RENDER_DEVICE_USER_DEBUG.get().booleanValue() || SystemProperties.RENDER_DEVICE_USER_DEBUG.get().booleanValue())) {
            this.classLoader.registerTransformer(patchClass("lwjgl.GlErrorContextTrackerTransformer"));
        }
        this.classLoader.registerTransformer(patchClass("lwjgl.GlStateTrackerTransformer"));
        this.platformClassloader.getAccessWidener().findAndCreateAccessWidener(this.classLoader, "labymod", this.runningVersion);
    }

    public void setRunningVersion(String runningVersion) {
        this.runningVersion = runningVersion;
        PlatformEnvironment.setRunningVersion(runningVersion);
    }

    public void setLabyModDevEnvironment(boolean labyModDevEnvironment) {
        this.labyModDevEnvironment = labyModDevEnvironment;
    }

    public void setPlatformClassloader(PlatformClassloader platformClassloader) {
        this.platformClassloader = platformClassloader;
        PlatformEnvironment.setPlatformClassloader(platformClassloader);
    }

    private String transformerClass(String path) {
        return "net.labymod.core.loader.vanilla.launchwrapper.transformer." + path;
    }

    private String patchClass(String path) {
        return "net.labymod.core.loader.vanilla.launchwrapper.transformer.patch." + path;
    }
}
