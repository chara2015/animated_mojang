package net.labymod.core.client.gui.background.position;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.util.camera.spline.position.Location;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/position/ResourceLocationPositionTransition.class */
public class ResourceLocationPositionTransition extends AbstractPositionTransition {

    @NotNull
    private final ResourceLocation location;

    public ResourceLocationPositionTransition(@NotNull Location position, @NotNull CubicBezier curve, long duration, @NotNull ResourceLocation location) {
        super(position, curve, duration);
        this.location = location;
    }

    @Override // net.labymod.core.client.gui.background.position.PositionTransition
    public boolean is(Object value) {
        if (!(value instanceof ResourceLocation)) {
            return false;
        }
        ResourceLocation resourceLocation = (ResourceLocation) value;
        return resourceLocation.equals(this.location);
    }
}
