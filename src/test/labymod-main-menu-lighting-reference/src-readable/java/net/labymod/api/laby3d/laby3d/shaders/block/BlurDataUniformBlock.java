package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector2fUniformProperty;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/BlurDataUniformBlock.class */
public class BlurDataUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "BlurData";
    private static final String BLUR_OFFSET_NAME = "Offset";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(BLUR_OFFSET_NAME, DataType.VEC2, UniformType.VEC2).build();
    private final UniformProperty<Vector2f> offset;

    public BlurDataUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.offset = createProperty(BLUR_OFFSET_NAME, Vector2fUniformProperty::new);
    }

    public UniformProperty<Vector2f> offset() {
        return this.offset;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.offset);
    }
}
