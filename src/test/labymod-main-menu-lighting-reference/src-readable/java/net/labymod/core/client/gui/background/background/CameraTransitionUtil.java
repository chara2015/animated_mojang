package net.labymod.core.client.gui.background;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.background.position.ResourceLocationPositionTransition;
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.util.camera.spline.position.Location;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/CameraTransitionUtil.class */
public final class CameraTransitionUtil {
    private static final ResourceLocation DYNAMIC_TRANSITION = ResourceLocation.create("labymod", "dynamic_transition");

    public static void execute(ResourceLocation identifier) {
        DynamicBackgroundController controller = LabyMod.references().dynamicBackgroundController();
        controller.executeTransition(identifier);
    }

    public static void execute(double x, double y, double z, double yaw, double pitch, double roll) {
        DynamicBackgroundController controller = LabyMod.references().dynamicBackgroundController();
        Location location = new Location(x, y, z, yaw, pitch, roll);
        controller.executeTransition(new ResourceLocationPositionTransition(location, ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_CURVE, 500L, DYNAMIC_TRANSITION));
    }
}
