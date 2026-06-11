package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.FloatUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector2fUniformProperty;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/CircleDataUniformBlock.class */
public class CircleDataUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "CircleData";
    private static final String CIRCLE_CENTER_NAME = "CircleCenter";
    private static final String CIRCLE_RADIUS_NAME = "CircleRadius";
    private static final String CIRCLE_INNER_RADIUS = "CircleInnerRadius";
    private static final String CIRCLE_START_ANGLE = "CircleStartAngle";
    private static final String CIRCLE_END_ANGLE = "CircleEndAngle";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(CIRCLE_CENTER_NAME, DataType.VEC2, UniformType.VEC2).add(CIRCLE_RADIUS_NAME, DataType.SCALAR, UniformType.FLOAT).add(CIRCLE_INNER_RADIUS, DataType.SCALAR, UniformType.FLOAT).add(CIRCLE_START_ANGLE, DataType.SCALAR, UniformType.FLOAT).add(CIRCLE_END_ANGLE, DataType.SCALAR, UniformType.FLOAT).build();
    private final UniformProperty<Vector2f> circleCenter;
    private final UniformProperty<Float> circleRadius;
    private final UniformProperty<Float> circleInnerRadius;
    private final UniformProperty<Float> circleStartAngle;
    private final UniformProperty<Float> circleEndAngle;

    public CircleDataUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.circleCenter = createProperty(CIRCLE_CENTER_NAME, Vector2fUniformProperty::new);
        this.circleRadius = createProperty(CIRCLE_RADIUS_NAME, FloatUniformProperty::new);
        this.circleInnerRadius = createProperty(CIRCLE_INNER_RADIUS, FloatUniformProperty::new);
        this.circleStartAngle = createProperty(CIRCLE_START_ANGLE, FloatUniformProperty::new);
        this.circleEndAngle = createProperty(CIRCLE_END_ANGLE, FloatUniformProperty::new);
    }

    public UniformProperty<Vector2f> circleCenter() {
        return this.circleCenter;
    }

    public UniformProperty<Float> circleRadius() {
        return this.circleRadius;
    }

    public UniformProperty<Float> circleInnerRadius() {
        return this.circleInnerRadius;
    }

    public UniformProperty<Float> circleStartAngle() {
        return this.circleStartAngle;
    }

    public UniformProperty<Float> circleEndAngle() {
        return this.circleEndAngle;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.circleCenter, this.circleRadius, this.circleInnerRadius, this.circleStartAngle, this.circleEndAngle);
    }
}
