package net.labymod.api.client.gui.screen.state;

import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/RoundedData.class */
public final class RoundedData {

    @Nullable
    private final Rectangle bounds;
    private final float leftTopRadius;
    private final float rightTopRadius;
    private final float leftBottomRadius;
    private final float rightBottomRadius;
    private final float lowerEdgeSoftness;
    private final float upperEdgeSoftness;
    private final float borderThickness;
    private final float borderSoftness;
    private final int borderColor;

    RoundedData(@Nullable Rectangle bounds, float leftTopRadius, float rightTopRadius, float leftBottomRadius, float rightBottomRadius, float lowerEdgeSoftness, float upperEdgeSoftness, float borderThickness, float borderSoftness, int borderColor) {
        this.bounds = bounds;
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

    public static Builder builder() {
        return new Builder();
    }

    @Nullable
    public Rectangle getBounds() {
        return this.bounds;
    }

    public float getLeftTopRadius() {
        return this.leftTopRadius;
    }

    public float getRightTopRadius() {
        return this.rightTopRadius;
    }

    public float getLeftBottomRadius() {
        return this.leftBottomRadius;
    }

    public float getRightBottomRadius() {
        return this.rightBottomRadius;
    }

    public float getLowerEdgeSoftness() {
        return this.lowerEdgeSoftness;
    }

    public float getUpperEdgeSoftness() {
        return this.upperEdgeSoftness;
    }

    public float getBorderThickness() {
        return this.borderThickness;
    }

    public float getBorderSoftness() {
        return this.borderSoftness;
    }

    public int getBorderColor() {
        return this.borderColor;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/RoundedData$Builder.class */
    public static class Builder {
        private Rectangle bounds;
        private float leftTopRadius;
        private float rightTopRadius;
        private float leftBottomRadius;
        private float rightBottomRadius;
        private float borderSoftness;
        private float lowerEdgeSoftness = -0.5f;
        private float upperEdgeSoftness = 0.0f;
        private float borderThickness = 0.0f;
        private int borderColor = 0;

        public Builder setBounds(float left, float top, float right, float bottom) {
            return setBounds(Rectangle.absolute(left, top, right, bottom));
        }

        public Builder setBounds(Rectangle bounds) {
            this.bounds = bounds;
            return this;
        }

        public Builder setLeftTopRadius(float leftTopRadius) {
            this.leftTopRadius = leftTopRadius;
            return this;
        }

        public Builder setRightTopRadius(float rightTopRadius) {
            this.rightTopRadius = rightTopRadius;
            return this;
        }

        public Builder setLeftBottomRadius(float leftBottomRadius) {
            this.leftBottomRadius = leftBottomRadius;
            return this;
        }

        public Builder setRightBottomRadius(float rightBottomRadius) {
            this.rightBottomRadius = rightBottomRadius;
            return this;
        }

        public Builder setRadius(float radius) {
            return setLeftTopRadius(radius).setRightTopRadius(radius).setLeftBottomRadius(radius).setRightBottomRadius(radius);
        }

        public Builder setLowerEdgeSoftness(float lowerEdgeSoftness) {
            this.lowerEdgeSoftness = lowerEdgeSoftness;
            return this;
        }

        public Builder setUpperEdgeSoftness(float upperEdgeSoftness) {
            this.upperEdgeSoftness = upperEdgeSoftness;
            return this;
        }

        public Builder setBorderThickness(float borderThickness) {
            this.borderThickness = borderThickness;
            return this;
        }

        public Builder setBorderSoftness(float borderSoftness) {
            this.borderSoftness = borderSoftness;
            return this;
        }

        public Builder setBorderColor(int borderColor) {
            this.borderColor = ColorUtil.combineAlpha(borderColor);
            return this;
        }

        public Builder applyBorderRadius(BorderRadius radius) {
            return setLeftTopRadius(radius.getLeftTop()).setRightTopRadius(radius.getRightTop()).setLeftBottomRadius(radius.getLeftBottom()).setRightBottomRadius(radius.getRightBottom()).setLowerEdgeSoftness(radius.getLowerEdgeSoftness()).setUpperEdgeSoftness(radius.getUpperEdgeSoftness()).setBorderColor(radius.getBorderBackground()).setBorderSoftness(radius.getBorderSoftness()).setBorderThickness(radius.getThickness());
        }

        public RoundedData build() {
            return new RoundedData(this.bounds, this.leftTopRadius, this.rightTopRadius, this.leftBottomRadius, this.rightBottomRadius, this.lowerEdgeSoftness, this.upperEdgeSoftness, this.borderThickness, this.borderSoftness, this.borderColor);
        }
    }
}
