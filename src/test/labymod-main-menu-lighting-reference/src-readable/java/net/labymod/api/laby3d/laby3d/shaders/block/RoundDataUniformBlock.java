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
import net.labymod.laby3d.api.shaders.block.property.Vector4fUniformProperty;
import org.joml.Vector2f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/RoundDataUniformBlock.class */
public class RoundDataUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "RoundData";
    private static final String CORNER_ROUNDING_RADIUS_NAME = "CornerRoundingRadius";
    private static final String RECTANGLE_BOUNDS_NAME = "RectangleBounds";
    private static final String RECTANGLE_DIMENSIONS_NAME = "RectangleDimensions";
    private static final String INNER_SHAPE_SOFTNESS = "InnerShapeSoftness";
    private static final String OUTER_SHAPE_SOFTNESS = "OuterShapeSoftness";
    private static final String BORDER_WIDTH_NAME = "BorderWidth";
    private static final String BORDER_FADE_DISTANCE_NAME = "BorderFadeDistance";
    private static final String BORDER_COLOR_NAME = "BorderColor";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(CORNER_ROUNDING_RADIUS_NAME, DataType.VEC4, UniformType.VEC4).add(RECTANGLE_BOUNDS_NAME, DataType.VEC4, UniformType.VEC4).add(RECTANGLE_DIMENSIONS_NAME, DataType.VEC2, UniformType.VEC2).add(INNER_SHAPE_SOFTNESS, DataType.SCALAR, UniformType.FLOAT).add(OUTER_SHAPE_SOFTNESS, DataType.SCALAR, UniformType.FLOAT).add(BORDER_WIDTH_NAME, DataType.SCALAR, UniformType.FLOAT).add(BORDER_FADE_DISTANCE_NAME, DataType.SCALAR, UniformType.FLOAT).add(BORDER_COLOR_NAME, DataType.VEC4, UniformType.VEC4).build();
    private final UniformProperty<Vector4f> cornerRoundingRadius;
    private final UniformProperty<Vector4f> rectangleBounds;
    private final UniformProperty<Vector2f> rectangleDimensions;
    private final UniformProperty<Vector4f> borderColor;
    private final UniformProperty<Float> innerShapeSoftness;
    private final UniformProperty<Float> outerShapeSoftness;
    private final UniformProperty<Float> borderWidth;
    private final UniformProperty<Float> borderFadeDistance;

    public RoundDataUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.cornerRoundingRadius = createProperty(CORNER_ROUNDING_RADIUS_NAME, Vector4fUniformProperty::new);
        this.rectangleBounds = createProperty(RECTANGLE_BOUNDS_NAME, Vector4fUniformProperty::new);
        this.rectangleDimensions = createProperty(RECTANGLE_DIMENSIONS_NAME, Vector2fUniformProperty::new);
        this.borderColor = createProperty(BORDER_COLOR_NAME, Vector4fUniformProperty::new);
        this.innerShapeSoftness = createProperty(INNER_SHAPE_SOFTNESS, FloatUniformProperty::new);
        this.outerShapeSoftness = createProperty(OUTER_SHAPE_SOFTNESS, FloatUniformProperty::new);
        this.borderWidth = createProperty(BORDER_WIDTH_NAME, FloatUniformProperty::new);
        this.borderFadeDistance = createProperty(BORDER_FADE_DISTANCE_NAME, FloatUniformProperty::new);
    }

    public UniformProperty<Vector4f> cornerRoundingRadius() {
        return this.cornerRoundingRadius;
    }

    public UniformProperty<Vector4f> rectangleBounds() {
        return this.rectangleBounds;
    }

    public UniformProperty<Vector2f> rectangleDimensions() {
        return this.rectangleDimensions;
    }

    public UniformProperty<Vector4f> borderColor() {
        return this.borderColor;
    }

    public UniformProperty<Float> innerShapeSoftness() {
        return this.innerShapeSoftness;
    }

    public UniformProperty<Float> outerShapeSoftness() {
        return this.outerShapeSoftness;
    }

    public UniformProperty<Float> borderWidth() {
        return this.borderWidth;
    }

    public UniformProperty<Float> borderFadeDistance() {
        return this.borderFadeDistance;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.cornerRoundingRadius, this.rectangleBounds, this.rectangleDimensions, this.borderColor, this.innerShapeSoftness, this.outerShapeSoftness, this.borderWidth, this.borderFadeDistance);
    }
}
