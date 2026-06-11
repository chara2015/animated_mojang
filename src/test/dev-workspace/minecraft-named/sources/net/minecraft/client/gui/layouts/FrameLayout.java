package net.minecraft.client.gui.layouts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.gui.layouts.AbstractLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/FrameLayout.class */
public class FrameLayout extends AbstractLayout {
    private final List<ChildContainer> children;
    private int minWidth;
    private int minHeight;
    private final LayoutSettings defaultChildLayoutSettings;

    public FrameLayout() {
        this(0, 0, 0, 0);
    }

    public FrameLayout(int $$0, int $$1) {
        this(0, 0, $$0, $$1);
    }

    public FrameLayout(int $$0, int $$1, int $$2, int $$3) {
        super($$0, $$1, $$2, $$3);
        this.children = new ArrayList();
        this.defaultChildLayoutSettings = LayoutSettings.defaults().align(0.5f, 0.5f);
        setMinDimensions($$2, $$3);
    }

    public FrameLayout setMinDimensions(int $$0, int $$1) {
        return setMinWidth($$0).setMinHeight($$1);
    }

    public FrameLayout setMinHeight(int $$0) {
        this.minHeight = $$0;
        return this;
    }

    public FrameLayout setMinWidth(int $$0) {
        this.minWidth = $$0;
        return this;
    }

    public LayoutSettings newChildLayoutSettings() {
        return this.defaultChildLayoutSettings.copy();
    }

    public LayoutSettings defaultChildLayoutSetting() {
        return this.defaultChildLayoutSettings;
    }

    @Override // net.minecraft.client.gui.layouts.Layout
    public void arrangeElements() {
        super.arrangeElements();
        int $$0 = this.minWidth;
        int $$1 = this.minHeight;
        for (ChildContainer $$2 : this.children) {
            $$0 = Math.max($$0, $$2.getWidth());
            $$1 = Math.max($$1, $$2.getHeight());
        }
        for (ChildContainer $$3 : this.children) {
            $$3.setX(getX(), $$0);
            $$3.setY(getY(), $$1);
        }
        this.width = $$0;
        this.height = $$1;
    }

    public <T extends LayoutElement> T addChild(T t) {
        return (T) addChild(t, newChildLayoutSettings());
    }

    public <T extends LayoutElement> T addChild(T $$0, LayoutSettings $$1) {
        this.children.add(new ChildContainer($$0, $$1));
        return $$0;
    }

    public <T extends LayoutElement> T addChild(T t, Consumer<LayoutSettings> consumer) {
        return (T) addChild(t, (LayoutSettings) Util.make(newChildLayoutSettings(), consumer));
    }

    @Override // net.minecraft.client.gui.layouts.Layout
    public void visitChildren(Consumer<LayoutElement> $$0) {
        this.children.forEach($$1 -> {
            $$0.accept($$1.child);
        });
    }

    public static void centerInRectangle(LayoutElement $$0, int $$1, int $$2, int $$3, int $$4) {
        alignInRectangle($$0, $$1, $$2, $$3, $$4, 0.5f, 0.5f);
    }

    public static void centerInRectangle(LayoutElement $$0, ScreenRectangle $$1) {
        centerInRectangle($$0, $$1.position().x(), $$1.position().y(), $$1.width(), $$1.height());
    }

    public static void alignInRectangle(LayoutElement $$0, ScreenRectangle $$1, float $$2, float $$3) {
        alignInRectangle($$0, $$1.left(), $$1.top(), $$1.width(), $$1.height(), $$2, $$3);
    }

    public static void alignInRectangle(LayoutElement $$0, int $$1, int $$2, int $$3, int $$4, float $$5, float $$6) {
        int width = $$0.getWidth();
        Objects.requireNonNull($$0);
        alignInDimension($$1, $$3, width, (v1) -> {
            r3.setX(v1);
        }, $$5);
        int height = $$0.getHeight();
        Objects.requireNonNull($$0);
        alignInDimension($$2, $$4, height, (v1) -> {
            r3.setY(v1);
        }, $$6);
    }

    public static void alignInDimension(int $$0, int $$1, int $$2, Consumer<Integer> $$3, float $$4) {
        int $$5 = (int) Mth.lerp($$4, 0.0f, $$1 - $$2);
        $$3.accept(Integer.valueOf($$0 + $$5));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/FrameLayout$ChildContainer.class */
    static class ChildContainer extends AbstractLayout.AbstractChildWrapper {
        protected ChildContainer(LayoutElement $$0, LayoutSettings $$1) {
            super($$0, $$1);
        }
    }
}
