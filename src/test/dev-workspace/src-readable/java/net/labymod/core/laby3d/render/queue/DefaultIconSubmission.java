package net.labymod.core.laby3d.render.queue;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.render.queue.AbstractSubmission;
import net.labymod.api.laby3d.render.queue.SortHint;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/DefaultIconSubmission.class */
public class DefaultIconSubmission extends AbstractSubmission implements IconSubmission, SortHint {
    private static final float DISPLAY_MODE_NORMAL_BASE = 0.0f;
    private static final float DISPLAY_MODE_SEE_THROUGH_BASE = 1.0f;
    private static final int HASH_MASK = 16777215;
    private static final float LOCATION_HASH_SCALE = 1.0E-4f;
    private final Icon icon;
    private final Matrix4f pose;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final int argb;
    private final float sortValue;

    /* JADX WARN: Illegal instructions before constructor call */
    public DefaultIconSubmission(@Nullable RenderState customRenderState, IconSubmission.DisplayMode displayMode, Icon icon, Matrix4f pose, float x, float y, float width, float height, int argb) {
        RenderState renderState;
        if (customRenderState == null) {
            renderState = displayMode.renderState();
        } else {
            renderState = customRenderState;
        }
        super(LevelMaterial.builder(renderState).setTexture(0, icon.getResourceLocation()).useLightmap().build());
        this.icon = icon;
        this.pose = pose;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.argb = argb;
        this.sortValue = computeSortValue(displayMode, icon);
    }

    private float computeSortValue(IconSubmission.DisplayMode display, Icon icon) {
        float f;
        if (display == IconSubmission.DisplayMode.NORMAL) {
            f = 0.0f;
        } else {
            f = DISPLAY_MODE_SEE_THROUGH_BASE;
        }
        float sortValue = f;
        ResourceLocation location = icon.getResourceLocation();
        if (location != null) {
            int hash = location.hashCode();
            float normalized = (hash & HASH_MASK) / 1.6777215E7f;
            sortValue += normalized * LOCATION_HASH_SCALE;
        }
        return sortValue;
    }

    @Override // net.labymod.api.laby3d.render.queue.submissions.IconSubmission
    public Icon icon() {
        return this.icon;
    }

    @Override // net.labymod.api.laby3d.render.queue.submissions.IconSubmission
    public Matrix4f pose() {
        return this.pose;
    }

    @Override // net.labymod.api.laby3d.render.queue.submissions.IconSubmission
    public float x() {
        return this.x;
    }

    @Override // net.labymod.api.laby3d.render.queue.submissions.IconSubmission
    public float y() {
        return this.y;
    }

    @Override // net.labymod.api.laby3d.render.queue.submissions.IconSubmission
    public float width() {
        return this.width;
    }

    @Override // net.labymod.api.laby3d.render.queue.submissions.IconSubmission
    public float height() {
        return this.height;
    }

    @Override // net.labymod.api.laby3d.render.queue.submissions.IconSubmission
    public int argb() {
        return this.argb;
    }

    @Override // net.labymod.api.laby3d.render.queue.SortHint
    public float sortValue() {
        return this.sortValue;
    }
}
