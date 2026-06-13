package net.labymod.core.client.gui.background.position;

import java.util.Objects;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.util.camera.spline.position.Location;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/position/ScreenPositionTransition.class */
public class ScreenPositionTransition extends AbstractPositionTransition {

    @NotNull
    private final Object screen;

    public ScreenPositionTransition(@NotNull Location position, @NotNull CubicBezier curve, long duration, @NotNull Object screen) {
        super(position, curve, duration);
        this.screen = screen;
    }

    @NotNull
    public Object getScreen() {
        return this.screen;
    }

    @Override // net.labymod.core.client.gui.background.position.PositionTransition
    public boolean is(Object value) {
        if ((value instanceof NamedScreen) && (this.screen instanceof NamedScreen)) {
            return (((NamedScreen) this.screen).isOptions() && ((NamedScreen) value).isOptions()) || this.screen.equals(value);
        }
        return this.screen.equals(value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScreenPositionTransition that = (ScreenPositionTransition) o;
        return Objects.equals(this.screen, that.screen);
    }

    public int hashCode() {
        return this.screen.hashCode();
    }
}
