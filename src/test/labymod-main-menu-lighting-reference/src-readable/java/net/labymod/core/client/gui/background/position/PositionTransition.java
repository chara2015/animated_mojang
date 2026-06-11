package net.labymod.core.client.gui.background.position;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.util.camera.spline.position.Location;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/position/PositionTransition.class */
public interface PositionTransition {
    @NotNull
    Location position();

    @NotNull
    CubicBezier curve();

    long getDuration();

    boolean is(Object obj);
}
