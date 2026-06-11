package net.minecraft.client.gui.font;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.chat.Style;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/EmptyArea.class */
public final class EmptyArea extends Record implements ActiveArea {
    private final float x;
    private final float y;
    private final float advance;
    private final float ascent;
    private final float height;
    private final Style style;
    public static final float DEFAULT_HEIGHT = 9.0f;
    public static final float DEFAULT_ASCENT = 7.0f;

    public EmptyArea(float $$0, float $$1, float $$2, float $$3, float $$4, Style $$5) {
        this.x = $$0;
        this.y = $$1;
        this.advance = $$2;
        this.ascent = $$3;
        this.height = $$4;
        this.style = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EmptyArea.class), EmptyArea.class, "x;y;advance;ascent;height;style", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->x:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->y:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->advance:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->ascent:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->height:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->style:Lnet/minecraft/network/chat/Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EmptyArea.class), EmptyArea.class, "x;y;advance;ascent;height;style", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->x:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->y:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->advance:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->ascent:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->height:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->style:Lnet/minecraft/network/chat/Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EmptyArea.class, Object.class), EmptyArea.class, "x;y;advance;ascent;height;style", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->x:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->y:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->advance:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->ascent:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->height:F", "FIELD:Lnet/minecraft/client/gui/font/EmptyArea;->style:Lnet/minecraft/network/chat/Style;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float advance() {
        return this.advance;
    }

    public float ascent() {
        return this.ascent;
    }

    public float height() {
        return this.height;
    }

    @Override // net.minecraft.client.gui.font.ActiveArea
    public Style style() {
        return this.style;
    }

    @Override // net.minecraft.client.gui.font.ActiveArea
    public float activeLeft() {
        return this.x;
    }

    @Override // net.minecraft.client.gui.font.ActiveArea
    public float activeTop() {
        return (this.y + 7.0f) - this.ascent;
    }

    @Override // net.minecraft.client.gui.font.ActiveArea
    public float activeRight() {
        return this.x + this.advance;
    }

    @Override // net.minecraft.client.gui.font.ActiveArea
    public float activeBottom() {
        return activeTop() + this.height;
    }
}
