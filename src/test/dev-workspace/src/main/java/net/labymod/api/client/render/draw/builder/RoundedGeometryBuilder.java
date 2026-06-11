package net.labymod.api.client.render.draw.builder;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.laby3d.shaders.block.RoundDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.RoundDataUniformBlockData;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/RoundedGeometryBuilder.class */
@Referenceable
public interface RoundedGeometryBuilder {
    RoundedGeometryBuilder left(float f);

    RoundedGeometryBuilder top(float f);

    RoundedGeometryBuilder right(float f);

    RoundedGeometryBuilder bottom(float f);

    RoundedGeometryBuilder pos(Rectangle rectangle);

    RoundedGeometryBuilder pos(float f, float f2, float f3, float f4);

    RoundedGeometryBuilder leftTopRadius(float f);

    RoundedGeometryBuilder rightTopRadius(float f);

    RoundedGeometryBuilder leftBottomRadius(float f);

    RoundedGeometryBuilder rightBottomRadius(float f);

    RoundedGeometryBuilder radius(float f, float f2, float f3, float f4);

    RoundedGeometryBuilder lowerEdgeSoftness(float f);

    RoundedGeometryBuilder upperEdgeSoftness(float f);

    RoundedGeometryBuilder borderThickness(float f);

    RoundedGeometryBuilder borderSoftness(float f);

    RoundedGeometryBuilder borderColor(int i);

    RoundedGeometryBuilder borderRadius(BorderRadius borderRadius);

    RoundedData build();

    default RoundedGeometryBuilder radius(float radius) {
        return radius(radius, radius, radius, radius);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/RoundedGeometryBuilder$RoundedDataApplier.class */
    public static class RoundedDataApplier {
        public static void apply(RoundedData data, CommandBuffer cmd) {
            RoundDataUniformBlockData roundData = new RoundDataUniformBlockData();
            roundData.setBorderWidth(data.borderThickness());
            roundData.setBorderFadeDistance(data.borderSoftness());
            ColorFormat format = ColorFormat.ARGB32;
            int borderColor = ColorUtil.combineAlpha(data.borderColor());
            float red = format.normalizedRed(borderColor);
            float green = format.normalizedGreen(borderColor);
            float blue = format.normalizedBlue(borderColor);
            float alpha = format.normalizedAlpha(borderColor);
            roundData.borderColor().set(red, green, blue, alpha);
            roundData.setInnerShapeSoftness(data.lowerEdgeSoftness());
            roundData.setOuterShapeSoftness(data.upperEdgeSoftness());
            roundData.cornerRoundingRadius().set(data.rightBottomRadius(), data.rightTopRadius(), data.leftBottomRadius(), data.leftTopRadius());
            roundData.rectangleDimensions().set(data.right() - data.left(), data.bottom() - data.top());
            roundData.rectangleBounds().set(data.left(), data.top(), data.right(), data.bottom());
            cmd.bindUniformBlock(RoundDataUniformBlock.NAME, Laby.references().laby3D().roundData());
            cmd.bindUniformBlockData(RoundDataUniformBlock.NAME, roundData);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/RoundedGeometryBuilder$RoundedData.class */
    public static class RoundedData {
        private final float left;
        private final float top;
        private final float right;
        private final float bottom;
        private final float leftTopRadius;
        private final float rightTopRadius;
        private final float leftBottomRadius;
        private final float rightBottomRadius;
        private final float lowerEdgeSoftness;
        private final float upperEdgeSoftness;
        private final float borderThickness;
        private final float borderSoftness;
        private final int borderColor;

        public RoundedData(float left, float top, float right, float bottom, float leftTopRadius, float rightTopRadius, float leftBottomRadius, float rightBottomRadius, float lowerEdgeSoftness, float upperEdgeSoftness, float borderThickness, float borderSoftness, int borderColor) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.leftTopRadius = leftTopRadius;
            this.rightTopRadius = rightTopRadius;
            this.leftBottomRadius = leftBottomRadius;
            this.rightBottomRadius = rightBottomRadius;
            this.lowerEdgeSoftness = lowerEdgeSoftness;
            this.upperEdgeSoftness = upperEdgeSoftness;
            this.borderThickness = borderThickness;
            this.borderSoftness = borderSoftness;
            this.borderColor = borderColor;
        }

        public float left() {
            return this.left;
        }

        public float top() {
            return this.top;
        }

        public float right() {
            return this.right;
        }

        public float bottom() {
            return this.bottom;
        }

        public float leftTopRadius() {
            return this.leftTopRadius;
        }

        public float rightTopRadius() {
            return this.rightTopRadius;
        }

        public float leftBottomRadius() {
            return this.leftBottomRadius;
        }

        public float rightBottomRadius() {
            return this.rightBottomRadius;
        }

        public float lowerEdgeSoftness() {
            return this.lowerEdgeSoftness;
        }

        public float upperEdgeSoftness() {
            return this.upperEdgeSoftness;
        }

        public float borderThickness() {
            return this.borderThickness;
        }

        public float borderSoftness() {
            return this.borderSoftness;
        }

        public int borderColor() {
            return this.borderColor;
        }
    }
}
