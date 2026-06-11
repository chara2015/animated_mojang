package net.labymod.api.client.world.canvas.template;

import java.util.Arrays;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.canvas.CanvasMeta;
import net.labymod.api.client.world.signobject.object.SignCanvasSize;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/template/CanvasTemplate.class */
public class CanvasTemplate extends SignObjectTemplate {
    private static final float DEFAULT_RATIO = 1.0f;
    private static final FloatVector3 DEFAULT_OFFSET = new FloatVector3(0.0f, 0.0f, DEFAULT_RATIO);
    private final float widthToHeightRatio;
    private final FloatVector3 renderOffset;
    private final MinMax widthBlocks;
    private final MinMax heightBlocks;

    protected CanvasTemplate(ResourceLocation location, float widthToHeightRatio, FloatVector3 renderOffset, MinMax widthBlocks, MinMax heightBlocks, InstalledAddonInfo addon) {
        super(location, addon);
        this.widthToHeightRatio = widthToHeightRatio;
        this.renderOffset = renderOffset;
        this.widthBlocks = widthBlocks;
        this.heightBlocks = heightBlocks;
    }

    public static Builder builder(ResourceLocation location) {
        return builder(location, location.getNamespace());
    }

    public static Builder builder(ResourceLocation location, String addonNamespace) {
        return new Builder(location, getAddon(addonNamespace));
    }

    public static CanvasTemplate simple(ResourceLocation location) {
        return simple(location, location.getNamespace());
    }

    public static CanvasTemplate simple(ResourceLocation location, String addonNamespace) {
        return new CanvasTemplate(location, DEFAULT_RATIO, DEFAULT_OFFSET, MinMax.positive(), MinMax.positive(), getAddon(addonNamespace));
    }

    public float widthToHeightRatio() {
        return this.widthToHeightRatio;
    }

    public FloatVector3 renderOffset() {
        return this.renderOffset;
    }

    public MinMax widthBlocks() {
        return this.widthBlocks;
    }

    public MinMax heightBlocks() {
        return this.heightBlocks;
    }

    public float calculateWidth(float height) {
        return height * this.widthToHeightRatio;
    }

    public float calculateHeight(float width) {
        return width / this.widthToHeightRatio;
    }

    @Override // net.labymod.api.client.world.signobject.template.SignObjectTemplate
    public CanvasMeta parseMeta(String[] meta) {
        if (meta.length == 0) {
            return null;
        }
        String[] size = meta[0].split("x");
        if (size.length != 2) {
            return null;
        }
        try {
            float widthBlocks = Float.parseFloat(size[0]);
            float heightBlocks = Float.parseFloat(size[1]);
            if ((widthBlocks == 0.0f && heightBlocks == 0.0f) || !this.widthBlocks.isValid(widthBlocks) || !this.heightBlocks.isValid(heightBlocks)) {
                return null;
            }
            return new CanvasMeta(this, (String[]) Arrays.copyOfRange(meta, 1, meta.length), new SignCanvasSize(widthBlocks, heightBlocks));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/template/CanvasTemplate$Builder.class */
    public static class Builder {
        private final ResourceLocation location;
        private final InstalledAddonInfo addon;
        private FloatVector3 renderOffset;
        private float widthToHeightRatio = CanvasTemplate.DEFAULT_RATIO;
        private MinMax widthBlocks;
        private MinMax heightBlocks;

        private Builder(ResourceLocation location, InstalledAddonInfo addon) {
            this.location = location;
            this.addon = addon;
        }

        public Builder ratio(float widthToHeightRatio) {
            this.widthToHeightRatio = widthToHeightRatio;
            return this;
        }

        public Builder ratio(float sampleWidth, float sampleHeight) {
            this.widthToHeightRatio = sampleWidth / sampleHeight;
            return this;
        }

        public Builder ratio1x1() {
            return ratio(CanvasTemplate.DEFAULT_RATIO);
        }

        public Builder ratio16x9() {
            return ratio(16.0f, 9.0f);
        }

        public Builder restrictWidth(MinMax widthBlocks) {
            this.widthBlocks = widthBlocks;
            return this;
        }

        public Builder restrictHeight(MinMax heightBlocks) {
            this.heightBlocks = heightBlocks;
            return this;
        }

        public Builder renderOffset(FloatVector3 renderOffset) {
            this.renderOffset = renderOffset;
            return this;
        }

        public CanvasTemplate build() {
            return new CanvasTemplate(this.location, this.widthToHeightRatio, this.renderOffset != null ? this.renderOffset : CanvasTemplate.DEFAULT_OFFSET, this.widthBlocks != null ? this.widthBlocks : MinMax.positive(), this.heightBlocks != null ? this.heightBlocks : MinMax.positive(), this.addon);
        }
    }
}
