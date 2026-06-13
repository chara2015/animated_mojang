package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.ArrayUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Matrix4fArrayUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Matrix4fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector2iUniformProperty;
import net.labymod.laby3d.api.util.Util;
import org.joml.Matrix4f;
import org.joml.Vector2i;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/CosmeticsUniformBlock.class */
public class CosmeticsUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "Cosmetics";
    public static final int MAX_BONES = 240;
    private static final String MODEL_MATRIX_NAME = "ModelMatrix";
    private static final String LIGHT_COORDS_NAME = "LightCoords";
    private static final String BONE_MATRIX_NAME = "Bones";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(MODEL_MATRIX_NAME, DataType.MAT4, UniformType.MAT4).add(LIGHT_COORDS_NAME, DataType.VEC2, UniformType.IVEC2).addArray(BONE_MATRIX_NAME, DataType.MAT4, UniformType.MAT4, 240).build();
    private final UniformProperty<Matrix4f> modelMatrix;
    private final UniformProperty<Vector2i> lightCoords;
    private final ArrayUniformProperty<Matrix4f> bones;

    public CosmeticsUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.modelMatrix = createProperty(MODEL_MATRIX_NAME, Matrix4fUniformProperty::new);
        this.lightCoords = createProperty(LIGHT_COORDS_NAME, Vector2iUniformProperty::new);
        this.bones = createProperty(BONE_MATRIX_NAME, (Matrix4f[]) Util.fillArray(new Matrix4f[240], Matrix4f::new), Matrix4fArrayUniformProperty::new);
    }

    public UniformProperty<Matrix4f> modelMatrix() {
        return this.modelMatrix;
    }

    public UniformProperty<Vector2i> lightCoords() {
        return this.lightCoords;
    }

    public ArrayUniformProperty<Matrix4f> bones() {
        return this.bones;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.modelMatrix, this.lightCoords, this.bones);
    }
}
