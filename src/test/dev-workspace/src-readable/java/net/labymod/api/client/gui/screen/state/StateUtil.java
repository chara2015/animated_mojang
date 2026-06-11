package net.labymod.api.client.gui.screen.state;

import net.labymod.api.Laby;
import net.labymod.api.laby3d.shaders.block.RoundDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.RoundDataUniformBlockData;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/StateUtil.class */
public final class StateUtil {
    private static final Vector3f TRANSFORM_DESTINATION = new Vector3f();
    private static final int DEFAULT_VERTEX_COLOR = -1;
    private static final float DEFAULT_MIN_U = 0.0f;
    private static final float DEFAULT_MAX_U = 1.0f;
    private static final float DEFAULT_MIN_V = 0.0f;
    private static final float DEFAULT_MAX_V = 1.0f;

    public static void applyRoundData(CommandBuffer cmd, RoundedData roundedData, Matrix4f pose, float stateLeft, float stateTop, float stateRight, float stateBottom) {
        float left;
        float top;
        float right;
        float bottom;
        RoundDataUniformBlockData roundData = new RoundDataUniformBlockData();
        Rectangle bounds = roundedData.getBounds();
        if (bounds == null) {
            left = pose.transformPosition(stateLeft, stateTop, 0.0f, TRANSFORM_DESTINATION).x;
            top = TRANSFORM_DESTINATION.y;
            right = pose.transformPosition(stateRight, stateBottom, 0.0f, TRANSFORM_DESTINATION).x;
            bottom = TRANSFORM_DESTINATION.y;
        } else {
            left = bounds.getLeft();
            top = bounds.getTop();
            right = bounds.getRight();
            bottom = bounds.getBottom();
        }
        roundData.setBorderWidth(roundedData.getBorderThickness());
        roundData.setBorderFadeDistance(roundedData.getBorderSoftness());
        ColorFormat format = ColorFormat.ARGB32;
        int borderColor = ColorUtil.combineAlpha(roundedData.getBorderColor());
        float red = format.normalizedRed(borderColor);
        float green = format.normalizedGreen(borderColor);
        float blue = format.normalizedBlue(borderColor);
        float alpha = format.normalizedAlpha(borderColor);
        roundData.borderColor().set(red, green, blue, alpha);
        roundData.setInnerShapeSoftness(roundedData.getLowerEdgeSoftness());
        roundData.setOuterShapeSoftness(roundedData.getUpperEdgeSoftness());
        roundData.cornerRoundingRadius().set(roundedData.getRightBottomRadius(), roundedData.getRightTopRadius(), roundedData.getLeftBottomRadius(), roundedData.getLeftTopRadius());
        roundData.rectangleDimensions().set(right - left, bottom - top);
        roundData.rectangleBounds().set(left, top, right, bottom);
        cmd.bindUniformBlock(RoundDataUniformBlock.NAME, Laby.references().laby3D().roundData());
        cmd.bindUniformBlockData(RoundDataUniformBlock.NAME, roundData);
    }

    public static void buildPostProcessingVertices(VertexConsumer consumer, Matrix4f pose, float left, float top, float right, float bottom) {
        consumer.addVertex2D(pose, left, top).setUv(0.0f, 0.0f).setColor(-1);
        consumer.addVertex2D(pose, right, top).setUv(1.0f, 0.0f).setColor(-1);
        consumer.addVertex2D(pose, right, bottom).setUv(1.0f, 1.0f).setColor(-1);
        consumer.addVertex2D(pose, left, bottom).setUv(0.0f, 1.0f).setColor(-1);
    }
}
