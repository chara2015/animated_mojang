package net.minecraft.client.gui.render.state.pip;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.ScreenArea;
import org.joml.Matrix3x2f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/pip/PictureInPictureRenderState.class */
public interface PictureInPictureRenderState extends ScreenArea {
    public static final Matrix3x2f IDENTITY_POSE = new Matrix3x2f();

    int x0();

    int x1();

    int y0();

    int y1();

    float scale();

    ScreenRectangle scissorArea();

    default Matrix3x2f pose() {
        return IDENTITY_POSE;
    }

    static ScreenRectangle getBounds(int $$0, int $$1, int $$2, int $$3, ScreenRectangle $$4) {
        ScreenRectangle $$5 = new ScreenRectangle($$0, $$1, $$2 - $$0, $$3 - $$1);
        return $$4 != null ? $$4.intersection($$5) : $$5;
    }
}
