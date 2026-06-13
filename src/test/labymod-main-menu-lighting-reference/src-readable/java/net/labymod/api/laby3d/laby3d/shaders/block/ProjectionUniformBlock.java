package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.Matrix4fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/ProjectionUniformBlock.class */
public class ProjectionUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "Projection";
    private static final String PROJECTION_MATRIX_NAME = "ProjectionMatrix";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(PROJECTION_MATRIX_NAME, DataType.MAT4, UniformType.MAT4).build();
    private final UniformProperty<Matrix4f> projectionMatrix;

    public ProjectionUniformBlock(RenderDevice device) {
        super(device, "Projection", LAYOUT);
        this.projectionMatrix = createProperty(PROJECTION_MATRIX_NAME, Matrix4fUniformProperty::new);
    }

    public UniformProperty<Matrix4f> projectionMatrix() {
        return this.projectionMatrix;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.projectionMatrix);
    }
}
