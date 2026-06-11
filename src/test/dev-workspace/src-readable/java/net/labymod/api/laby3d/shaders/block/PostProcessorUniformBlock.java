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
import net.labymod.laby3d.api.shaders.block.property.Vector2fUniformProperty;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/PostProcessorUniformBlock.class */
public class PostProcessorUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "PostProcessor";
    private static final String PROJECTION_MATRIX_NAME = "ProjectionMatrix";
    private static final String SOURCE_SIZE_NAME = "SourceSize";
    private static final String DESTINATION_SIZE_NAME = "DestinationSize";
    private static final String SCREEN_SIZE_NAME = "ScreenSize";
    private static final String TIME_NAME = "Time";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(PROJECTION_MATRIX_NAME, DataType.MAT4, UniformType.MAT4).add(SOURCE_SIZE_NAME, DataType.VEC2, UniformType.VEC2).add(DESTINATION_SIZE_NAME, DataType.VEC2, UniformType.VEC2).add(SCREEN_SIZE_NAME, DataType.VEC2, UniformType.VEC2).add(TIME_NAME, DataType.SCALAR, UniformType.FLOAT).build();
    private final UniformProperty<Matrix4f> projectionMatrix;
    private final UniformProperty<Vector2f> sourceSize;
    private final UniformProperty<Vector2f> destinationSize;
    private final UniformProperty<Vector2f> screenSize;
    private final UniformProperty<Float> time;

    public PostProcessorUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.projectionMatrix = createProperty(PROJECTION_MATRIX_NAME, Matrix4fUniformProperty::new);
        this.sourceSize = createProperty(SOURCE_SIZE_NAME, Vector2fUniformProperty::new);
        this.destinationSize = createProperty(DESTINATION_SIZE_NAME, Vector2fUniformProperty::new);
        this.screenSize = createProperty(SCREEN_SIZE_NAME, Vector2fUniformProperty::new);
        this.time = createProperty(TIME_NAME, FloatUniformProperty::new);
    }

    @NotNull
    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.projectionMatrix, this.sourceSize, this.destinationSize, this.screenSize, this.time);
    }

    public UniformProperty<Matrix4f> projectionMatrix() {
        return this.projectionMatrix;
    }

    public UniformProperty<Vector2f> sourceSize() {
        return this.sourceSize;
    }

    public UniformProperty<Vector2f> destinationSize() {
        return this.destinationSize;
    }

    public UniformProperty<Vector2f> screenSize() {
        return this.screenSize;
    }

    public UniformProperty<Float> time() {
        return this.time;
    }
}
