package net.labymod.api.laby3d;

import java.util.Locale;
import net.labymod.api.util.logging.Logging;
import net.labymod.laby3d.api.GraphicsDeviceInfo;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.util.property.SystemProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/GraphicsWorkarounds.class */
public final class GraphicsWorkarounds {
    private static final Logging LOGGER = Logging.getLogger();
    private static GraphicsWorkarounds instance;
    private final RenderDevice renderDevice;
    private final boolean useDrawBlitter;

    private GraphicsWorkarounds(RenderDevice renderDevice) {
        this.renderDevice = renderDevice;
        this.useDrawBlitter = isIntel(renderDevice.info());
        if (this.useDrawBlitter) {
            LOGGER.info("Using draw blitter workaround for Intel iGPU and GPU", new Object[0]);
        }
    }

    public static GraphicsWorkarounds get(RenderDevice renderDevice) {
        GraphicsWorkarounds workarounds = instance;
        if (workarounds == null) {
            GraphicsWorkarounds graphicsWorkarounds = new GraphicsWorkarounds(renderDevice);
            workarounds = graphicsWorkarounds;
            instance = graphicsWorkarounds;
        }
        return workarounds;
    }

    public boolean useDrawBlitter() {
        return this.useDrawBlitter;
    }

    private boolean isIntel(GraphicsDeviceInfo deviceInfo) {
        if (SystemProperties.DISABLE_INTEL_BLIT_FRAMEBUFFER_WORKAROUND.get().booleanValue()) {
            return false;
        }
        String renderer = deviceInfo.deviceName().toLowerCase(Locale.ROOT);
        return renderer.contains("intel");
    }
}
