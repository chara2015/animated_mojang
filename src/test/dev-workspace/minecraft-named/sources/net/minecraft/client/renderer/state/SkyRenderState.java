package net.minecraft.client.renderer.state;

import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.dimension.DimensionType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/state/SkyRenderState.class */
public class SkyRenderState {
    public boolean shouldRenderDarkDisc;
    public float sunAngle;
    public float moonAngle;
    public float starAngle;
    public float rainBrightness;
    public float starBrightness;
    public int sunriseAndSunsetColor;
    public int skyColor;
    public float endFlashIntensity;
    public float endFlashXAngle;
    public float endFlashYAngle;
    public DimensionType.Skybox skybox = DimensionType.Skybox.NONE;
    public MoonPhase moonPhase = MoonPhase.FULL_MOON;

    public void reset() {
        this.skybox = DimensionType.Skybox.NONE;
    }
}
