package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector3fUniformProperty;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/LightingUniformBlock.class */
public class LightingUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "Lighting";
    private static final String LIGHT0_DIRECTION_NAME = "Light0_Direction";
    private static final String LIGHT1_DIRECTION_NAME = "Light1_Direction";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(LIGHT0_DIRECTION_NAME, DataType.VEC3, UniformType.VEC3).add(LIGHT1_DIRECTION_NAME, DataType.VEC3, UniformType.VEC3).build();
    private final UniformProperty<Vector3f> light0Direction;
    private final UniformProperty<Vector3f> light1Direction;

    public LightingUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.light0Direction = createProperty(LIGHT0_DIRECTION_NAME, Vector3fUniformProperty::new);
        this.light1Direction = createProperty(LIGHT1_DIRECTION_NAME, Vector3fUniformProperty::new);
    }

    public UniformProperty<Vector3f> light0Direction() {
        return this.light0Direction;
    }

    public UniformProperty<Vector3f> light1Direction() {
        return this.light1Direction;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.light0Direction, this.light1Direction);
    }
}
