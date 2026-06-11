package net.labymod.core.client.gui.background.position;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.util.camera.spline.position.Location;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/position/AbstractPositionTransition.class */
public abstract class AbstractPositionTransition implements PositionTransition {
    private final Location position;
    private final CubicBezier curve;
    private final long duration;

    protected AbstractPositionTransition(@NotNull Location position, @NotNull CubicBezier curve, long duration) {
        this.position = position;
        this.curve = curve;
        this.duration = duration;
    }

    @Override // net.labymod.core.client.gui.background.position.PositionTransition
    @NotNull
    public Location position() {
        return this.position;
    }

    @Override // net.labymod.core.client.gui.background.position.PositionTransition
    @NotNull
    public CubicBezier curve() {
        return this.curve;
    }

    @Override // net.labymod.core.client.gui.background.position.PositionTransition
    public long getDuration() {
        return this.duration;
    }
}
