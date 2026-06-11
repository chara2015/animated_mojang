package net.labymod.core.laby3d.opengl;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlPolygonOffsetState.class */
public class GlPolygonOffsetState extends GlBooleanState {
    private float factor;
    private float units;

    public GlPolygonOffsetState() {
        super(GlConst.GL_POLYGON_OFFSET_FILL);
        this.factor = 0.0f;
        this.units = 0.0f;
    }

    public float getFactor() {
        return this.factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getUnits() {
        return this.units;
    }

    public void setUnits(float units) {
        this.units = units;
    }
}
