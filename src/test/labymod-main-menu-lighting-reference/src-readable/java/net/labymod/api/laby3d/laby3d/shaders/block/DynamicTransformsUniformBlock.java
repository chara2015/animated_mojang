package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.FloatUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Matrix4fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector4fUniformProperty;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/DynamicTransformsUniformBlock.class */
public class DynamicTransformsUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "DynamicTransforms";
    private static final String MODEL_VIEW_MATRIX_NAME = "ModelViewMatrix";
    private static final String COLOR_MODULATOR_NAME = "ColorModulator";
    private static final String MODEL_OFFSET_NAME = "ModelOffset";
    private static final String LINE_WIDTH_NAME = "LineWidth";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(MODEL_VIEW_MATRIX_NAME, DataType.MAT4, UniformType.MAT4).add(COLOR_MODULATOR_NAME, DataType.VEC4, UniformType.VEC4).add(MODEL_OFFSET_NAME, DataType.VEC4, UniformType.VEC4).add(LINE_WIDTH_NAME, DataType.SCALAR, UniformType.FLOAT).build();
    private final UniformProperty<Matrix4f> modelViewMatrix;
    private final UniformProperty<Vector4f> colorModulator;
    private final UniformProperty<Vector4f> modelOffset;
    private final UniformProperty<Float> lineWidth;

    public DynamicTransformsUniformBlock(RenderDevice device) {
        super(device, "DynamicTransforms", LAYOUT);
        this.modelViewMatrix = createProperty(MODEL_VIEW_MATRIX_NAME, Matrix4fUniformProperty::new);
        this.colorModulator = createProperty(COLOR_MODULATOR_NAME, Vector4fUniformProperty::new);
        this.modelOffset = createProperty(MODEL_OFFSET_NAME, Vector4fUniformProperty::new);
        this.lineWidth = createProperty(LINE_WIDTH_NAME, FloatUniformProperty::new);
    }

    public UniformProperty<Matrix4f> modelViewMatrix() {
        return this.modelViewMatrix;
    }

    public UniformProperty<Vector4f> colorModulator() {
        return this.colorModulator;
    }

    public UniformProperty<Vector4f> modelOffset() {
        return this.modelOffset;
    }

    public UniformProperty<Float> lineWidth() {
        return this.lineWidth;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.modelViewMatrix, this.colorModulator, this.modelOffset, this.lineWidth);
    }
}
