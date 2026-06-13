package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.ArrayUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.BooleanUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.FloatArrayUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Matrix4fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector3fArrayUniformProperty;
import net.labymod.laby3d.api.util.Util;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/SchematicUniformBlock.class */
public class SchematicUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "Schematic";
    public static final int MAX_LIGHTS = 48;
    public static final String MAX_LIGHTS_NAME = "MAX_LIGHTS";
    private final UniformProperty<Matrix4f> projectionMatrix;
    private final UniformProperty<Matrix4f> viewMatrix;
    private final UniformProperty<Matrix4f> modelMatrix;
    private final ArrayUniformProperty<Vector3f> lightSourcePosition;
    private final ArrayUniformProperty<Vector3f> lightSourceColor;
    private final ArrayUniformProperty<Float> lightSourceConstant;
    private final ArrayUniformProperty<Float> lightSourceLinear;
    private final ArrayUniformProperty<Float> lightSourceQuadratic;
    private final UniformProperty<Boolean> lightSourceEnabled;
    private static final String PROJECTION_MATRIX_NAME = "ProjectionMatrix";
    private static final String VIEW_MATRIX_NAME = "ViewMatrix";
    private static final String MODEL_MATRIX_NAME = "ModelMatrix";
    private static final String LIGHT_SOURCE_ENABLED_NAME = "LightSourceEnabled";
    private static final String LIGHT_SOURCE_POSITION_NAME = "LightSourcePosition";
    private static final String LIGHT_SOURCE_COLOR_NAME = "LightSourceColor";
    private static final String LIGHT_SOURCE_CONSTANT_NAME = "LightSourceConstant";
    private static final String LIGHT_SOURCE_LINEAR_NAME = "LightSourceLinear";
    private static final String LIGHT_SOURCE_QUADRATIC_NAME = "LightSourceQuadratic";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(PROJECTION_MATRIX_NAME, DataType.MAT4, UniformType.MAT4).add(VIEW_MATRIX_NAME, DataType.MAT4, UniformType.MAT4).add(MODEL_MATRIX_NAME, DataType.MAT4, UniformType.MAT4).add(LIGHT_SOURCE_ENABLED_NAME, DataType.SCALAR, UniformType.FLOAT).addArray(LIGHT_SOURCE_POSITION_NAME, DataType.VEC3, UniformType.VEC3, 48).addArray(LIGHT_SOURCE_COLOR_NAME, DataType.VEC3, UniformType.VEC3, 48).addArray(LIGHT_SOURCE_CONSTANT_NAME, DataType.SCALAR, UniformType.FLOAT, 48).addArray(LIGHT_SOURCE_LINEAR_NAME, DataType.SCALAR, UniformType.FLOAT, 48).addArray(LIGHT_SOURCE_QUADRATIC_NAME, DataType.SCALAR, UniformType.FLOAT, 48).build();

    public SchematicUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.projectionMatrix = createProperty(PROJECTION_MATRIX_NAME, Matrix4fUniformProperty::new);
        this.viewMatrix = createProperty(VIEW_MATRIX_NAME, Matrix4fUniformProperty::new);
        this.modelMatrix = createProperty(MODEL_MATRIX_NAME, Matrix4fUniformProperty::new);
        this.lightSourceEnabled = createProperty(LIGHT_SOURCE_ENABLED_NAME, BooleanUniformProperty::new);
        this.lightSourcePosition = createProperty(LIGHT_SOURCE_POSITION_NAME, (Vector3f[]) Util.fillArray(new Vector3f[48], Vector3f::new), Vector3fArrayUniformProperty::new);
        this.lightSourceColor = createProperty(LIGHT_SOURCE_COLOR_NAME, (Vector3f[]) Util.fillArray(new Vector3f[48], Vector3f::new), Vector3fArrayUniformProperty::new);
        this.lightSourceConstant = createProperty(LIGHT_SOURCE_CONSTANT_NAME, (Float[]) Util.fillArray(new Float[48], () -> {
            return Float.valueOf(0.0f);
        }), FloatArrayUniformProperty::new);
        this.lightSourceLinear = createProperty(LIGHT_SOURCE_LINEAR_NAME, (Float[]) Util.fillArray(new Float[48], () -> {
            return Float.valueOf(0.0f);
        }), FloatArrayUniformProperty::new);
        this.lightSourceQuadratic = createProperty(LIGHT_SOURCE_QUADRATIC_NAME, (Float[]) Util.fillArray(new Float[48], () -> {
            return Float.valueOf(0.0f);
        }), FloatArrayUniformProperty::new);
    }

    public UniformProperty<Matrix4f> projectionMatrix() {
        return this.projectionMatrix;
    }

    public UniformProperty<Matrix4f> viewMatrix() {
        return this.viewMatrix;
    }

    public UniformProperty<Matrix4f> modelMatrix() {
        return this.modelMatrix;
    }

    public ArrayUniformProperty<Vector3f> lightSourcePosition() {
        return this.lightSourcePosition;
    }

    public ArrayUniformProperty<Vector3f> lightSourceColor() {
        return this.lightSourceColor;
    }

    public ArrayUniformProperty<Float> lightSourceConstant() {
        return this.lightSourceConstant;
    }

    public ArrayUniformProperty<Float> lightSourceLinear() {
        return this.lightSourceLinear;
    }

    public ArrayUniformProperty<Float> lightSourceQuadratic() {
        return this.lightSourceQuadratic;
    }

    public UniformProperty<Boolean> lightSourceEnabled() {
        return this.lightSourceEnabled;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.projectionMatrix, this.viewMatrix, this.modelMatrix, this.lightSourceEnabled, this.lightSourcePosition, this.lightSourceColor, this.lightSourceConstant, this.lightSourceLinear, this.lightSourceQuadratic);
    }
}
