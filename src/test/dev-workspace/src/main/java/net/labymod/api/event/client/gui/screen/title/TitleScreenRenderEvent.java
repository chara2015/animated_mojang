package net.labymod.api.event.client.gui.screen.title;

import java.util.Objects;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/title/TitleScreenRenderEvent.class */
public final class TitleScreenRenderEvent implements Event {
    private final Phase phase;
    private final Stack stack;
    private final MutableMouse mouse;
    private final float partialTicks;

    public TitleScreenRenderEvent(Phase phase, Stack stack, MutableMouse mouse, float partialTicks) {
        this.phase = phase;
        this.stack = stack;
        this.mouse = mouse;
        this.partialTicks = partialTicks;
    }

    public Phase phase() {
        return this.phase;
    }

    public Stack stack() {
        return this.stack;
    }

    public MutableMouse mouse() {
        return this.mouse;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        TitleScreenRenderEvent that = (TitleScreenRenderEvent) obj;
        return Objects.equals(this.stack, that.stack) && Objects.equals(this.mouse, that.mouse) && Float.floatToIntBits(this.partialTicks) == Float.floatToIntBits(that.partialTicks);
    }

    public int hashCode() {
        int result = this.stack != null ? this.stack.hashCode() : 0;
        return (31 * ((31 * result) + (this.mouse != null ? this.mouse.hashCode() : 0))) + (this.partialTicks != 0.0f ? Float.floatToIntBits(this.partialTicks) : 0);
    }

    public String toString() {
        return "TitleScreenPostRenderEvent[stack=" + String.valueOf(this.stack) + ", mouse=" + String.valueOf(this.mouse) + ", partialTicks=" + this.partialTicks + "]";
    }
}
