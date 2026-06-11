package net.labymod.api.event.client.render.font;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/font/StringRenderEvent.class */
public class StringRenderEvent extends DefaultCancellable implements Event {
    private final Stack stack;
    private final String text;
    private final float x;
    private final float y;
    private int color;
    private boolean shadow;

    public StringRenderEvent(@NotNull Stack stack, @NotNull String text, float x, float y, int color, boolean shadow) {
        this.stack = stack;
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadow = shadow;
    }

    @NotNull
    public Stack stack() {
        return this.stack;
    }

    @NotNull
    public String getText() {
        return this.text;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isShadow() {
        return this.shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}
