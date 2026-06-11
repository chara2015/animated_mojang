package net.labymod.core.client.render.schematic;

import java.util.function.Consumer;
import net.labymod.core.util.camera.CinematicCamera;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/ShaderCamera.class */
public class ShaderCamera extends CinematicCamera {
    private static Consumer<CinematicCamera> shaderHook;

    public ShaderCamera(float fov) {
        super(fov);
    }

    @Override // net.labymod.core.util.camera.CinematicCamera
    public void setup(float left, float top, float right, float bottom, float partialTicks) {
        super.setup(left, top, right, bottom, partialTicks);
        if (shaderHook != null) {
            shaderHook.accept(this);
        }
    }

    public static void setShaderHook(Consumer<CinematicCamera> cameraConsumer) {
        shaderHook = cameraConsumer;
    }
}
