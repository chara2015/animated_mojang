package net.minecraft.client.gui.layouts;

import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/SpacerElement.class */
public class SpacerElement implements LayoutElement {
    private int x;
    private int y;
    private final int width;
    private final int height;

    public SpacerElement(int $$0, int $$1) {
        this(0, 0, $$0, $$1);
    }

    public SpacerElement(int $$0, int $$1, int $$2, int $$3) {
        this.x = $$0;
        this.y = $$1;
        this.width = $$2;
        this.height = $$3;
    }

    public static SpacerElement width(int $$0) {
        return new SpacerElement($$0, 0);
    }

    public static SpacerElement height(int $$0) {
        return new SpacerElement(0, $$0);
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public void setX(int $$0) {
        this.x = $$0;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public void setY(int $$0) {
        this.y = $$0;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getX() {
        return this.x;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getY() {
        return this.y;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getWidth() {
        return this.width;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getHeight() {
        return this.height;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public void visitWidgets(Consumer<AbstractWidget> $$0) {
    }
}
